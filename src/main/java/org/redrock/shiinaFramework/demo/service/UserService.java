package org.redrock.frameworkDemo.demo.service;

import org.redrock.frameworkDemo.framework.annotation.Component;

@Component
public class UserService {
    public void doSomething(){
        System.out.println("做了一件事");
    }
}
