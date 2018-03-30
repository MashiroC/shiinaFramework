package org.redrock.frameworkDemo.framework.core;

import org.redrock.frameworkDemo.framework.util.CastUtil;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsLoader {
    private Properties properties;
    private static final String FILENAME ="/WEB-INF/config.properties";

    public PropsLoader() {
    }

    public PropsLoader(ServletContext context) {
        init(context);
    }

    public void init(ServletContext context) {
        InputStream in = context.getResourceAsStream(FILENAME);
        if (in != null) {
            properties = new Properties();
            try {
                properties.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("没有找到配置文件");
        }
    }

    public String getString(String key) {
        String result = properties.getProperty(key);
        if(result!=null){
            return result;
        }else{
            return "";
        }
    }

    public int getInt(String key) {
        return CastUtil.castInt(this.properties.getProperty(key));
    }

    public long getLong(String key) {
        return CastUtil.castLong(this.properties.getProperty(key));
    }

}
