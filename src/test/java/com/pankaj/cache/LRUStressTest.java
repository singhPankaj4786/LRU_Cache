package com.pankaj.cache;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LRUStressTest {

    @Test
    public void testMultiThreadedLoad() throws InterruptedException {

        final ThreadSafeLRUCache<Integer, Integer> cache = new ThreadSafeLRUCache<>(50);
        final int THREADS = 100;
        final int OPERATIONS = 2000;

        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        CountDownLatch latch = new CountDownLatch(THREADS);

        Random random = new Random();

        for (int t = 0; t < THREADS; t++) {
            executor.submit(() -> {
                for (int i = 0; i < OPERATIONS; i++) {
                    int key = random.nextInt(200);

                    if (random.nextBoolean()) {
                        cache.put(key, key * 10);
                    } else {
                        cache.get(key);
                    }
                }
                latch.countDown();
            });
        }

        latch.await();
        executor.shutdown();

        // Ensures DLL and HashMap are not mismatched (basic integrity check)
//        assertTrue(cache.size() <= 50,
//                () -> "Cache size exceeded capacity after concurrency test");
        assertTrue(cache.size() <= 50);



    }
}
