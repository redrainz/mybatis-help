package xyz.redrain.helper;

import xyz.redrain.parse.ObjectEntity;
import xyz.redrain.parse.ObjectParse;
import xyz.redrain.parse.ParseUtil;
import xyz.redrain.parse.PropertyEntity;

import java.util.Map;

/**
 * Created by RedRain on 2018/11/21.
 *
 * @author RedRain
 * @version 1.0

 */
public class SelectHelper {

    public String selectObjById(Object param) throws Exception {
        if (null == param) {
            throw new Exception();
        }
        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param.getClass());
        return selectObjById1(objectEntity);
    }

    private String selectObjById1(ObjectEntity objectEntity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getHeaderStr(objectEntity));
        for (PropertyEntity propertyEntity : objectEntity.getPropertyEntities()) {
            if (propertyEntity.isId()) {
                stringBuilder.append(ParseUtil.getEqualParams(propertyEntity));
            }
        }
        return stringBuilder.toString();
    }

    public String selectObjByParams(Object param) throws Exception {
        if (null == param) {
            throw new Exception();
        }
        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param.getClass());
        return selectObjByParams1(param, objectEntity);
    }

    private String selectObjByParams1(Object param, ObjectEntity objectEntity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getHeaderStr(objectEntity));
        ObjectParse.delNullProperty(param, objectEntity);
        ObjectParse.useIndexs(objectEntity);
        for (PropertyEntity propertyEntity : objectEntity.getPropertyEntities()) {
            stringBuilder.append(ParseUtil.getEqualParams(propertyEntity))
                    .append(" and ");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        }
        return stringBuilder.toString();
    }

    public String selectListByParams(Object param) throws Exception {

        return selectObjByParams(param);
    }


    public String selectListByParamsPages(Map<String, Object> params) throws Exception {
        Object param = params.get("param");
        Integer offset = (Integer) params.get("offset");
        Integer limit = (Integer) params.get("limit");
        if (null == param || null == offset || null == limit) {
            throw new Exception();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(selectListByParamsPages1(param))
                .append(" limit ")
                .append(offset)
                .append(" , ")
                .append(limit);
        return stringBuilder.toString();
    }

    private String selectListByParamsPages1(Object param) throws Exception {
        if (null == param) {
            throw new Exception();
        }
        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param.getClass());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getHeaderStr(objectEntity));
        ObjectParse.delNullProperty(param, objectEntity);
        ObjectParse.useIndexs(objectEntity);
        for (PropertyEntity propertyEntity : objectEntity.getPropertyEntities()) {
            stringBuilder.append(ParseUtil.getEqualParamsFromObject(propertyEntity))
                    .append(" and ");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
        }
        return stringBuilder.toString();
    }

    private String getHeaderStr(ObjectEntity objectEntity) {
        StringBuilder selectStr = new StringBuilder();
        selectStr.append("select ")
                .append(ParseUtil.getJdbcParamsAndAlias(objectEntity))
                .append(" from ")
                .append(objectEntity.getTableName())
                .append(" where ");
        return selectStr.toString();
    }


}
