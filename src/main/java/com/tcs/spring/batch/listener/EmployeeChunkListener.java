package com.tcs.spring.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmployeeChunkListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext chunkContext) {
        log.info("New Chunk Started");
    }

    @Override
    public void afterChunk(ChunkContext chunkContext) {
        log.info("Chunk Completed");
    }

    @Override
    public void afterChunkError(ChunkContext chunkContext) {
        log.info("Error in Chunk");
    }
}
