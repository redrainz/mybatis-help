package xyz.redrain.parse;

import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 */
public class ParseUtil {

    public static String underlineStitching(String target) {
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


    public static String toUpperCase(String target) {
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

    public static String getProperty(String value, Supplier<String> supplier) {
        if (null == value || "".equals(value.trim())) {
            return supplier.get();
        }
        return value;
    }


    public static String getSqlParams(ObjectEntity objectEntity) {

        return objectEntity.getPropertyEntities().stream()
                .map(propertyEntity ->
                        String.format("#{ %s , %s }", propertyEntity.getPropertyName(),
                                propertyEntity.getJdbcType() != null
                                        ? "jdbcType = " + propertyEntity.getJdbcType().toUpperCase()
                                        : "javaType = " + propertyEntity.getJavaType()))
                .collect(Collectors.joining(" , "));
    }

    public static String getJdbcParams(ObjectEntity objectEntity) {
        return objectEntity.getPropertyEntities().stream()
                .map(propertyEntity -> addBackQuote(propertyEntity.getColumnName()))
                .collect(Collectors.joining(" , "));
    }

    public static String getJdbcParamsAndAlias(ObjectEntity objectEntity) {

        return objectEntity.getPropertyEntities().stream()
                .map(propertyEntity -> String.format("%s AS '%s'", addBackQuote(propertyEntity.getColumnName()),
                        propertyEntity.getPropertyName()))
                .collect(Collectors.joining(" , "));
    }


    public static String getEqualParams(PropertyEntity propertyEntity, String paramName) {

        if (paramName != null && !"".equals(paramName.trim())) {
            paramName += ".";
        } else {
            paramName = "";
        }
        return String.format(" %s = #{%s,%s} ",
                addBackQuote(propertyEntity.getColumnName()),
                paramName + propertyEntity.getPropertyName(),
                propertyEntity.getJdbcType() != null
                        ? "jdbcType = " + propertyEntity.getJdbcType().toUpperCase()
                        : "javaType = " + propertyEntity.getJavaType());
    }

    public static String getEqualParams(PropertyEntity propertyEntity) {

        return String.format(" %s = #{%s,%s} ",
                addBackQuote(propertyEntity.getColumnName()),
                propertyEntity.getPropertyName(),
                propertyEntity.getJdbcType() != null
                        ? "jdbcType=" + propertyEntity.getJdbcType().toUpperCase()
                        : "javaType=" + propertyEntity.getJavaType());
    }


    /**
     * 加上反引号
     *
     * @param s String
     * @return String
     */
    public static String addBackQuote(String s) {
        if (s == null || "".equals(s.trim())) {
            return s;
        }
        return String.format("`%s`", s);
    }

    /**
     * 去掉反引号
     *
     * @param s String
     * @return String
     */
    public static String removeBackQuote(String s) {

        return s == null ? null : s.replaceAll("`", "");
    }


}
