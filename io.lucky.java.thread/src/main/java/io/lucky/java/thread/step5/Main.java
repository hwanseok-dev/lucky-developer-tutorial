package io.lucky.java.thread.step5;

import io.lucky.java.thread.util.ThreadUtil;

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
