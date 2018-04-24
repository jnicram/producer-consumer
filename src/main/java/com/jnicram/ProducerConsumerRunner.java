package com.jnicram;

import com.jnicram.consumer.TaskConsumer;
import com.jnicram.producer.TaskProducer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerRunner {

    private static final int MAX_QUEUE_SIZE = 20;

    public static void main(String[] args) {
        final Queue<Task> sharedQueue = new LinkedList<>();

        ExecutorService producerExecutor = Executors.newFixedThreadPool(2);
        producerExecutor.execute(new TaskProducer(sharedQueue, MAX_QUEUE_SIZE));
        producerExecutor.execute(new TaskProducer(sharedQueue, MAX_QUEUE_SIZE));

        ExecutorService consumerExecutor = Executors.newFixedThreadPool(4);
        consumerExecutor.execute(new TaskConsumer(sharedQueue));
        consumerExecutor.execute(new TaskConsumer(sharedQueue));
        consumerExecutor.execute(new TaskConsumer(sharedQueue));
        consumerExecutor.execute(new TaskConsumer(sharedQueue));

        producerExecutor.shutdown();
        consumerExecutor.shutdown();
    }
}
