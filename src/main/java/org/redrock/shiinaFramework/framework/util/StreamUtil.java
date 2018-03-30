package org.redrock.frameworkDemo.framework.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class StreamUtil {
    public static void writeStream(OutputStream out,String text){
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(out)
            );
        try {
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
