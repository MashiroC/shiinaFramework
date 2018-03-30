package org.redrock.frameworkDemo.framework.been;

import com.google.gson.Gson;
import org.redrock.frameworkDemo.framework.util.StreamUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;

@lombok.Data
public class Data {
    private static Gson gson;
    private String responseJson;

    static {
        gson = new Gson();
    }

    public Data(){}

    public Data(String responseJson){
    this.responseJson=responseJson;
    }

    public void parameterError(){
        this.responseJson="{\"error\":\"parameterError\"}";
    }

    public void write(HttpServletResponse response) throws IOException {
        StreamUtil.writeStream(response.getOutputStream(),this.responseJson);
    }
}
