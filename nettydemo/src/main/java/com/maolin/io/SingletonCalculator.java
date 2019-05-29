package com.maolin.io;

import java.util.concurrent.atomic.AtomicInteger;

public enum SingletonCalculator {
    /*
    instance of class
     */
    INSTANCE;

    private volatile AtomicInteger count = new AtomicInteger(0);

    public AtomicInteger getCount() {
        return count;
    }

    public void addCountOne() {
        count.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> SingletonCalculator.INSTANCE.addCountOne()).start();
        }

        Thread.sleep(500);

        System.out.println(SingletonCalculator.INSTANCE.getCount());
    }
}
