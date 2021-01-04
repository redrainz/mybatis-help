package xyz.redrain.parse;

import xyz.redrain.anntation.*;
import xyz.redrain.exception.DuplicatePrimaryKeyException;
import xyz.redrain.exception.ParamIsNullException;
import xyz.redrain.exception.PrimaryKeyNoExistException;

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

    private static final String ID = "id";

    private ObjectParse() {
    }

    public static ObjectEntity getObjectEntity(Object param) throws Exception {
        if (null == param) {
            throw new ParamIsNullException();
        }
        Class<?> clazz = param.getClass();
        ObjectEntity objectEntity = new ObjectEntity();
        parseTableName(clazz, objectEntity);
        parsePropertyName(param, clazz, objectEntity);

        return objectEntity;
    }

    private static void parsePropertyName(Object param, Class<?> clazz, ObjectEntity objectEntity) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length != 0) {
            boolean propertyUseUnderlineStitching = objectEntity.isPropertyUseUnderlineStitching();
            boolean hasId = false;
            for (Field field : fields) {

                if (field.getAnnotation(Ignore.class) != null) {
                    continue;
                }
                field.setAccessible(true);
                String columnName = propertyUseUnderlineStitching
                        ? ParseUtil.underlineStitching(field.getName())
                        : field.getName();

                String javaType = field.getType().getSimpleName().toLowerCase();
                JavaType javaTypeAnnotation = field.getAnnotation(JavaType.class);
                if (null != javaTypeAnnotation) {
                    javaType = ParseUtil.getProperty(javaTypeAnnotation.value(), javaType);
                }

                String jdbcType = null;
                boolean id = false;
                Id idAnnotation = field.getAnnotation(Id.class);
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (null != idAnnotation) {
                    if (hasId) {
                        throw new DuplicatePrimaryKeyException();
                    }
                    id = true;
                    hasId = true;
                    columnName = ParseUtil.getProperty(idAnnotation.value(), columnName);
                    jdbcType = ParseUtil.getProperty(idAnnotation.jdbcType(), (String) null);
                } else if (null != columnAnnotation) {
                    columnName = ParseUtil.getProperty(columnAnnotation.value(), columnName);
                    jdbcType = ParseUtil.getProperty(columnAnnotation.jdbcType(), (String) null);
                }

                PropertyEntity propertyEntity = new PropertyEntity();
                propertyEntity.setId(id);
                propertyEntity.setColumnName(columnName);
                propertyEntity.setJdbcType(jdbcType);
                propertyEntity.setJavaType(javaType);
                propertyEntity.setPropertyName(field.getName());
                propertyEntity.setPropertyValue(field.get(param));
                propertyEntity.setUpdateSetNullFlag(field.getAnnotation(UpdateSetNull.class) != null);
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
                throw new PrimaryKeyNoExistException();
            }
        }
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

}
