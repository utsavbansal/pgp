package com.Irrigation.SAIAFarming.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestContext {
    private static final Log logger = LogFactory.getLog(RequestContext.class);

    private static ThreadLocal store = new ThreadLocal();

    public static void add(String name, Object value) {
        Object o = store.get();
        if (null == o) {
            o = new HashMap<String, Object>();
            store.set(o);
        }
        if (o instanceof HashMap) {
            HashMap<String, Object> data = (HashMap<String, Object>) o;
            data.put(name, value);
        } else {
            logger.error("Invalid context type: "+o.getClass().getName());
        }
    }

    public static String getString(String name) {
        Object o = get(name);
        if (o != null) {
            return (String) o;
        }
        return null;
    }

    public static Object get(String name) {
        Object o = store.get();
        if (null != o) {
            if (o instanceof HashMap) {
                return  ((HashMap<String, Object>) o).get(name);
            }
        } else {
            //logger.error("Context data not set!!");
        }
        return null;
    }

    public static boolean contains(String name) {
        Object o = store.get();
        if (null != o) {
            if (o instanceof HashMap) {
                return  ((HashMap<String, Object>) o).containsKey(name);
            }
        }
        return false;
    }

    public static Map getContextMap() {
        Object o = store.get();
        if (o != null) {
            return (Map) o;
        }
        return Collections.EMPTY_MAP;
    }

    public static void clear() {
        store.set(null);
    }
}
