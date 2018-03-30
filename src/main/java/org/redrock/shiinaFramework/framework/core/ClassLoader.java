package org.redrock.frameworkDemo.framework.core;

import lombok.Data;
import org.redrock.frameworkDemo.framework.annotation.Component;
import org.redrock.frameworkDemo.framework.annotation.Controller;
import org.redrock.frameworkDemo.framework.util.StringUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

@Data
public class ClassLoader {
    private Set<Class<?>> classSet;
    private Set<Class<?>> controllerSet;
    private Set<Class<?>> componentSet;

    public ClassLoader() {
    }

    public ClassLoader(PropsLoader propsLoader) {
        load(propsLoader);
    }

    public void load(PropsLoader propsLoader) {
        classSet = new HashSet<>();
        String packageName = propsLoader.getString("packageName");
        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageName.replaceAll("\\.", "/"));
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                String protocol = resource.getProtocol();
                if (protocol.equalsIgnoreCase("file")) {
                    String packagePath = resource.getPath();
                    loadClass(packageName, packagePath);
                    loadController();
                    loadComponent();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadClass(String packageName, String packagePath) {
        File[] files = new File(packagePath).listFiles(file -> file.isDirectory() || file.getName().endsWith(".class"));
        if (files != null) {
            if (!StringUtil.isEmpty(packageName) && !StringUtil.isEmpty(packagePath)) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isDirectory()) {
                        String subPackageName = packageName + "." + fileName;
                        String subPackagePath = packagePath + "/" + fileName;
                        loadClass(subPackageName, subPackagePath);
                    } else {
                        fileName = packageName + "." + fileName.substring(0, fileName.lastIndexOf("."));
                        Class<?> clazz = null;
                        try {
                            clazz = Class.forName(fileName);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (clazz != null) {
                            classSet.add(clazz);
                        }
                    }
                }
            }
        }
    }

    private void loadController(){
        controllerSet = new HashSet<>();
        for(Class<?> clazz:classSet){
            Controller controller = clazz.getAnnotation(Controller.class);
            System.out.println(controller);
            if(controller!=null){
                controllerSet.add(clazz);
            }
        }
    }

    private void loadComponent(){
        componentSet = new HashSet<>();
        for(Class<?> clazz : classSet){
            Component component = clazz.getAnnotation(Component.class);
            if(component!=null){
                componentSet.add(clazz);
            }
        }
    }
}
