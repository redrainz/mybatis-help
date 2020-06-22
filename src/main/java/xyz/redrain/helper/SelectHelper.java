package xyz.redrain.helper;

import com.sun.org.apache.regexp.internal.RE;
import xyz.redrain.exception.PageParamIsNullException;
import xyz.redrain.exception.ParamIsNullException;
import xyz.redrain.exception.PrimaryKeyNoExsitException;
import xyz.redrain.exception.SelectConditionNoExsitException;
import xyz.redrain.parse.ObjectEntity;
import xyz.redrain.parse.ObjectParse;
import xyz.redrain.parse.ParseUtil;
import xyz.redrain.parse.PropertyEntity;

import java.util.Map;
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
        if (null == offset || null == limit || limit < 1) {
            throw new PageParamIsNullException();
        }
        return getSelectSql(param, false, offset, limit);
    }

    private String getSelectSql(Object param, boolean isId, Integer offset, Integer limit) throws Exception {
        if (null == param) {
            throw new ParamIsNullException();
        }
        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param);
        String headerStr = ParseUtil.getJdbcParamsAndAlias(objectEntity);
        String tableName = ParseUtil.addBackQuote(objectEntity.getTableName());
        String whereSql;
        String limitStr = null;

        ObjectParse.delNullProperty(objectEntity);
        if (isId) {
            whereSql = objectEntity.getPropertyEntities().stream()
                    .filter(PropertyEntity::isId).findAny()
                    .map(ParseUtil::getEqualParams)
                    .orElseThrow(PrimaryKeyNoExsitException::new);
        } else {
            ObjectParse.useIndices(objectEntity);
            whereSql = objectEntity.getPropertyEntities().stream()
                    .map(ParseUtil::getEqualParams)
                    .collect(Collectors.joining(" AND "));

            if (offset != null && limit != null && limit > 0) {
                limitStr = String.format(" LIMIT %d,%d ", offset, limit);
            }

        }

        return getSelectSql0(headerStr, tableName, whereSql, limitStr);
    }


    private String getSelectSql0(String headStr, String tableName, String whereSql, String limitStr) throws SelectConditionNoExsitException {

        if (limitStr == null && (whereSql == null || "".equals(whereSql.trim()))) {
            throw new SelectConditionNoExsitException();
        }
        String sql = String.format("SELECT %s FROM %s", headStr, tableName);
        if (whereSql == null || "".equals(whereSql.trim())) {
            sql += " WHERE " + whereSql;
        }
        if (limitStr != null) {
            sql += limitStr;
        }
        return sql;
    }


}
