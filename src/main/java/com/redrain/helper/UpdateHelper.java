package com.redrain.helper;

import com.redrain.parse.ObjectEntity;
import com.redrain.parse.ObjectParse;
import com.redrain.parse.ParseUtil;
import com.redrain.parse.PropertyEntity;

/**
 * Created by RedRain on 2018/11/19.
 *
 * @author RedRain
 * @version 1.0
 * @description TODO
 */
public class UpdateHelper {

    public String updateObjById(Object param) throws Exception {
        if (null == param) {
            throw new Exception();
        }
        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param.getClass());
        return getUpdateSql(objectEntity);
    }

    private String getUpdateSql(ObjectEntity objectEntity) {
        StringBuilder updateStr = new StringBuilder();
        int idIndex = -1;
        updateStr.append("update ")
                .append(objectEntity.getTableName())
                .append(" set \n");
        for (int i = 0; i < objectEntity.getPropertyEntities().size(); i++) {
            PropertyEntity propertyEntity = objectEntity.getPropertyEntities().get(i);
            if (propertyEntity.isId()) {
                idIndex = i;
            } else {
                updateStr.append(ParseUtil.getEqualParams(propertyEntity))
                        .append(", ");
            }
        }
        if (updateStr.length() > 1) {
            updateStr.deleteCharAt(updateStr.length() - 2);
        }
        if (idIndex > -1) {
            updateStr.append(" where ")
                    .append(ParseUtil.getEqualParams(objectEntity.getPropertyEntities().get(idIndex)));
        }
        return updateStr.toString();
    }

    public String updateObjSelectiveById(Object param) throws Exception {
        if (null == param) {
            throw new Exception();
        }
        ObjectEntity objectEntity = ObjectParse.getObjectEntity(param.getClass());
        ObjectParse.delNullProperty(param, objectEntity);
        return getUpdateSql(objectEntity);
    }

}
