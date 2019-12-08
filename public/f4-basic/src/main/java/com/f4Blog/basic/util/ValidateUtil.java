package com.f4Blog.basic.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ValidateUtil {
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else {
            if (object instanceof String) {
                if (object.toString().trim().equals("")) {
                    return true;
                }
            } else if (object instanceof List) {
                if (((List)object).size() == 0) {
                    return true;
                }
            } else if (object instanceof Map) {
                if (((Map)object).size() == 0) {
                    return true;
                }
            } else if (object instanceof Set) {
                if (((Set)object).size() == 0) {
                    return true;
                }
            } else if (object instanceof Object[]) {
                if (((Object[])((Object[])object)).length == 0) {
                    return true;
                }
            } else if (object instanceof int[]) {
                if (((int[])((int[])object)).length == 0) {
                    return true;
                }
            } else if (object instanceof long[] && ((long[])((long[])object)).length == 0) {
                return true;
            }

            return false;
        }
    }

    public static boolean isOneEmpty(Object... objects) {
        for(int i = 0; i < objects.length; ++i) {
            Object o = objects[i];
            if (isEmpty(o)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isAllEmpty(Object... objects) {
        for(int i = 0; i < objects.length; ++i) {
            Object o = objects[i];
            if (!isEmpty(o)) {
                return false;
            }
        }
        return true;
    }

}
