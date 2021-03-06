package xyz.redrain.helper;

import xyz.redrain.exception.DeleteConditionNoExistException;
import xyz.redrain.exception.ParamIsNullException;
import xyz.redrain.exception.PrimaryKeyNoExistException;
import xyz.redrain.parse.ObjectEntity;
import xyz.redrain.parse.ObjectParse;
import xyz.redrain.parse.ParseUtil;
import xyz.redrain.parse.PropertyEntity;

import java.util.stream.Collectors;

/**
 * 删除
 * Created by RedRain on 2018/11/21.
 *
 * @author RedRain
 * @version 1.0
 */
public class DeleteHelper {

    /**
     * 通过ID删除数据
     *
     * @param param id不为空的入参
     * @return deleteObjByIdSql
     * @throws Exception
     */
    public String deleteObjById(Object param) throws Exception {
        return getDeleteSql(param, true);
    }

    public String deleteObjByParams(Object param) throws Exception {

        return getDeleteSql(param, false);
    }

    private String getDeleteSql(Object param, boolean deleteById) throws Exception {

        if (null == param) {
            throw new ParamIsNullException();
        }

        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param);
        String equalParams = deleteById ?
                objectEntity.getPropertyEntities().stream()
                        .filter(PropertyEntity::isId)
                        .filter(propertyEntity -> propertyEntity.getPropertyValue() != null)
                        .findAny()
                        .map(ParseUtil::getEqualParams)
                        .orElseThrow(PrimaryKeyNoExistException::new)
                : objectEntity.getPropertyEntities().stream()
                .filter(propertyEntity -> propertyEntity.getPropertyValue() != null)
                .map(ParseUtil::getEqualParams)
                .collect(Collectors.joining(" AND "));

        if ("".equals(equalParams.trim())) {
            throw new DeleteConditionNoExistException();
        }
        return String.format("DELETE FROM %s WHERE %s",
                ParseUtil.addBackQuote(objectEntity.getTableName()),
                equalParams);
    }


}
