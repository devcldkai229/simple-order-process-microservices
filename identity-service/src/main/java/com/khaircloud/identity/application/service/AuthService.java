package com.khaircloud.identity.application.service;

import com.khaircloud.identity.application.dto.AuthResponse;
import com.khaircloud.identity.application.dto.request.ClaimPayload;
import com.khaircloud.identity.application.dto.request.LoginRequest;
import com.khaircloud.identity.application.dto.request.RegisterRequest;
import com.khaircloud.identity.application.dto.response.Authentication;
import com.khaircloud.identity.common.exception.EmailAlreadyExistException;
import com.khaircloud.identity.common.exception.ResourceNotExistException;
import com.khaircloud.identity.domain.event.NotificationEvent;
import com.khaircloud.identity.domain.model.User;
import com.khaircloud.identity.infrastructure.aws.sns.VerifyEmailPublisher;
import com.khaircloud.identity.infrastructure.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthService {

    PasswordEncoder passwordEncoder;

    UserRepository userRepository;

    JwtService jwtService;

    VerifyEmailPublisher verifyEmailPublisher;

    public void register(RegisterRequest request) {
        var existingUser = userRepository.findByEmail(request.getEmail());

        if(existingUser.isPresent()) {
            throw new EmailAlreadyExistException("Email already exist!");
        }

        var user = userRepository.save(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .verifyToken(Base64.getEncoder()
                        .encodeToString((System.currentTimeMillis()
                                +UUID.randomUUID().toString()).substring(0,8).getBytes()))
                .build());

        verifyEmailPublisher.sendVerificationEmail(NotificationEvent.builder().receiver(user.getEmail()).build(),
                user.getVerifyToken());
    }

    public AuthResponse<Authentication> login(LoginRequest request) {
        var loadedUser = userRepository.findByEmail(request.getEmail());
        if(!loadedUser.isPresent()) throw new ResourceNotExistException("User not exist");

        var user = loadedUser.get();
        boolean isMatched = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!isMatched) throw new BadCredentialsException("Password incorrect");

        var accessToken = jwtService.generateAccessToken(ClaimPayload.builder().email(user.getEmail())
                .userId(user.getId())
                .role(user.getRoles())
                .build());
        var refreshToken = jwtService.generateRefreshToken();
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return AuthResponse.<Authentication>builder()
                .isAuthenticated(true)
                .data(Authentication.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build())
                .build();
    }
}
