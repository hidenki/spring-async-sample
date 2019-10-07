package com.example.asyncsample.controller;

import com.example.asyncsample.service.TaskServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.http.HttpStatus;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;


/**
 * クライアントからのリクエストを処理するコントローラクラス。
 */
@Slf4j
@RestController
public class TaskRestController {
    // 非同期処理実行クラス
    @Autowired
    private TaskServiceImpl taskService;

    // 非同期処理結果用の変数
    private DeferredResult<String> result;

    /**
     * 非同期処理実行メソッド
     * @return タスク実行結果
     */
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public DeferredResult<String> executeTask() {
        final long start = System.currentTimeMillis();

        UUID uuid = UUID.randomUUID();
        String strUuid = uuid.toString();

        // 結果(タイムアウトは8秒)
        result = new DeferredResult<>(8000L);
        taskService.executeSleep(result, start, strUuid);

        return result;
    }

    /**
     * タスク実行数オーバー時のエラーハンドル用メソッド
     * @return エラー時出力文字列
     */
    @ExceptionHandler(TaskRejectedException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String handleTaskRejectedException() {
        log.error("task - " + (taskService.taskNum + 1) + " : error.");
        return "too busy";
    }

    /**
     * タスク実行でのタイムアウト時のエラーハンドル用メソッド
     */
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public String handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e) {
        return "timeout";
    }
}
