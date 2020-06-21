package xyz.redrain.helper;

import xyz.redrain.exception.IdNoExsitException;
import xyz.redrain.exception.ParamIsNullException;
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

    private String getDeleteSql(Object param, boolean isId) throws Exception {

        if (null == param) {
            throw new ParamIsNullException();
        }

        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param.getClass());
        ObjectParse.delNullProperty(param, objectEntity);

        String equalParams = isId ?
                objectEntity.getPropertyEntities().stream()
                        .filter(PropertyEntity::isId).findAny()
                        .map(ParseUtil::getEqualParams)
                        .orElseThrow(IdNoExsitException::new)
                : objectEntity.getPropertyEntities().stream()
                .map(ParseUtil::getEqualParams)
                .collect(Collectors.joining(" and "));

        return String.format("DELETE FROM %s WHERE %s",
                ParseUtil.addBackQuote(objectEntity.getTableName()),
                equalParams);
    }


}
