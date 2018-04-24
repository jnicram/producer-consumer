# producer-consumer
Build an application that will consist of two components:

TaskProducer
TaskConsumer
Both start run on application startup and share the global queue (based on the list, queue, the exact structure is your decision). TaskProducer generates random tasks and add them to this queue (Task object, which is the task explained below) until the queue is full (ie, it reaches a configurable maximum limit defined by a constant, a parameter configurable in the application, etc.). Once the queue is full TaskProducer stops producing tasks and waits (periodically polling the queue) until its size drop to the half of the maximum size and starts producing tasks again.

During this time, TaskConsumer retrieves a task from the job queue (if exists) and executes it.


What is the task? The task is a randomly generated character string in the range of `0-9 + / * -` (eg. `5 + 4`, `323/65323 * 24323/2343 + 2234-2233`), which analyzes this string, calculates its value according to the formula and writes the result to the console.


Please create at least 2 TaskProducers working in separate threads and min. 4 TaskConsumers, also working in separate threads.
