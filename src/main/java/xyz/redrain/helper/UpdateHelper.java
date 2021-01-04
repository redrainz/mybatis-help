package xyz.redrain.helper;

import xyz.redrain.exception.ParamIsNullException;
import xyz.redrain.exception.PrimaryKeyNoExistException;
import xyz.redrain.exception.UpdateSetValueNoExistException;
import xyz.redrain.parse.ObjectEntity;
import xyz.redrain.parse.ObjectParse;
import xyz.redrain.parse.ParseUtil;
import xyz.redrain.parse.PropertyEntity;

import java.util.stream.Collectors;

/**
 * Created by RedRain on 2018/11/19.
 *
 * @author RedRain
 * @version 1.0
 */
public class UpdateHelper {

    public String updateObjById(Object param) throws Exception {
        return getUpdateSql(param, false);
    }

    public String updateObjSelectiveById(Object param) throws Exception {
        return getUpdateSql(param, true);
    }


    private String getUpdateSql(Object param, boolean isSelective) throws Exception {
        if (null == param) {
            throw new ParamIsNullException();
        }
        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param);

        if (isSelective) {
            ObjectParse.delNullProperty(objectEntity);
        }

        String setStr = objectEntity.getPropertyEntities().stream()
                .filter(propertyEntity -> !propertyEntity.isId())
                .filter(propertyEntity -> !propertyEntity.isUpdateSetNullFlag())
                .map(ParseUtil::getEqualParams)
                .collect(Collectors.joining(" , "));

        if ("".equals(setStr.trim())) {
            throw new UpdateSetValueNoExistException();
        }

        String whereStr = objectEntity.getPropertyEntities().stream()
                .filter(PropertyEntity::isId).findAny()
                .map(ParseUtil::getEqualParams)
                .orElseThrow(PrimaryKeyNoExistException::new);

        return String.format("UPDATE %s SET %s WHERE %s", ParseUtil.addBackQuote(objectEntity.getTableName()),
                setStr, whereStr);
    }


}
