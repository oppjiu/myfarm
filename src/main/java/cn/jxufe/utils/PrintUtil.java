package cn.jxufe.utils;

/**
 * @create: 2022-06-03 00:17
 * @author: lwz
 * @description:
 **/
public class PrintUtil {
    public static void println(String str) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        System.err.println(str + " : ---" + stackTrace[stackTrace.length > 2 ? 1 : 0].toString());
    }
}


