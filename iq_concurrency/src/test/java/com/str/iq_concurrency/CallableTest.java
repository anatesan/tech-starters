package com.str.iq_concurrency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CallableTest {

    private static ExecutorService executorService;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private final int threadPoolSize = 10;
    private final int numIteration = 30;
    private final int randomNumberLimit = 10;

    Callable<Integer> randomIntTask(int limit) {
        return () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return ThreadLocalRandom.current().nextInt(0, limit + 1);

            }
            catch(InterruptedException e) {
                logger.info(String.format("Thread: %s got interrupted", Thread.currentThread().getName()));
                return -1;
            }
        };
    }

    @Before
    public void startUpExecutors() {
        executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    @After
    public void shutDownExecutors() {
        ConcurrencyTestUtil.shutDownExecutorSerice(executorService);
    }

    @Test
    public void addRandomNumbers() {
        Integer max = 0;
        Integer sum = 0;


        List<Callable<Integer>> randomIntTasks = new ArrayList<>();
        for (int i = 0; i < numIteration; i++) {
            randomIntTasks.add(randomIntTask(randomNumberLimit));
        }

        try {
            Integer[] resultsArray = executorService.invokeAll(randomIntTasks)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        }
                        catch(Exception e) {
                            logger.info(String.format("Exception type: %s in thread: %s",
                                    e.getClass().getName(),
                                    Thread.currentThread().getName()));
                            return -1;
                        }
                    })
                    .toArray(Integer[]::new);

            max = Arrays.stream(resultsArray).reduce(-1, (m, val) -> val>m? val: m);
            sum = Arrays.stream(resultsArray).reduce(0, (s, val) -> s+val);


            for (Integer result: resultsArray
                 ) {
                logger.info(String.format("%s ", result.toString()));

            }
            logger.info(String.format("Count: %s", resultsArray.length));
            logger.info(String.format("Sum: %s", sum));
            logger.info(String.format("Max: %s", max));

        }
        catch (InterruptedException e) {

        }


    }
}
