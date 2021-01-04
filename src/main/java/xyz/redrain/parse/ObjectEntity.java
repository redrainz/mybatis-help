package xyz.redrain.parse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 */
public class ObjectEntity {
    private String tableName;
    private List<PropertyEntity> propertyEntities;

    /**
     * 属性是否映射成下划线模式
     * true  aB - a_b
     * false aB - aB
     */
    boolean propertyUseUnderlineStitching;

    /**
     * 表名是否映射成下划线模式
     * true  aB - a_b
     * false aB - aB
     */
    boolean tableUseUnderlineStitching;

    public ObjectEntity() {
        propertyEntities = new ArrayList<>();
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

    public boolean isPropertyUseUnderlineStitching() {
        return propertyUseUnderlineStitching;
    }

    public void setPropertyUseUnderlineStitching(boolean propertyUseUnderlineStitching) {
        this.propertyUseUnderlineStitching = propertyUseUnderlineStitching;
    }

    public boolean isTableUseUnderlineStitching() {
        return tableUseUnderlineStitching;
    }

    public void setTableUseUnderlineStitching(boolean tableUseUnderlineStitching) {
        this.tableUseUnderlineStitching = tableUseUnderlineStitching;
    }
}
