package io.lucky.java.thread.step3;

import io.lucky.java.thread.util.DateUtil;
import io.lucky.java.thread.util.ThreadUtil;

/**
 * Thread를 실행하는 방법 1 : extends Thread
 * 단, Thread#run()을 직접 실행하지 않고 Thread.start()를 호출해야 함
 */
class MyThread extends Thread {

    @Override
    public void run() {
        doNetworkStuff();
    }

    private static void doNetworkStuff(){
        final int TIMEOUT_IN_SECOND = 3;
        for (int i = 0; i < TIMEOUT_IN_SECOND; i++) {
            ThreadUtil.sleep(DateUtil.MILLIS_PER_SECOND);
            System.out.printf("doing network stuff ... %d/%d\n", i, TIMEOUT_IN_SECOND);
        }
    }
}
