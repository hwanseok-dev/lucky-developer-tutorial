package io.lucky.java.thread.util;

public class ThreadUtil {

    private ThreadUtil(){}

    /**
     * 현재 쓰레드를 지정된 시간동안 멈춤
     * 얼마나 세세하게 멈춤을 제어할 수 있는지는 Java 처리계에 달려있으며 100% 정확한 시간동안 sleep함을 보장하지 않음
     * @param ms
     */
    public static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // ignored
            // TODO 5장에서 보강
        }
    }

    public static String getName(){
        return Thread.currentThread().getName();
    }
}
