package com.redrain.parse;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 * @description TODO
 */
public class ParseUtil {

    /**
     * userName->user_name
     *
     * @param target
     * @return
     * @throws Exception
     */
    public static String underlineStitching(String target) throws Exception {
        if (null == target || "".equals(target.trim())) {
            return null;
        }

        char[] chars = target.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'A' && chars[i] <= 'Z') {
                target = target.replace(String.valueOf(chars[i]), "_" + String.valueOf(chars[i]).toLowerCase());
                if (i == 0) {
                    target = target.substring(1);
                }
            }
        }
        return target;
    }

    /**
     * user_name->userName
     *
     * @param target
     * @return
     * @throws Exception
     */
    public static String toUpperCase(String target) throws Exception {
        if (null == target || "".equals(target.trim())) {
            return null;
        }

        char[] chars = target.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '_' && i < chars.length - 1) {
                target = target.replace("_" + chars[i + 1], String.valueOf(chars[i + 1]).toUpperCase());
                if (i == chars.length - 1) {
                    target = target.substring(0, target.length() - 1);
                }
            }
        }
        return target;
    }

    public static String getProperty(String value, String defaultValue) {
        if (null == value || "".equals(value.trim())) {
            return defaultValue;
        }
        return value;
    }

    public static String getSqlParams(ObjectEntity objectEntity) {
        StringBuilder paramsStr = new StringBuilder();
        for (PropertyEntity propertyEntity : objectEntity.getPropertyEntities()) {
            paramsStr.append("#{")
                    .append(propertyEntity.getPropertyName())
                    .append(null != propertyEntity.getJdbcType()
                            ? ", jdbcType=" + propertyEntity.getJdbcType().toUpperCase()
                            : ", javaType=" + propertyEntity.getJavaType().toUpperCase())
                    .append("}, ");
        }
        if (paramsStr.length() > 1) {
            paramsStr.deleteCharAt(paramsStr.length() - 2);
        }
        return paramsStr.toString();
    }

    public static String getJdbcParams(ObjectEntity objectEntity) {
        StringBuilder paramsStr = new StringBuilder();
        for (PropertyEntity propertyEntity : objectEntity.getPropertyEntities()) {
            paramsStr.append(propertyEntity.getColumnName())
                    .append(", ");
        }
        if (paramsStr.length() > 1) {
            paramsStr.deleteCharAt(paramsStr.length() - 2);
        }
        return paramsStr.toString();
    }

    public static String getJdbcParamsAndAlias(ObjectEntity objectEntity) {
        StringBuilder paramsStr = new StringBuilder();
        for (PropertyEntity propertyEntity : objectEntity.getPropertyEntities()) {
            paramsStr.append(propertyEntity.getColumnName())
                    .append(" as ")
                    .append(" '")
                    .append(propertyEntity.getPropertyName())
                    .append("' ")
                    .append(", ");
        }
        if (paramsStr.length() > 1) {
            paramsStr.deleteCharAt(paramsStr.length() - 2);
        }
        return paramsStr.toString();
    }


    public static String getEqualParamsFromObject(PropertyEntity propertyEntity) {
        StringBuilder param = new StringBuilder();
        param.append(propertyEntity.getColumnName())
                .append(" = ")
                .append("#{")
                .append("param." + propertyEntity.getPropertyName())
                .append(null != propertyEntity.getJdbcType()
                        ? ", jdbcType=" + propertyEntity.getJdbcType().toUpperCase()
                        : ", javaType=" + propertyEntity.getJavaType().toUpperCase())
                .append("}");
        return param.toString();
    }

    public static String getEqualParams(PropertyEntity propertyEntity) {
        StringBuilder param = new StringBuilder();
        param.append(propertyEntity.getColumnName())
                .append(" = ")
                .append("#{")
                .append(propertyEntity.getPropertyName())
                .append(null != propertyEntity.getJdbcType()
                        ? ", jdbcType=" + propertyEntity.getJdbcType().toUpperCase()
                        : ", javaType=" + propertyEntity.getJavaType().toUpperCase())
                .append("}");
        return param.toString();
    }
}
