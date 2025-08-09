package com.example.asyncexample.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AsyncService {

    @Async
    public CompletableFuture<String> asyncTest() {
        log.info("Async method started. Thread: {}", Thread.currentThread().getName());

        try {
            Thread.sleep(1000); // 비동기 작업 시뮬레이션
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Async method finished. Thread: {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture("Async result");
    }

    // ❌ CompletableFuture 사용 안 함 → AsyncUncaughtExceptionHandler가 처리
    @Async
    public void asyncThrowError() {
        log.info("Error test started. Thread: {}", Thread.currentThread().getName());
        throw new RuntimeException("🔥 강제로 발생시킨 예외");
    }

    // ✅ CompletableFuture 사용 → future.get()에서 예외 확인 가능
    @Async
    public CompletableFuture<String> asyncThrowErrorFuture() {
        log.info("Error future test started. Thread: {}", Thread.currentThread().getName());
        throw new RuntimeException("🔥 CompletableFuture 예외 발생");
    }

    public void inner() {
        asyncTest();
    }
}
