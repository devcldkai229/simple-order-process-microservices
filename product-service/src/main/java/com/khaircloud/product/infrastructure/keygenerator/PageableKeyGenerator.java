package com.khaircloud.product.infrastructure.keygenerator;

import com.khaircloud.product.application.dto.request.PageableRequest;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("pageableKeyGenerator")
public class PageableKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        PageableRequest req = (PageableRequest) params[0];
        return String.format("page=%d:size=%d:sortBy=%s:desc=%b",
                req.getPage(), req.getSize(), req.getSortBy(), req.isDesc());
    }
}
