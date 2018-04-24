package com.jnicram.consumer;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskConsumer implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(TaskConsumer.class);

    private final Queue queue;

    public TaskConsumer(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        LOG.error("Process is interrupted", e);
                    }
                }

                int number = (int) queue.poll();
                LOG.info("removing Element " + number);
                queue.notifyAll();
            }
        }
    }
}
