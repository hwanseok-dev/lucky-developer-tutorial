package io.lucky.java.thread.step2;

import io.lucky.java.thread.util.DateUtil;
import io.lucky.java.thread.util.StopWatch;
import io.lucky.java.thread.util.ThreadUtil;

class WhyMultiThread2 {

    public static void main(String[] args) {
        StopWatch sw = new StopWatch();
        sw.start();
        System.out.println("Hello World");
        new Thread() {
            @Override
            public void run() {
                doNetworkStuff();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                doDiskIOStuff();
            }
        }.start();
        long elapsed = sw.stop();
        System.out.println("Elapsed time : " + elapsed + " ms");
    }

    private static void doNetworkStuff(){
        final int TIMEOUT_IN_SECOND = 3;
        for (int i = 0; i < TIMEOUT_IN_SECOND; i++) {
            ThreadUtil.sleep(DateUtil.MILLIS_PER_SECOND);
            System.out.printf("doing network stuff ... %d/%d\n", i, TIMEOUT_IN_SECOND);
        }
    }

    private static void doDiskIOStuff(){
        final int TIMEOUT_IN_SECOND = 5;
        for (int i = 0; i < TIMEOUT_IN_SECOND; i++) {
            ThreadUtil.sleep(DateUtil.MILLIS_PER_SECOND);
            System.out.printf("doing disk io stuff ... %d/%d\n", i, TIMEOUT_IN_SECOND);
        }
    }

    /*
     * Hello World
     * Elapsed time : 2 ms
     * doing disk io stuff ... 0/5
     * doing network stuff ... 0/3
     * doing disk io stuff ... 1/5
     * doing network stuff ... 1/3
     * doing network stuff ... 2/3
     * doing disk io stuff ... 2/5
     * doing disk io stuff ... 3/5
     * doing disk io stuff ... 4/5
     */
}
