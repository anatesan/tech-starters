package com.str.iq_concurrency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@SpringBootTest
@RunWith(SpringRunner.class)

public class RunnableTest {

    private static ExecutorService executorService;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Before
    public void startUpExecutorService() {

        executorService = Executors.newFixedThreadPool(10);
    }

    @After
    public void shutDownExecutorService() {
        ConcurrencyTestUtil.shutDownExecutorSerice(executorService);
    }

    @Test
    public void pooledThreadTest() {
        for (int i = 0; i < 20; i++) {
            executorService.submit(() -> {
                logger.info(String.format("Running thread: %s", Thread.currentThread().getName()));
            });
        }
    }

}
