package io.lucky.java.thread.util;

public class StopWatch {

    private long stime; // start time
    private long etime; // end time

    public StopWatch(){
    }

    public void start(){
        this.stime = DateUtil.now();
    }

    /**
     * @return elapsed time in ms
     */
    public long stop(){
        this.etime = DateUtil.now();
        return etime - stime;
    }

}
