package com.qpp.model;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by qpp on 8/14/2014.
 */
public class ModelUtil {
    public static Map<String,String> getMap(ModelMap model) {
        Map<String, String> resultMap = new HashMap<String, String>();
        Field[] fields = model.getClass().getDeclaredFields();
        Object value = null;
        for (Field field : fields) {
            field.setAccessible(true);
            Class type=field.getType();
            try {
                value = field.get(model);
                if(!(value instanceof HashSet)&&!(StringUtils.startsWith(type.getName(),"com.qpp.model"))) {
//                    value = String.valueOf(field.get(model));
                    if (value != null && !String.valueOf(value).trim().equals(""))
                        resultMap.put(field.getName(), String.valueOf(value));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }
}
