package com.redrain.parse;

import java.util.List;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 * @description TODO
 */
public class ObjectEntity {
    private String tableName;
    private List<PropertyEntity> propertyEntities;

    public ObjectEntity() {
    }

    public ObjectEntity(String tableName, List<PropertyEntity> propertyEntities) {
        this.tableName = tableName;
        this.propertyEntities = propertyEntities;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<PropertyEntity> getPropertyEntities() {
        return propertyEntities;
    }

    public void setPropertyEntities(List<PropertyEntity> propertyEntities) {
        this.propertyEntities = propertyEntities;
    }
}
