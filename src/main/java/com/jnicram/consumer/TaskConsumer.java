package com.jnicram.consumer;

import java.util.Queue;

import com.jnicram.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskConsumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskConsumer.class);

    private final Queue<Task> queue;

    public TaskConsumer(Queue<Task> queue) {
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
                        LOGGER.error("Process is interrupted", e);
                    }
                }

                Task task = queue.poll();
                assert task != null;
                LOGGER.info(String.format("result of equation '%s' = %f", task.getEquation(), task.execute()));
                queue.notifyAll();
            }
        }
    }
}
