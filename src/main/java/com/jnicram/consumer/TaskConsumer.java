package com.jnicram.consumer;

import java.util.Queue;

import com.jnicram.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskConsumer implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(TaskConsumer.class);

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
                        LOG.error("Process is interrupted", e);
                    }
                }

                Task task = queue.poll();
                assert task != null;
                LOG.info(String.format("result of equation '%s' = %d", task.getEquation(), task.execute()));
                queue.notifyAll();
            }
        }
    }
}
