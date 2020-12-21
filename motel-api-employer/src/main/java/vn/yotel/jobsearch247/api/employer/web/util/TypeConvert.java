package vn.yotel.jobsearch247.api.employer.web.util;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

@Slf4j
public class TypeConvert {
    public static <T> T parseObj(Object object, Class<T> className) {
        if (object == null) {
            return null;
        }
        if (object.getClass().getName().equals(className.getName())) {
            return (T) object;
        }
        String error = String.format("Object %s has type %s, can't cast to %s",
                object, object.getClass().getName(), className.getName());
        throw new ClassCastException(error);
    }

    public static <T> T parseObj(Object object, Class<T> className, T defaultValue) {
        if (object == null) {
            return null;
        }
        if (object.getClass().getName().equals(className.getName())) {
            return (T) object;
        }
        log.error("Object {} has type {}, cant cast to {}", object, object.getClass().getName(), className.getName());
        return defaultValue;
    }

    public static JSONObject convertStringToJsonObj(String jsonString) {
        try {
            return new JSONObject(jsonString);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getAttrValueFromJsonObj(JSONObject jsonObject, String key) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return null;
        }
        try {
            return jsonObject.getString(key);
        } catch (Exception ex) {
            return null;
        }
    }
}
