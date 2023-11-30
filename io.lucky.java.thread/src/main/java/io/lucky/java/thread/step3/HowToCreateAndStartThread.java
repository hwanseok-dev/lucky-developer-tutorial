package io.lucky.java.thread.step3;


import io.lucky.java.thread.util.DateUtil;
import io.lucky.java.thread.util.ThreadUtil;

class HowToCreateAndStartThread {
    public static void main(String[] args) {
        /*
         * MyThread != 쓰레드
         * MyThread == 인스턴스
         * MyThread 인스턴스 생성 != 쓰레드 생성
         * MyThread#start() 호출 == 쓰레드 생성 및 실행
         */
        MyThread withClass = new MyThread();
        Thread withRunnable = new Thread() {
            @Override
            public void run() {
                doDiskIOStuff();
            }
        };

        withClass.start();
        withRunnable.start();
    }

    private static void doDiskIOStuff(){
        final int TIMEOUT_IN_SECOND = 5;
        for (int i = 0; i < TIMEOUT_IN_SECOND; i++) {
            ThreadUtil.sleep(DateUtil.MILLIS_PER_SECOND);
            System.out.printf("doing disk io stuff ... %d/%d\n", i, TIMEOUT_IN_SECOND);
        }
    }

    /**
     * doing network stuff ... 0/3
     * doing disk io stuff ... 0/5
     * doing disk io stuff ... 1/5
     * doing network stuff ... 1/3
     * doing network stuff ... 2/3
     * doing disk io stuff ... 2/5
     * doing disk io stuff ... 3/5
     * doing disk io stuff ... 4/5
     *
     * Process finished with exit code 0
     */
}
