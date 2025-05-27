package com.getjavajob.securityservice.web;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class FeignSessionInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        try {
            String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
            template.header("X-Auth-Token", sessionId);
        } catch (IllegalStateException e) {
            // Игнорируем, если нет активной сессии (например, фоновые задачи)
        }
    }
}
