package com.khaircloud.identity.application.service;

import com.khaircloud.identity.application.dto.ApiResponse;
import com.khaircloud.identity.application.dto.AuthResponse;
import com.khaircloud.identity.application.dto.request.ClaimPayload;
import com.khaircloud.identity.application.dto.request.IntrospectRequest;
import com.khaircloud.identity.application.dto.request.LoginRequest;
import com.khaircloud.identity.application.dto.request.RegisterRequest;
import com.khaircloud.identity.application.dto.response.Authentication;
import com.khaircloud.identity.application.dto.response.IntrospectResponse;
import com.khaircloud.identity.common.exception.EmailAlreadyExistException;
import com.khaircloud.identity.common.exception.ResourceNotExistException;
import com.khaircloud.identity.domain.event.NotificationEvent;
import com.khaircloud.identity.domain.model.User;
import com.khaircloud.identity.infrastructure.aws.sns.VerifyEmailPublisher;
import com.khaircloud.identity.infrastructure.repository.RoleRepository;
import com.khaircloud.identity.infrastructure.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthService {

    PasswordEncoder passwordEncoder;

    UserRepository userRepository;

    RoleRepository roleRepository;

    JwtService jwtService;

    KafkaTemplate<String, Object> kafkaTemplate;

    public void register(RegisterRequest request) {
        var existingUser = userRepository.findByEmail(request.getEmail());

        if(existingUser.isPresent()) {
            throw new EmailAlreadyExistException("Email already exist!");
        }

        var userRole = roleRepository.findById("USER").orElseThrow(
            () -> new RuntimeException("ROLE NOT ACCEPT!")
        );

        var user = userRepository.save(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .verifyToken(Base64.getEncoder()
                        .encodeToString((System.currentTimeMillis()
                                +UUID.randomUUID().toString()).substring(0,8).getBytes()))
                        .roles(new HashSet<>(List.of(userRole)))
                        .isActive(true)
                        .plan("FREE")
                .build());

        kafkaTemplate.send("verify-account-register-notification", NotificationEvent.<String>builder()
                .sender("")
                .receiver(user.getEmail())
                .body("")
                .subject("Verify email account").build());
    }

    public AuthResponse<Authentication> login(LoginRequest request) {
        var loadedUser = userRepository.findByEmail(request.getEmail());
        if(!loadedUser.isPresent()) throw new ResourceNotExistException("User not exist");

        var user = loadedUser.get();
        boolean isMatched = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!isMatched) throw new BadCredentialsException("Password incorrect");

        var accessToken = jwtService.generateAccessToken(ClaimPayload.builder().email(user.getEmail())
                .userId(user.getId())
                .authorities(buildAuthorities(user))
                .userPlan(user.getPlan())
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

    private List<String> buildAuthorities(User user) {
        List<String> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add("ROLE_" + role.getName());
            role.getPermissions().forEach(permission ->
                    authorities.add(permission.getName())
            );
        });
        return authorities;
    }

    public ApiResponse<IntrospectResponse> introspect(IntrospectRequest request) {
        boolean isValid = true;
        String userPlan = "";
        try {
            var jwt = jwtService.verify(request.getToken());
            userPlan = jwt.getJWTClaimsSet().getClaim("plan").toString();
        } catch (ParseException | JOSEException e) {
            isValid = false;
        }

        return ApiResponse.<IntrospectResponse>builder().data(
                IntrospectResponse.builder().isValid(isValid)
                        .userPlan(userPlan)
                        .build()).build();
    }
}
