package xyz.redrain.helper;

import xyz.redrain.parse.ObjectEntity;
import xyz.redrain.parse.ObjectParse;
import xyz.redrain.parse.ParseUtil;
import xyz.redrain.parse.PropertyEntity;

/**
 * Created by RedRain on 2018/11/21.
 *
 * @author RedRain
 * @version 1.0
 * @description TODO
 */
public class DeleteHelper {
    public String deleteObjById(Object param) throws Exception {
        if (null == param) {
            throw new Exception();
        }
        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param.getClass());
        return getDeleteSqlById(objectEntity);
    }

    public String deleteObjByParams(Object param) throws Exception {
        if (null == param) {
            throw new Exception();
        }

        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param.getClass());
        ObjectParse.delNullProperty(param, objectEntity);

        return getDeleteSql(objectEntity);
    }

    private String getDeleteSql(ObjectEntity objectEntity) {
        StringBuilder deleteStr = new StringBuilder();
        deleteStr.append("delete from ")
                .append(objectEntity.getTableName())
                .append(" where ");
        for (PropertyEntity propertyEntity : objectEntity.getPropertyEntities()) {

            deleteStr.append(ParseUtil.getEqualParams(propertyEntity))
                    .append(" and ");
        }
        if (deleteStr.length() > 1) {
            deleteStr.delete(deleteStr.length() - 4, deleteStr.length());
        }
        return deleteStr.toString();
    }

    private String getDeleteSqlById(ObjectEntity objectEntity) {
        StringBuilder deleteStr = new StringBuilder();
        deleteStr.append("delete from ")
                .append(objectEntity.getTableName())
                .append(" where ");
        for (PropertyEntity propertyEntity : objectEntity.getPropertyEntities()) {
            if (propertyEntity.isId()) {
                deleteStr.append(ParseUtil.getEqualParams(propertyEntity));
            }

        }
        return deleteStr.toString();
    }


}
