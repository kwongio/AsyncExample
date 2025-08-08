package com.example.asyncexample.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "A");
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "B");

        // 모든 작업 완료 대기
        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2);
        all.join();

        // 결과값 각각 가져오기
        String result1 = f1.get();
        String result2 = f2.get();

        System.out.println("Result1 = " + result1);
        System.out.println("Result2 = " + result2);

        // 필요하면 둘을 합친 결과도 저장 가능
        String combined = result1 + result2;
        System.out.println("Combined = " + combined);
    }
}
