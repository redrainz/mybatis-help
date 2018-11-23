package com.redrain.parse;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 * @description TODO
 */
public class PropertyEntity {
    private String columnName;
    private String jdbcType;
    private String javaType;
    private String propertyName;
    private boolean id;

    public PropertyEntity() {
    }

    public PropertyEntity(String columnName, String jdbcType, String javaType, String propertyName, boolean id) {
        this.columnName = columnName;
        this.jdbcType = jdbcType;
        this.javaType = javaType;
        this.propertyName = propertyName;
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }
}
