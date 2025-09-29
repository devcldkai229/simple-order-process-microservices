package com.khaircloud.identity.interfaces.rest;

import com.khaircloud.identity.application.dto.ApiResponse;
import com.khaircloud.identity.application.dto.AuthResponse;
import com.khaircloud.identity.application.dto.request.IntrospectRequest;
import com.khaircloud.identity.application.dto.request.LoginRequest;
import com.khaircloud.identity.application.dto.request.RegisterRequest;
import com.khaircloud.identity.application.dto.response.Authentication;
import com.khaircloud.identity.application.dto.response.IntrospectResponse;
import com.khaircloud.identity.application.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
public class AuthController {

    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse<Authentication>> login(@RequestBody LoginRequest request) {
        AuthResponse<Authentication> res = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request) {
        var res = authService.introspect(request);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
