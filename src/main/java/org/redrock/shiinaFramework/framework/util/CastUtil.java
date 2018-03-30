package org.redrock.frameworkDemo.framework.util;

public class CastUtil {
    public static int castInt(String text) {
        int result;
        try {
            result = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            result = -1;
        }
        return result;
    }

    public static long castLong(String text) {
        long result;
        try{
            result=Long.parseLong(text);
        }catch (NumberFormatException e){
            result=-1L;
        }
        return result;
    }
}
