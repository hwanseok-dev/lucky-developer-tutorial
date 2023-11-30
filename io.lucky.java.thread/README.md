# Java Thread Tutorial

## STEP 1. 메인 쓰레드, 백그라운드 쓰레드

자바 프로그램을 실행하면 쓰레드가 항상 실행됩니다. GC 등을 위해서 백그라운드에서 실행되는 쓰레드도 존재합니다.  

```java
class HelloWorld {

    // main 메서드를 실행시키는 하나의 쓰레드가 반드시 동작함
    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    // GC 등 백그라운드 쓰레드도 함께 실행됨

    /**
     * Hello World
     *
     * Process finished with exit code 0
     */
}
```

## STEP 2. 멀티 쓰레드가 필요한 이유

아래의 코드에서 네트워크 작업은 3초, 디스크 작업은 5초가 소요되도록 설정했습니다. 멀티 쓰레드 없이 순차적으로 수행하면 3 + 5 = 8초가 걸립니다.  

```java
class WhyMultiThread {

    public static void main(String[] args) {
        StopWatch sw = new StopWatch();
        sw.start();
        System.out.println("Hello World");
        doNetworkStuff();
        doDiskIOStuff();
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

    /**
     * Hello World
     * doing network stuff ... 0/3
     * doing network stuff ... 1/3
     * doing network stuff ... 2/3
     * doing network stuff ... 0/5
     * doing network stuff ... 1/5
     * doing network stuff ... 2/5
     * doing network stuff ... 3/5
     * doing network stuff ... 4/5
     * Elapsed time : 8065 ms
     */
}
```

멀티 쓰레드를 사용하면 3초가 걸리는 작업과 5초가 걸리는 작업이 동시에 수행됩니다.
전체 작업에 걸리는 시간이 8초 -> 5초로 줄었습니다.  

```java
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

    /**
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
```

## STEP 3. 쓰레드를 생성하고 실행하는 방법

쓰레드를 생성할 때는 2가지 방법을 사용할 수 있습니다.  

Thread를 extends해서 사용하거나  

```java
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
```

직접 쓰레드 클래스를 생성해서 사용하거나 

```java
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
```

Runnable을 전달해서 생성할 수도 있습니다. 

```java
Thread withRunnable2 = new Thread(() -> doDiskIOStuff());
```

## STEP 4. 일반 쓰레드와 데몬 쓰레드의 차이점

자바 프로그램은 모든 일반 쓰레드가 종료될 때까지 기다렸다가 종료됩니다.  
아래의 코드에서 commonThread는 3초, daemonThread는 5초동안 수행되도록 설정되었습니다.
일반적인 상황이라면 3초 작업과 5초 작업이 모두 끝나는 5초 뒤에 프로그램이 종료됩니다.  

하지만 아래의 예시에서는 3초만에 작업이 끝납니다.

```java
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
```

```java
class MyCommonThread extends Thread {

    public MyCommonThread() {
        this.setName("MyCommonThread");
        this.setDaemon(false);
    }

    @Override
    public void run() {
        doCommonStuff();
    }

    private static void doCommonStuff(){
        final int TIMEOUT_IN_SECOND = 3;
        for (int i = 0; i < TIMEOUT_IN_SECOND; i++) {
            ThreadUtil.sleep(DateUtil.MILLIS_PER_SECOND);
            System.out.printf("doing common stuff ... %d/%d\n", i, TIMEOUT_IN_SECOND);
        }
    }
}
```

```java
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
```

이 이유는 자바 프로그램은 데몬 쓰레드가 종료될 때까지 기다리지 않기 때문입니다.  

```txt
자바 프로그램은 모든 일반 쓰레드가 종료될 때까지 기다렸다가 종료됩니다
= 자바 프로그램은 데몬 쓰레드가 종료될 때까지 기다리지 않습니다
```

## STEP 5. synchronized 그리고 lock

여러 개의 쓰레드가 하나의 객체에 동시에 접근하면 동시성 문제가 생깁니다.
이를 위해서 자바에는 synchronized가 있습니다. 그런데 synchronized를 제대로 사용하려면 lock의 범위를 제대로 이해해야 합니다.

여러개의 쓰레드에서 동시에 실행 불가능하다는 것은 쓰레드가 한개라도 락을 취하고 있는 순간에는 다른 쓰레드가 들어올 수 없다는 뜻입니다.
해당 메서드의 실행이 끝나면 그 메스드를 실행한 쓰레드는 락이 해제됩니다.   

