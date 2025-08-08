package com.example.asyncexample.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

public class CompletableFutureRuntimeExceptionExample {
    public static void main(String[] args) {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "A");
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("f2 실패!");
        });

        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2);

        try {
            // all.join() 호출 시 f2에서 예외가 발생하면 CompletionException 발생
            all.join();

            // 모든 작업이 정상 완료된 경우에만 get() 호출 (예외 시 여기에 도달하지 않음)
            String result1 = f1.get();
            String result2 = f2.get();

            System.out.println("Result1 = " + result1);
            System.out.println("Result2 = " + result2);

            String combined = result1 + result2;
            System.out.println("Combined = " + combined);

        } catch (CompletionException e) {
            System.err.println("CompletionException 발생: " + e.getCause());
        } catch (ExecutionException e) {
            System.err.println("ExecutionException 발생: " + e.getCause());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("작업이 중단되었습니다.");
        }
    }
}
