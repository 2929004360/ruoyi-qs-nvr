package com.ruoyi.gb28181.common;

import java.lang.annotation.*;

/**
 * @author gaofuwang
 * @version 1.0
 * @date 2022/6/28 14:58
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageElementForCatalog {
    String[] value();

    String subVal() default "";
}
