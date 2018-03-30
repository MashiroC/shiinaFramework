package org.redrock.frameworkDemo.demo.controller;

import com.google.gson.Gson;
import org.redrock.frameworkDemo.framework.annotation.Action;
import org.redrock.frameworkDemo.framework.annotation.Controller;
import org.redrock.frameworkDemo.framework.annotation.RequestMethod;
import org.redrock.frameworkDemo.framework.annotation.RequestParameter;
import org.redrock.frameworkDemo.framework.been.*;

import java.io.IOException;

@Controller
public class IndexController {

    private Gson gson = new Gson();

    @Action(value = "/login",method = RequestMethod.GET)
    public Data login(User user) throws IOException {
        Data data = new Data();
        Result result = new Result();
        result.setResult("success");
        result.setInfo("你是普通账户 账号："+ user.getUsername()+" 密码："+ user.getPassword());
        data.setResponseJson(gson.toJson(result));
        return data;
    }

    @Action(value = "/test")
    public Data test(User user,Info info){
        Data data = new Data();
        data.setResponseJson("{\"info\":\"test\"}");
        return new Data();
    }

    @Action(value = "/emm")
    public Data test1(@RequestParameter("int") int a,@RequestParameter("str") String str, User user){
        System.out.println("test1");
        System.out.println(a);
        System.out.println(str);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        return new Data();
    }
}
