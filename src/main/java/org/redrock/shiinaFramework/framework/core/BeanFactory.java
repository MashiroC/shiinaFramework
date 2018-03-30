package org.redrock.frameworkDemo.framework.core;

import lombok.Data;
import org.redrock.frameworkDemo.framework.annotation.*;
import org.redrock.frameworkDemo.framework.been.Handle;
import org.redrock.frameworkDemo.framework.util.CastUtil;
import org.redrock.frameworkDemo.framework.util.ReflectUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

@Data
public class BeanFactory {
    private Map<String, Handle> handleMap;//url对处理器的映射
    private Map<Class<?>, Object> contrallerMap;
    private Map<Class<?>, Object> componentMap;

    public BeanFactory(ClassLoader classLoader) {
        init(classLoader);
    }

    private void init(ClassLoader classLoader) {
        handleMap = new HashMap<>();
        contrallerMap = new HashMap<>();
        componentMap = new HashMap<>();
        Set<Class<?>> controllerSet = classLoader.getControllerSet();
        for (Class<?> controller : controllerSet) {
            Object object = ReflectUtil.newInstance(controller);
            contrallerMap.put(controller, object);

            Method[] methods = controller.getMethods();
            for (Method method : methods) {
                Action action = method.getAnnotation(Action.class);
                if (action != null) {
                    String methodStr = action.method().name();
                    String uri = action.value();
                    String requestMapping = methodStr + ":" + uri;
                    Handle handle = new Handle(method, this);
                    handle.setController(object);
                    handleMap.put(requestMapping, handle);
                }
            }

            Field[] fields = controller.getDeclaredFields();
            for (Field field : fields) {
                Component component = field.getAnnotation(Component.class);
                if (component != null) {
                    Class<?> clazz = field.getDeclaringClass();
                    Object obj = ReflectUtil.newInstance(clazz);
                    componentMap.put(clazz, obj);
                }
            }
        }
    }


    public Handle getHandle(String requestMapping) {
        Handle handle = handleMap.get(requestMapping);
        if (handle != null) {
            return handle;
        }
        return null;
    }

    public Object getInstanse(Map<String, String> requestParameterMap, Class<?> clazz, Queue<String> varNameQueue) {
        Object object = null;
        if (!ReflectUtil.isPrimitive(clazz)) {
            object = ReflectUtil.newInstance(clazz);
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Ingore isIngore = field.getDeclaredAnnotation(Ingore.class);
                if (isIngore == null) {
                    String name = field.getName();
                    String value = requestParameterMap.get(name);
                    if (value != null) {
                        ReflectUtil.setFieldValue(field, object, value);
                    } else {
                        return null;
                    }
                }
            }
        } else {
            String name = varNameQueue.poll();
            String value = requestParameterMap.get(name);
            object = ReflectUtil.newInstance(clazz, value);
        }
        return object;
    }

    public Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> parameterMap = new HashMap<>();
        Enumeration<String> keys = request.getParameterNames();
        ;
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = request.getParameter(key);
            parameterMap.put(key, value);
        }
        return parameterMap;
    }
}
