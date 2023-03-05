package com.steelrain.springboot.lilac.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 주기적으로 Apache Http client 커넥션풀에서 idle되거나 만료된 커넥션들을 회수한다
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApacheHttpClientConnectionPoolEvictor {

    private static final int IDLE_TIMEOUT = 30000;

    private final PoolingHttpClientConnectionManager m_connectionManager;

    //@Async
    @Scheduled(fixedDelay = 30000)
    public void evictIdleHttpConnection(){
        if(m_connectionManager == null){
            return;
        }
        try{
            synchronized (this){
                log.debug("http connection 회수 실행");
                m_connectionManager.closeExpired();
                m_connectionManager.closeIdle(TimeValue.ofMicroseconds(IDLE_TIMEOUT));
            }
        }catch(Exception ex){
            log.error("만료 되거나 idle 상태의 http connection 종료 중 예외 발생 : {}", ex);
        }
    }
}
