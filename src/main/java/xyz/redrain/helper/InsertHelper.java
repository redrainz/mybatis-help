package xyz.redrain.helper;

import xyz.redrain.parse.ObjectEntity;
import xyz.redrain.parse.ObjectParse;
import xyz.redrain.parse.ParseUtil;
import xyz.redrain.parse.PropertyEntity;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 */
public class InsertHelper {

    public String insertObj(Object param) throws Exception {
        if (null == param) {
            throw new Exception();
        }
        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param.getClass());
        return getInsertSql(objectEntity);
    }

    public String insertObjSelective(Object param) throws Exception {
        if (null == param) {
            throw new Exception();
        }
        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param.getClass());
        ObjectParse.delNullProperty(param, objectEntity);
        return getInsertSql(objectEntity);
    }


    public String getInsertSql(ObjectEntity objectEntity) {
        objectEntity.getPropertyEntities().removeIf(PropertyEntity::isId);

        return String.format("insert into %s ( %s ) values ( %s )",
                ParseUtil.addBackQuote(objectEntity.getTableName()),
                ParseUtil.getJdbcParams(objectEntity),
                ParseUtil.getSqlParams(objectEntity));

    }

}
