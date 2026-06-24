package com.otavio.jakarta.cdi;

import jakarta.enterprise.util.TypeLiteral;

import java.lang.annotation.Annotation;

public interface ConditionContext {

    <T> boolean hasBean(Class<T> type, Annotation... qualifiers);

    <T> boolean hasBean(TypeLiteral<T> type, Annotation... qualifiers);

    <T> boolean hasClass(Class<T> clazz);

    boolean hasClasspathResource(String path);
}
