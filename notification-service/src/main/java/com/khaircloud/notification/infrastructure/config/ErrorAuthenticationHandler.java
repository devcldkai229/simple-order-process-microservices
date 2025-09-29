package com.khaircloud.notification.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaircloud.notification.common.exception.UnauthorizeException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class ErrorAuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(UnauthorizeException.code);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        var body = com.khaircloud.notification.application.dto.ApiResponse.builder()
                .code(UnauthorizeException.code)
                .message("Unauthorized to access this functions!")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(body));
        response.flushBuffer();
    }
}
