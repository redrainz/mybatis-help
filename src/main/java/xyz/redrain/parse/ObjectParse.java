package xyz.redrain.parse;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import xyz.redrain.anntation.*;
import xyz.redrain.exception.ParamClassIsNullException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 */
public class ObjectParse {

    private ObjectParse() {
    }

    public static ObjectEntity getObjectEntity(Class clazz) throws Exception {
        if (null == clazz) {
            throw new ParamClassIsNullException();
        }
        ObjectEntity objectEntity = new ObjectEntity();
        parseIndexs(clazz, objectEntity);
        parseTableName(clazz, objectEntity);
        parsePropertyName(clazz, objectEntity);

        return objectEntity;
    }

    private static void parseIndexs(Class clazz, ObjectEntity objectEntity) {
        Indices indices = (Indices) clazz.getAnnotation(Indices.class);
        if (indices != null && indices.value().length > 0) {
            List<String> indexList = new ArrayList<>();
            for (String index : indices.value()) {
                if (index != null && !"".equals(index.trim())) {
                    index = index.replaceAll(" ", "");
                    index = index.replaceAll("`", "");
                    index = ParseUtil.toUpperCase(index);
                    indexList.add(index);
                }
            }
            objectEntity.setIndices(indexList);
        }
    }

    private static void parsePropertyName(Class clazz, ObjectEntity objectEntity) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        boolean propertyUseUnderlineStitching = objectEntity.isPropertyUseUnderlineStitching();
        if (fields.length != 0) {
            boolean hasId = false;
            for (Field field : fields) {
                Ignore ignoreAnnotation = field.getAnnotation(Ignore.class);
                Column columnAnnotation = field.getAnnotation(Column.class);
                Id idAnnotation = field.getAnnotation(Id.class);
                JavaType javaTypeAnnotation = field.getAnnotation(JavaType.class);
                UpdateSetNull updateSetNullAnnotation = field.getAnnotation(UpdateSetNull.class);
                Order orderAnnotation = field.getAnnotation(Order.class);
                if (null != ignoreAnnotation) {
                    continue;
                }
                String columnName = propertyUseUnderlineStitching
                        ? ParseUtil.underlineStitching(field.getName())
                        : field.getName();
                String javaType = field.getType().getSimpleName().toLowerCase();
                String jdbcType = null;
                String propertyName = field.getName();
                boolean id = false;
                if (null != javaTypeAnnotation) {
                    javaType = ParseUtil.getProperty(javaTypeAnnotation.value(), javaType);
                }
                if (null != idAnnotation) {
                    if (hasId) {
                        throw new Exception("不支持两个主键");
                    }
                    id = true;
                    hasId = true;
                    columnName = ParseUtil.getProperty(idAnnotation.value(), columnName);
                    jdbcType = ParseUtil.getProperty(idAnnotation.jdbcType(), jdbcType);
                } else if (null != columnAnnotation) {
                    columnName = ParseUtil.getProperty(columnAnnotation.value(), columnName);
                    jdbcType = ParseUtil.getProperty(columnAnnotation.jdbcType(), jdbcType);
                }
                PropertyEntity propertyEntity = new PropertyEntity(columnName, jdbcType, javaType, propertyName, id);

                if (updateSetNullAnnotation != null) {
                    propertyEntity.setUpdateSetNullFlag(true);
                }

                if (orderAnnotation != null) {
                    propertyEntity.setOrder(orderAnnotation.value());
                }
                objectEntity.getPropertyEntities().add(propertyEntity);
            }
            if (!hasId) {
                for (PropertyEntity propertyEntity : objectEntity.getPropertyEntities()) {
                    if ("id".equals(propertyEntity.getPropertyName())) {
                        propertyEntity.setId(true);
                        hasId = true;
                    }
                }
            }
            if (!hasId) {
                throw new Exception("主键不能为空");
            }
        }
        objectEntity.getPropertyEntities().sort(Comparator.comparing(PropertyEntity::getOrder));
    }

    private static void parseTableName(Class clazz, ObjectEntity objectEntity){
        Table table = (Table) clazz.getAnnotation(Table.class);
        boolean tableUseUnderlineStitching = table == null || table.tableUseUnderlineStitching();
        objectEntity.setTableUseUnderlineStitching(tableUseUnderlineStitching);
        objectEntity.setPropertyUseUnderlineStitching(table == null || table.propertyUseUnderlineStitching());

        String tableName;
        if (table == null || "".equals(table.value().trim())) {
            tableName = tableUseUnderlineStitching
                    ? ParseUtil.underlineStitching(clazz.getSimpleName())
                    : clazz.getSimpleName();
        } else {
            tableName = table.value();
        }
        objectEntity.setTableName(tableName);
    }

    public static void delNullProperty(Object object, ObjectEntity objectEntity) {
        MetaObject metaObject = SystemMetaObject.forObject(object);
        List<PropertyEntity> propertyEntities = new ArrayList<>();
        if (null != objectEntity && null != objectEntity.getPropertyEntities() && !objectEntity.getPropertyEntities().isEmpty()) {
            for (PropertyEntity propertyEntity : objectEntity.getPropertyEntities()) {
                if (null != metaObject.getValue(propertyEntity.getPropertyName())) {
                    propertyEntities.add(propertyEntity);
                }
            }
        }
        objectEntity.setPropertyEntities(propertyEntities);
    }

    public static void useIndexs(ObjectEntity objectEntity) {
        List<String> indices = objectEntity.getIndices();
        List<PropertyEntity> propertyEntities = objectEntity.getPropertyEntities();

        if (indices == null || indices.isEmpty() || propertyEntities == null || propertyEntities.isEmpty()) {
            return;
        }
        Set<String> propertyNames = propertyEntities.stream()
                .map(PropertyEntity::getPropertyName).collect(Collectors.toSet());
        int maxIndex = -1;
        int maxSum = 0;
        for (int i = 0; i < indices.size(); i++) {
            String index = indices.get(i);
            if (index != null && !"".equals(index.trim())) {
                int sum = 0;
                String[] indexTemp = index.split(",");
                for (String name : indexTemp) {
                    if (!propertyNames.contains(name)) {
                        break;
                    }
                    sum++;
                }
                if (sum > maxSum) {
                    maxSum = sum;
                    maxIndex = i;
                }
            }
        }
        //使用第maxIndex索引
        if (maxIndex > -1) {
            Map<String, PropertyEntity> map = propertyEntities.stream()
                    .collect(Collectors.toMap(PropertyEntity::getPropertyName, propertyEntity -> propertyEntity));
            String index = indices.get(maxIndex);
            String[] indexTemp = index.split(",");
            List<PropertyEntity> result = new ArrayList<>();
            for (int i = 0; i < maxSum; i++) {
                result.add(map.remove(indexTemp[i]));
            }
            result.addAll(new ArrayList<>(map.values()));
            objectEntity.setPropertyEntities(result);
        }


    }
}
