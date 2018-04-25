package com.jnicram.producer;

import com.jnicram.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

public class TaskProducer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskProducer.class);

    private final Queue<Task> queue;
    private final int maxSize;

    public TaskProducer(Queue<Task> queue, int maxSize) {
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
                        LOGGER.error("Process is interrupted", e);
                    }
                }

                Task task = new Task();
                task.generateRandomEquation();
                LOGGER.info("new equation was created: " + task.getEquation());

                queue.add(task);
                queue.notifyAll();
            }
        }
    }
}
