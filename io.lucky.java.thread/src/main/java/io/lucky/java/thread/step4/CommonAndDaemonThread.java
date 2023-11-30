package io.lucky.java.thread.step4;

class CommonAndDaemonThread {
    public static void main(String[] args) {
        /*
         * 프로그램이 종료되지 않는다면 common과 daemon은 각각 3초/5초동안 수행됨
         */
        MyCommonThread commonThread = new MyCommonThread();
        MyDaemonThread daemonThread = new MyDaemonThread();
        commonThread.start();
        daemonThread.start();
    }

    /**
     * doing common stuff ... 0/3
     * doing daemon stuff ... 0/5
     * doing common stuff ... 1/3
     * doing daemon stuff ... 1/5
     * doing common stuff ... 2/3
     * doing daemon stuff ... 2/5
     *
     * Process finished with exit code 0
     */
}
