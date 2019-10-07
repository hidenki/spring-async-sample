package com.example.asyncsample.service;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 非同期処理実行用サービスクラス
 */
@Slf4j
@Service
public class TaskServiceImpl {
    public static int taskNum = 0;

    /**
     * 5秒スリープする非同期処理実行メソッド
     * @param deferredResult    非同期処理結果の格納用変数
     * @param start             実行時間計測開始時間
     */
    @Async("sleepTask")
    @Transactional
    public void executeSleep(final DeferredResult<String> deferredResult, final long start, final String name) {
        try {
            taskNum++;
            final int taskNumber = taskNum;

            log.info("start task - " + taskNumber);

            TimeUnit.SECONDS.sleep(5);
            deferredResult.setResult("task - " + taskNumber + " finished. ");

            log.info("end task - " + taskNumber);
            log.info("task - " + taskNumber + " : " + (System.currentTimeMillis() - start - 5000L) + "ms");
        } catch (Exception e) {
            deferredResult.setErrorResult(e);
        }
    }

}