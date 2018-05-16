package com.almundo.backendtest.service;

import com.almundo.backendtest.domain.Call;
import com.almundo.backendtest.factory.EmployeeFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * @author hollman_castro
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Dispatcher.class, EmployeeFactory.class,
        ThreadPoolTaskScheduler.class, ThreadPoolTaskExecutor.class})
@ActiveProfiles("test")
public class DispatcherTest {

    @Autowired
    private Dispatcher dispatcher;

    @Test
    public void processCall() throws Throwable {
        Call call = new Call("+57123456789", 5);
        dispatcher.processCall(call);
        await().atMost(call.getDuration() * 2, SECONDS).
                untilAsserted(() -> assertThat(call.isProcessed()).isEqualTo(true));
        assertTrue(call.isProcessed());
    }

    @Test
    public void testMultipleCalls() {
        int testSize = 10;
        List<Call> calls = new ArrayList<>();
        for (int x = 0; x < testSize; x++) {
            Call call = new Call("+5713456789");
            calls.add(call);
            dispatcher.processCall(call);
        }

        for (Call call : calls) {
            await().atMost(call.getDuration() * 2, SECONDS).
                    untilAsserted(() -> assertThat(call.isProcessed()).isEqualTo(true));
            assertTrue(call.isProcessed());
        }
    }
}