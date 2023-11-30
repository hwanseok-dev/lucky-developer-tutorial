package io.lucky.java.thread.step4;

import io.lucky.java.thread.util.DateUtil;
import io.lucky.java.thread.util.ThreadUtil;

/**
 * Thread를 실행하는 방법 1 : extends Thread
 * 단, Thread#run()을 직접 실행하지 않고 Thread.start()를 호출해야 함
 */
class MyDaemonThread extends Thread {

    public MyDaemonThread() {
        this.setName("MyDaemonThread");
        this.setDaemon(true);
    }

    @Override
    public void run() {
        doDaemonStuff();
    }

    private static void doDaemonStuff(){
        final int TIMEOUT_IN_SECOND = 5;
        for (int i = 0; i < TIMEOUT_IN_SECOND; i++) {
            ThreadUtil.sleep(DateUtil.MILLIS_PER_SECOND);
            System.out.printf("doing daemon stuff ... %d/%d\n", i, TIMEOUT_IN_SECOND);
        }
    }
}
