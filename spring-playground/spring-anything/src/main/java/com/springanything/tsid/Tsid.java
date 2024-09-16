package com.springanything.tsid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.annotations.IdGeneratorType;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@IdGeneratorType(TimeSortedIdGenerator.class)
public @interface Tsid {

}
