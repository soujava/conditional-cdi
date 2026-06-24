package com.otavio.jakarta.cdi.extension;

import com.otavio.jakarta.cdi.ConditionContext;
import jakarta.enterprise.util.TypeLiteral;

import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;

class DefaultConditionContext implements ConditionContext {

    private static final Logger LOGGER = Logger.getLogger(RequiresConditionExtension.class.getName());

    @Override
    public <T> boolean hasBean(Class<T> type, Annotation... qualifiers) {
        LOGGER.log(Level.FINE,
                "ConditionContext.hasBean(Class, Annotation...) is not implemented "
                        + "in this prototype. Returning false for type {0}.",
                type.getName());
        return false;
    }

    @Override
    public <T> boolean hasBean(TypeLiteral<T> type, Annotation... qualifiers) {
        LOGGER.log(Level.FINE,
                "ConditionContext.hasBean(TypeLiteral, Annotation...) is not implemented "
                        + "in this prototype. Returning false for type {0}.",
                type.getType());
        return false;
    }

    @Override
    public <T> boolean hasClass(Class<T> type) {
        return type != null;
    }

    @Override
    public boolean hasClasspathResource(String path) {
        if (path == null || path.isBlank()) {
            return false;
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (classLoader == null) {
            classLoader = RequiresConditionExtension.class.getClassLoader();
        }

        return classLoader.getResource(path) != null;
    }
}