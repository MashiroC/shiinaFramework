package org.redrock.frameworkDemo.framework.been;

import org.redrock.frameworkDemo.framework.annotation.RequestParameter;
import org.redrock.frameworkDemo.framework.core.BeanFactory;
import org.redrock.frameworkDemo.framework.util.ReflectUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@lombok.Data
public class Handle {
    private Object controller;
    private BeanFactory beanFactory;
    private Method method;
    private ArrayList<Class<?>> parameterBeenList;
    private ArrayList<String> varNameList;

    public Handle() {

    }

    public Handle(Method method, BeanFactory beanFactory) {
        init(method, beanFactory);
    }


    public void init(Method method, BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.method = method;
        Parameter[] parameters = method.getParameters();
        parameterBeenList = new ArrayList<>();
        varNameList = new ArrayList<>();
        for (Parameter parameter : parameters) {
            Class<?> clazz = parameter.getType();
            if (!ReflectUtil.isPrimitive(clazz)) {
                parameterBeenList.add(parameter.getType());
            } else {
                parameterBeenList.add(ReflectUtil.getNormalClass(clazz));
                RequestParameter requestParameter = parameter.getAnnotation(RequestParameter.class);
                if (requestParameter != null) {
                    String key = requestParameter.value();
                    varNameList.add(key);
                } else {
                    throw new RuntimeException("参数没指定key!");
                }
            }
        }
    }

    public Object invoke(Map<String, String> requestParameterMap) {
        Queue<String> queue = new PriorityQueue<>();
        queue.addAll(varNameList);
        ArrayList<Object> list = new ArrayList<>();
        Object result = null;
        for (Class<?> clazz : parameterBeenList) {
                Object been = beanFactory.getInstanse(requestParameterMap, clazz, queue);
                if (been != null) {
                    list.add(been);
                } else {
                    Data data = new Data();
                    data.parameterError();
                    return data;
                }

        }
        try {
            result = method.invoke(controller, list.toArray());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
