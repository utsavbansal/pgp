package com.Irrigation.SAIAFarming;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface SampleRequest {
    String value();
}
