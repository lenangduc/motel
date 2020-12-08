package vn.yotel.jobsearch247.cms.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TypeConvert {
    public static <T> T parseObj(Object object, Class<T> className) {
        if (object == null) {
            return null;
        }
        if (object.getClass().getName().equals(className.getName())) {
            return (T) object;
        }
        String error = String.format("Object %s has type %s, can't cast to %s", object, object.getClass().getName(), className.getName());
        throw new ClassCastException(error);
    }

    public static <T> T parseObj(Object object, Class<T> className, T defaultValue) {
        if (object == null) {
            return null;
        }
        if (object.getClass().getName().equals(className.getName())) {
            return (T) object;
        }
        log.warn("Object {} has type {}, can't cast to {}", object, object.getClass().getName(), className.getName());
        return defaultValue;

    }
}
