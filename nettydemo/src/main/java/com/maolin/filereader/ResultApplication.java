package com.maolin.filereader;

import javax.xml.transform.Result;
import java.io.File;
import java.net.URL;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ResultApplication {
    public static void main(String[] args) {
        URL url = ResultApplication.class.getClassLoader().getResource("text/");
        assert url != null;
        File[] files = new File(url.getFile()).listFiles();
        assert files != null;
        CountDownLatch count = new CountDownLatch(files.length + 1);
        LinkedBlockingQueue<QuotaRecord> queue = new LinkedBlockingQueue<>();
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

        for(String key : sortMap.keySet()) {
            System.out.println(sortMap.get(key));
        }

    }

}