락은 인스턴스마다 존재합니다. 서로 다른 인스턴스의 synchronized 메서드는 배타제어에 전혀 상관없습니다.  

```java
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
```

아래의 예시에서 두 고객이 하나의 은행에 돈을 넣고 빼고있습니다. 같은 은행 인스턴스에 대해서 행위를 하고 있으므로,
두 쓰레드가 모두 끝난 시점에는 은행의 잔고는 0이어야 합니다.  

synchronized가 걸린 메서드는 동기화가 되고, synchronized가 없는 메서드는 동기화가 되지 않습니다.  

두 개의 쓰레드가 모두 끝났을 때에는 항상 moneyWithSync만 zeroSum이 보장된다. 

```java
class Main {
    public static void main(String[] args) {
        Bank bank = new Bank(0);
        final int COUNT = 100_000;
        Thread customerA = new Thread() {
            @Override
            public void run() {
                this.setName("customerA");
                for (int i = 0; i < COUNT; i++) {
                    deposit(bank, 100);
                }
                print(bank);
            }
        };
        Thread customerB = new Thread() {
            @Override
            public void run() {
                this.setName("customerB");
                for (int i = 0; i < COUNT; i++) {
                    withdraw(bank, 100);
                }
                print(bank);
            }
        };

        /**
         * 서로 영향을 주지 않는 독립적인 행위를 동시에 수행할 수 있다.
         * 만약 각 쓰레드의 행위가 서로에게 영향을 준다면 동기화가 필요하다
         */
        customerA.start();
        customerB.start();
    }

    private static void deposit(Bank bank, int amount){
        bank.deposit(amount);
        bank.depositWithoutSync(amount);
    }

    private static void withdraw(Bank bank, int amount){
        bank.withdraw(amount);
        bank.withdrawWithoutSync(amount);
    }

    private static synchronized void print(Bank bank){
        System.out.printf("ThreadName : %s, Bank moneyWithSync : %d\n", ThreadUtil.getName(), bank.getMoney());
        System.out.printf("ThreadName : %s, Bank moneyWithoutSync : %d\n", ThreadUtil.getName(), bank.getMoneyWithoutSync());
    }

    /**
     * ThreadName : customerA, Bank moneyWithSync : 3016400  // 하나의 쓰레드가 먼저 끝났을 때에는 money의 zeroSum이 보장되지 않지만
     * ThreadName : customerA, Bank moneyWithoutSync : -200
     * ThreadName : customerB, Bank moneyWithSync : 0        // 두 개의 쓰레드가 모두 끝났을 때에는 항상 money의 zeroSum이 보장된다
     * ThreadName : customerB, Bank moneyWithoutSync : -200
     */
}
```

## STEP 6 :: wait 그리고 notify

어떤 쓰레드가 synchronized 메소드를 실행할 때 다른 메서드가 그 메서드를 실행할 수 없다는 점은 매우 간단한 배타제어입니다.
보다 정교한 베타제어를 위해서는 아래와 같은 메커니즘이 필요합니다. 

1. 메서드를 호출할 수 있을 때까지 대기한다
2. 메서드의 호출이 끝날 때 대기하는 쓰레드에게 끝났음을 알린다

이와 같은 메커니즘은 자바의 Wait, Notify, NotifyAll 메서드를 사용합니다.  

`wait set` 이라는 개념을 정의해보겠습니다. wait set은 그 인스턴스의 wait 메서드를 실행한 후 동작을 정지하고 있는 쓰레드의 집합입니다.
lock이 인스턴스마다 존재하기 때문에 wait set도 인스턴스마다 존재합니다. 만약 static synchronized 블럭에 대해서는 클래스마다 wait set이 존재합니다.

### wait set에 들어가는 조건 :: wait 

wait set에 들어가는 조건은 딱 1가지입니다. wait을 호출했을 때입니다. obj.wait()의 호출에 대해서도 정해진 규칙이 있습니다. 

1. wait 메서드를 호출하기 위해서는 쓰레드가 lock을 가지고 있어야 합니다. 
2. 하지만 wait set에 들어갈 때에는 그 인스턴스의 락을 해제합니다.  

### wait set에서 나오는 조건 :: notify, notidfyAll, interrupt, timeout  
wait set에서 나오는 조건은 딱 4가지입니다. 

1. 다른 쓰레드에서 nofity 메소드를 호출한다
2. 다른 쓰레드에서 nofityAll 메소드를 호출한다
3. 다른 쓰레드에서 interrupt 메소드를 호출한다
4. wait 메서드가 timeout 된다



