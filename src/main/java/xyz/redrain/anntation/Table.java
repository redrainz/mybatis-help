package xyz.redrain.anntation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by RedRain on 2018/11/16.
 *
 * @author RedRain
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

    String value() default "";

    /**
     * 属性是否映射成下划线模式
     * true  aB -> a_b
     * false aB -> aB
     *
     * @return
     */
    boolean propertyUseUnderlineStitching() default true;

    /**
     * 属性是否映射成下划线模式
     * true  aB -> a_b
     * false aB -> aB
     * @return
     */
    boolean tableUseUnderlineStitching() default true;
}
