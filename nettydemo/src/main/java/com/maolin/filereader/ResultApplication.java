package com.maolin.filereader;

import java.io.File;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ResultApplication {
    public static void main(String[] args) {
        File[] files = new File("src/main/resources").listFiles();
        LinkedBlockingQueue<QuotaRecord> queue = new LinkedBlockingQueue<>();
        CountDownLatch count = new CountDownLatch(files.length + 1);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        TreeMap<String, QuotaRecord> sortMap = new TreeMap<>();

        for(File file : files) {
            RecordReader reader = new RecordReader(queue, count, file);
            threadPool.execute(reader);
        }
        new Thread(new RecordSorter(queue, sortMap, count)).start();

        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threadPool.shutdown();

        System.out.println(sortMap);

    }

}
