package com.example.asyncsample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.asyncsample.service.AsyncService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FindNameContoller {

    private final AsyncService asyncService;

    FindNameContoller() {
        asyncService = new AsyncService();
    }


    @GetMapping("/users/")
    public List<String> findUsers() throws Exception {
        long start = System.currentTimeMillis();
        long heavyProcessTime = 10000L;
//        long lightProcessTime = 1000L;

        UUID uuid = UUID.randomUUID();
        String strUuid = uuid.toString();

        log.warn("request started");
        CompletableFuture<String> heavyProcess = asyncService.findName(strUuid, heavyProcessTime);
//        CompletableFuture<String> lightProcess = asyncService.findName("light", lightProcessTime);

        // heavyProcessが終わったら実行される処理
        heavyProcess.thenAcceptAsync(heavyProcessResult -> {
            log.warn("finished name=" + heavyProcessResult + ", sleep = " + heavyProcessTime);
        });

        // lightProcessが終わったら実行される処理
//        lightProcess.thenAcceptAsync(lightProcessResult -> {
//            log.warn("finished name=" + lightProcessResult + ", sleep = " + lightProcessTime);
//        });

        // 指定した処理が終わったらこれ以降の処理が実行される
//        CompletableFuture.allOf(heavyProcess, lightProcess).join();
        CompletableFuture.allOf(heavyProcess).join();

        // 返却値の作成
        List<String> names = new ArrayList<>();
        names.add(heavyProcess.get());
//        names.add(lightProcess.get());

        Thread.sleep(10L);

        long end = System.currentTimeMillis();
        // 処理全体の時間を出力
        log.warn("request finished. time: " + ((end - start))  + "ms");

        return names;
    }

}