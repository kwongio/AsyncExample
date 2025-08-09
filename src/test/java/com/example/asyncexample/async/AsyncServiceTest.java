package com.example.asyncexample.async;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootTest
class AsyncServiceTest {

    @Autowired
    private AsyncService asyncService;

    @Test
    void 정상_비동기_동작_테스트() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = asyncService.asyncTest();
        String result = future.get(); // 결과 대기
        log.info("✅ 결과: {}", result);
    }

    @Test
    void 예외_발생_AsyncUncaughtExceptionHandler_테스트() throws InterruptedException {
        log.info("❌ AsyncUncaughtExceptionHandler 테스트 시작");
        asyncService.asyncThrowError();
    }

    @Test
    void CompletableFuture_예외_처리_테스트() {
        log.info("❌ CompletableFuture 예외 처리 테스트 시작");
        CompletableFuture<String> future = asyncService.asyncThrowErrorFuture();
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("future.get() 예외 발생", e);
        }
    }

    @Test
    void innerTest() {
        log.info("inner 테스트 시작 {}", Thread.currentThread().getName());
        asyncService.inner();
        log.info("inner 테스트 끝  {}", Thread.currentThread().getName());
    }
}
