package com.qpp.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by SZ_it123 on 2014/9/28.
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
//注解使用的约束类
@Constraint(validatedBy=KeyInTableCheck.class)
@Documented
public @interface KeyInTable {
    String message() default "{data.inValidLinkTable}";
    Class<?>[] groups() default {};
    //定义级别条件的严重级别
    Class<? extends Payload>[] payload() default {};
    Class value();
    String column() default "";
}
