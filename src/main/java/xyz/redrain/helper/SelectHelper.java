package xyz.redrain.helper;

import xyz.redrain.exception.PageParamIsNullException;
import xyz.redrain.exception.ParamIsNullException;
import xyz.redrain.exception.PrimaryKeyNoExistException;
import xyz.redrain.parse.ObjectEntity;
import xyz.redrain.parse.ObjectParse;
import xyz.redrain.parse.ParseUtil;
import xyz.redrain.parse.PropertyEntity;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by RedRain on 2018/11/21.
 *
 * @author RedRain
 * @version 1.0
 */
public class SelectHelper {

    public String selectObjById(Object param) throws Exception {
        return getSelectSql(param, true, null, null);
    }

    public String selectObjByParams(Object param) throws Exception {
        return getSelectSql(param, false, null, null);
    }

    public String selectListByParams(Object param) throws Exception {

        return selectObjByParams(param);
    }

    public String selectListByParamsPages(Map<String, Object> params) throws Exception {
        Object param = params.get("param");
        Integer offset = (Integer) params.get("offset");
        Integer limit = (Integer) params.get("limit");
        if (null == limit || limit < 1) {
            throw new PageParamIsNullException();
        }
        return getSelectSql(param, false, offset, limit);
    }

    public String countByParams(Object param) throws Exception {
        return getSelectSql(param, objectEntity -> " COUNT(*) ", false, null, null);
    }

    private String getSelectSql(Object param, boolean selectById, Integer offset, Integer limit) throws Exception {
        return getSelectSql(param, ParseUtil::getJdbcParamsAndAlias, selectById, offset, limit);
    }

    private String getSelectSql(Object param, Function<ObjectEntity, String> headerFunction, boolean selectById, Integer offset, Integer limit) throws Exception {
        if (null == param) {
            throw new ParamIsNullException();
        }
        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param);
        String headerStr = headerFunction.apply(objectEntity);
        String tableName = ParseUtil.addBackQuote(objectEntity.getTableName());
        ObjectParse.delNullProperty(objectEntity);

        String whereSql;
        String limitStr = null;
        if (selectById) {
            whereSql = objectEntity.getPropertyEntities().stream()
                    .filter(PropertyEntity::isId).findAny()
                    .map(ParseUtil::getEqualParams)
                    .orElseThrow(PrimaryKeyNoExistException::new);
        } else {
            boolean hasLimit = limit != null && limit > 0;
            if (hasLimit) {
                if (offset != null) {
                    limitStr = String.format(" LIMIT %d,%d ", offset, limit);
                } else {
                    limitStr = String.format(" LIMIT %d ", limit);
                }
            }

            whereSql = objectEntity.getPropertyEntities().stream()
                    .map(propertyEntity -> ParseUtil.getEqualParams(propertyEntity, hasLimit ? "param" : null))
                    .collect(Collectors.joining(" AND "));
        }

        return getSelectSql0(headerStr, tableName, whereSql, limitStr);
    }


    private String getSelectSql0(String headStr, String tableName, String whereSql, String limitStr) {

        String sql = String.format("SELECT %s FROM %s", headStr, tableName);
        if (whereSql != null && !"".equals(whereSql.trim())) {
            sql += " WHERE " + whereSql;
        }
        if (limitStr != null) {
            sql += limitStr;
        }
        return sql;
    }


}
