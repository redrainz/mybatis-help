package xyz.redrain.helper;

import xyz.redrain.exception.ParamIsNullException;
import xyz.redrain.exception.PrimaryKeyNoExsitException;
import xyz.redrain.exception.UpdateSetValueNoExsitException;
import xyz.redrain.parse.ObjectEntity;
import xyz.redrain.parse.ObjectParse;
import xyz.redrain.parse.ParseUtil;
import xyz.redrain.parse.PropertyEntity;

import java.util.List;
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

        String whereStr = objectEntity.getPropertyEntities().stream()
                .filter(PropertyEntity::isId).findAny()
                .map(ParseUtil::getEqualParams)
                .orElseThrow(PrimaryKeyNoExsitException::new);

        String equalStr = objectEntity.getPropertyEntities().stream()
                .filter(propertyEntity -> !propertyEntity.isId())
                .filter(propertyEntity -> !propertyEntity.isUpdateSetNullFlag())
                .map(ParseUtil::getEqualParams)
                .collect(Collectors.joining(" , "));

        if ("".equals(equalStr.trim())) {
            throw new UpdateSetValueNoExsitException();
        }
        return String.format("UPDATE %s SET %s WHERE %s", ParseUtil.addBackQuote(objectEntity.getTableName()),
                equalStr, whereStr);
    }


}
