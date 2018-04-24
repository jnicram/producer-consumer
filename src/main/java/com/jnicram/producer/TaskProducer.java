package com.jnicram.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.Random;

public class TaskProducer implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(TaskProducer.class);

    private final Queue queue;
    private final int maxSize;

    public TaskProducer(Queue queue, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                boolean shouldWait = queue.size() == maxSize;
                while (shouldWait) {
                    try {
                        queue.wait();
                        shouldWait = queue.size() > maxSize / 2;
                    } catch (InterruptedException e) {
                        LOG.error("Process is interrupted", e);
                    }
                }

                Random random = new Random();
                int number = random.nextInt(100);
                LOG.info("Producing value " + number);
                queue.add(number);
                queue.notifyAll();
            }

        }
    }


}
