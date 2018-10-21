package com.erproject.busgo.dependencyinjection.scoped;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


@SuppressWarnings("unused")
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivityScoped {
}
