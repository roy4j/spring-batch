package com.tcs.spring.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmployeeSkipListener<T> {

    @OnSkipInRead
    public void onSkipInRead(Throwable throwable) {
        log.info("OnSkipInRead");
    }

    @OnSkipInProcess
    public void onSkipInProcess(T item, Throwable t) {
        log.info("OnSkipInProcess");
    }

    @OnSkipInWrite
    public void onSkipInWrite(T item, Throwable t) {
        log.info("OnSkipInWrite");
    }
}
