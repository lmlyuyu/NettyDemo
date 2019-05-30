package com.maolin.filereader;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class RecordReader implements Runnable {
    private LinkedBlockingQueue<QuotaRecord> quotaDataQueue;
    private CountDownLatch count;
    private File file;

    RecordReader(LinkedBlockingQueue<QuotaRecord> queue, CountDownLatch count, File file) {
        this.quotaDataQueue = queue;
        this.count = count;
        this.file = file;
    }

    @Override
    public void run() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
            String line;
            String[] recordDataArr;
            while((line = reader.readLine()) != null) {
                recordDataArr = line.split(",");
                quotaDataQueue.put(new QuotaRecord(recordDataArr[0], recordDataArr[1], Double.valueOf(recordDataArr[2])));
            }

            count.countDown();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
