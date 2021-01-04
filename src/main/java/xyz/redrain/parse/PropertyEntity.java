package xyz.redrain.parse;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 */
public class PropertyEntity {
    private String columnName;
    private String jdbcType;
    private String javaType;
    private String propertyName;
    private Object propertyValue;
    /**
     * 是否允许该属性更新
     */
    private boolean updateSetNullFlag;
    /**
     * 是否主键
     */
    private boolean id;

    public PropertyEntity() {
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

    public boolean isUpdateSetNullFlag() {
        return updateSetNullFlag;
    }

    public void setUpdateSetNullFlag(boolean updateSetNullFlag) {
        this.updateSetNullFlag = updateSetNullFlag;
    }

    public Object getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(Object propertyValue) {
        this.propertyValue = propertyValue;
    }
}
