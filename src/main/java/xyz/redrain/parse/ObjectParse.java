package xyz.redrain.parse;

import xyz.redrain.anntation.Column;
import xyz.redrain.anntation.Id;
import xyz.redrain.anntation.Ignore;
import xyz.redrain.anntation.JavaType;
import xyz.redrain.anntation.Table;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 * @description TODO
 */
public class ObjectParse {
    public static ObjectEntity getObjectEntity(Class clazz) throws Exception {
        if (null == clazz) {
            return null;
        }
        ObjectEntity objectEntity = new ObjectEntity(null, new ArrayList<PropertyEntity>());
        parseTableName(clazz, objectEntity);
        parsePropertyName(clazz, objectEntity);
        return objectEntity;
    }

    private static void parsePropertyName(Class clazz, ObjectEntity objectEntity) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        if (null != fields && fields.length != 0) {
            boolean hasId = false;
            for (Field field : fields) {
                Ignore ignoreAnnotation = field.getAnnotation(Ignore.class);
                Column columnAnnotation = field.getAnnotation(Column.class);
                Id idAnnotation = field.getAnnotation(Id.class);
                JavaType javaTypeAnnotation = field.getAnnotation(JavaType.class);

                if (null != ignoreAnnotation) {
                    continue;
                }
                String columnName = ParseUtil.underlineStitching(field.getName());
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

    }

    private static void parseTableName(Class clazz, ObjectEntity objectEntity) throws Exception {
        Table table = (Table) clazz.getAnnotation(Table.class);
        String tableName;
        if (null != table) {
            tableName = ParseUtil.getProperty(table.value(), ParseUtil.underlineStitching(clazz.getSimpleName()));
        } else {
            tableName = ParseUtil.underlineStitching(clazz.getSimpleName());
        }
        objectEntity.setTableName(tableName);
    }

    public static void delNullProperty(Object object, ObjectEntity objectEntity) {
        MetaObject metaObject = MetaObject.forObject(object, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        List<PropertyEntity> propertyEntities = new ArrayList<PropertyEntity>();
        if (null != objectEntity && null != objectEntity.getPropertyEntities() && !objectEntity.getPropertyEntities().isEmpty()) {
            for (PropertyEntity propertyEntity : objectEntity.getPropertyEntities()) {
                if (null != metaObject.getValue(propertyEntity.getPropertyName())) {
                    propertyEntities.add(propertyEntity);
                }
            }
        }
        objectEntity.setPropertyEntities(propertyEntities);
    }
}
