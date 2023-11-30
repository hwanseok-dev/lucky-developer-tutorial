package io.lucky.java.thread.step5;

/**
 * synchronized 의미와 Lock의 단위 및 범위
 */

/*
 * 여러개의 쓰레드에서 동시에 실행 불가능
 * = 쓰레드가 한개라도 락을 취하고 있는 순간에는 다른 쓰레드가 들어올 수 없음
 * = 해당 메서드의 실행이 끝나면 락이 해제됨
 *
 * 락은 인스턴스마다 존재함
 * = 서로 다른 인스턴스의 synchronized 메서드는 배타제어에 전혀 상관없음
 */
public class Bank {

    private int money;
    private int moneyWithoutSync;
    private static int staticMoney;

    public Bank(int money) {
        this.money = money;
    }

    public synchronized void deposit(int amount){
        money += amount;
    }

    /*
     * Lock 단위 : 인스턴스
     * Lock 범위 : 메서드
     */
    public synchronized void withdraw(int amount){
        money -= amount;
    }


    /*
     * Lock 단위 : 인스턴스
     * Lock 범위 : synchronized 블럭
     */
    public void withdrawByBlock(int amount){
        money -= amount;
    }

    /*
     * Lock 단위 : 클래스
     * Lock 범위 : 메서드
     */
    public static synchronized void withdrawByClass(int amount){
        staticMoney -= amount;
    }

    /*
     * 여러개의 쓰레드에서 동시에 실행 가능
     */

    public int getMoney() {
        return money;
    }

    public int getMoneyWithoutSync() {
        return moneyWithoutSync;
    }

    public void depositWithoutSync(int amount){
        moneyWithoutSync += amount;
    }

    public void withdrawWithoutSync(int amount){
        moneyWithoutSync -= amount;
    }
}
