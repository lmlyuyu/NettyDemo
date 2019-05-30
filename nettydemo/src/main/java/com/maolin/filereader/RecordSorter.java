package com.maolin.filereader;

import sun.awt.image.ImageWatched;

import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class RecordSorter implements Runnable {
    private LinkedBlockingQueue<QuotaRecord> quotaDataQueue;
    private TreeMap<String, QuotaRecord> sortMap;
    private CountDownLatch count;

    public RecordSorter(LinkedBlockingQueue<QuotaRecord> queue, TreeMap<String, QuotaRecord> sortMap, CountDownLatch count) {
        this.quotaDataQueue = queue;
        this.sortMap = sortMap;
        this.count = count;
    }

    @Override
    public void run() {
        QuotaRecord record;
        QuotaRecord treeData;
        while (!quotaDataQueue.isEmpty() || count.getCount() > 1) {
            try {
                record = quotaDataQueue.take();
                if((treeData = sortMap.get(record.getGroupId())) != null && treeData.getQuota() < record.getQuota()) {
                    continue;
                } else {
                    sortMap.put(record.getGroupId(), record);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count.countDown();

    }
}
