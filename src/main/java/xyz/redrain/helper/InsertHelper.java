package xyz.redrain.helper;

import xyz.redrain.exception.InsertValuesNoExsitException;
import xyz.redrain.exception.ParamIsNullException;
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
        return getInsertSql(param, false);
    }

    public String insertObjSelective(Object param) throws Exception {
        return getInsertSql(param, true);
    }


    public String getInsertSql(Object param, boolean isSelective) throws Exception {
        if (null == param) {
            throw new ParamIsNullException();
        }

        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param);
        if (isSelective) {
            ObjectParse.delNullProperty(objectEntity);
        }

        if (objectEntity.getPropertyEntities().isEmpty()){
            throw new InsertValuesNoExsitException();
        }
        return String.format("insert into %s ( %s ) values ( %s )",
                ParseUtil.addBackQuote(objectEntity.getTableName()),
                ParseUtil.getJdbcParams(objectEntity),
                ParseUtil.getSqlParams(objectEntity));

    }

}
