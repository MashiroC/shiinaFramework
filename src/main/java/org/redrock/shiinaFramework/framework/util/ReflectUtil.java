package org.redrock.frameworkDemo.framework.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflectUtil {
    public static Object newInstance(Class<?> clazz) {
        Object object = null;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static Object newInstance(Class<?> clazz, String value) {
        Object object = null;
        try {
            Constructor constructor = clazz.getDeclaredConstructor(String.class);
            object = constructor.newInstance(value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            object = -1;
        }
        return object;
    }

    public static boolean isPrimitive(Class<?> clazz) {
        if (!clazz.isPrimitive()) {
            switch (clazz.getName()) {
                case "java.lang.Integer":
                    return true;
                case "java.lang.Long":
                    return true;
                case "java.lang.Double":
                    return true;
                case "java.lang.Float":
                    return true;
                case "java.lang.Boolean":
                    return true;
                case "java.lang.Character":
                    return true;
                case "java.lang.String":
                    return true;
                default:
                    return false;
            }
        }
        return true;
    }

    public static Class<?> getNormalClass(Class<?> clazz) {
        String name = clazz.getName();
        switch (name) {
            case "int":
                return Integer.class;
            case "long":
                return Long.class;
            case "double":
                return Double.class;
            case "float":
                return Float.class;
            case "char":
                return Character.class;
            case "boolean":
                return Boolean.class;
        }
        return clazz;
    }

    public static void setFieldValue(Field field, Object obj, Object value) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
