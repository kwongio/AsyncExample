package com.example.asyncexample.completable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

public class CompletableFutureRuntimeException2Example {
    public static void main(String[] args) {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "A");

        CompletableFuture<Object> f2 = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("f2 실패!");
        }).exceptionally(ex -> {
            System.err.println("f2에서 예외 발생: " + ex.getMessage());
            return "기본값 B";  // 예외 시 대체값 반환
        });

        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2);

        try {
            all.join();

            String result1 = f1.get();
            String result2 = (String)f2.get();

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

