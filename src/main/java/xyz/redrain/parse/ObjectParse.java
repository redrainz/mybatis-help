package xyz.redrain.parse;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import xyz.redrain.anntation.*;
import xyz.redrain.exception.DuplicatePrimaryKeyException;
import xyz.redrain.exception.ParamClassIsNullException;
import xyz.redrain.exception.ParamIsNullException;
import xyz.redrain.exception.PrimaryKeyNoExsitException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 */
public class ObjectParse {

    private static final String ID = "id";

    private ObjectParse() {
    }

    public static ObjectEntity getObjectEntity(Object param) throws Exception {
        if (null == param) {
            throw new ParamIsNullException();
        }
        Class<?> clazz = param.getClass();
        ObjectEntity objectEntity = new ObjectEntity();
        parseIndexs(clazz, objectEntity);
        parseTableName(clazz, objectEntity);
        parsePropertyName(param, clazz, objectEntity);

        return objectEntity;
    }

    private static void parseIndexs(Class<?> clazz, ObjectEntity objectEntity) {
        Indices indices = clazz.getAnnotation(Indices.class);
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

    private static void parsePropertyName(Object param, Class<?> clazz, ObjectEntity objectEntity) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        boolean propertyUseUnderlineStitching = objectEntity.isPropertyUseUnderlineStitching();
        if (fields.length != 0) {
            boolean hasId = false;
            for (Field field : fields) {

                if (field.getAnnotation(Ignore.class) != null) {
                    continue;
                }
                field.setAccessible(true);
                Column columnAnnotation = field.getAnnotation(Column.class);
                String columnName = propertyUseUnderlineStitching
                        ? ParseUtil.underlineStitching(field.getName())
                        : field.getName();

                String javaType = field.getType().getSimpleName().toLowerCase();
                String jdbcType = null;
                JavaType javaTypeAnnotation = field.getAnnotation(JavaType.class);
                if (null != javaTypeAnnotation) {
                    javaType = ParseUtil.getProperty(javaTypeAnnotation.value(), javaType);
                }

                boolean id = false;
                Id idAnnotation = field.getAnnotation(Id.class);
                if (null != idAnnotation) {
                    if (hasId) {
                        throw new DuplicatePrimaryKeyException();
                    }
                    id = true;
                    hasId = true;
                    columnName = ParseUtil.getProperty(idAnnotation.value(), columnName);
                    jdbcType = ParseUtil.getProperty(idAnnotation.jdbcType(), jdbcType);
                } else if (null != columnAnnotation) {
                    columnName = ParseUtil.getProperty(columnAnnotation.value(), columnName);
                    jdbcType = ParseUtil.getProperty(columnAnnotation.jdbcType(), jdbcType);
                }

                PropertyEntity propertyEntity = new PropertyEntity();
                propertyEntity.setId(id);
                propertyEntity.setColumnName(columnName);
                propertyEntity.setJdbcType(jdbcType);
                propertyEntity.setJavaType(javaType);
                propertyEntity.setPropertyName(field.getName());
                propertyEntity.setPropertyValue(field.get(param));
                propertyEntity.setUpdateSetNullFlag(field.getAnnotation(UpdateSetNull.class) != null);
                Order orderAnnotation = field.getAnnotation(Order.class);
                if (orderAnnotation != null) {
                    propertyEntity.setOrder(orderAnnotation.value());
                }
                objectEntity.getPropertyEntities().add(propertyEntity);
            }

            if (!hasId) {
                for (PropertyEntity propertyEntity : objectEntity.getPropertyEntities()) {
                    if (ID.equals(propertyEntity.getPropertyName())) {
                        propertyEntity.setId(true);
                        hasId = true;
                    }
                }
            }

            if (!hasId) {
                throw new PrimaryKeyNoExsitException();
            }
        }
        objectEntity.getPropertyEntities().sort(Comparator.comparing(PropertyEntity::getOrder));
    }

    private static void parseTableName(Class<?> clazz, ObjectEntity objectEntity) {
        Table table = clazz.getAnnotation(Table.class);
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

    public static void delNullProperty(ObjectEntity objectEntity) {
        List<PropertyEntity> propertyEntities = objectEntity.getPropertyEntities()
                .stream().filter(propertyEntity -> propertyEntity.getPropertyValue() != null)
                .collect(Collectors.toList());
        objectEntity.setPropertyEntities(propertyEntities);
    }

    public static void useIndices(ObjectEntity objectEntity) {
        List<String> indices = objectEntity.getIndices();
        List<PropertyEntity> propertyEntities = objectEntity.getPropertyEntities();

        if (indices == null || indices.isEmpty()
                || propertyEntities == null || propertyEntities.isEmpty()) {
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
                    .collect(Collectors.toMap(PropertyEntity::getPropertyName, Function.identity()));
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
