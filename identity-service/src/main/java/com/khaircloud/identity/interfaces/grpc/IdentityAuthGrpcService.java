package com.khaircloud.identity.interfaces.grpc;

import com.khaircloud.identity.IdentityAuthServiceGrpc;
import com.khaircloud.identity.IntrospectRequest;
import com.khaircloud.identity.IntrospectResponse;
import com.khaircloud.identity.application.service.JwtService;
import com.khaircloud.identity.common.exception.UnauthorizeException;
import com.khaircloud.identity.infrastructure.repository.UserRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityAuthGrpcService extends IdentityAuthServiceGrpc.IdentityAuthServiceImplBase {

    JwtService jwtService;

    UserRepository userRepository;

    @Override
    public void introspect(IntrospectRequest request, StreamObserver<IntrospectResponse> responseObserver) {
        try {
            var signedJWT = jwtService.verify(request.getToken());
            var loadedUser = userRepository.findById(signedJWT.getJWTClaimsSet().getSubject()).orElseThrow(
                    () -> new UnauthorizeException("User not exist!")
            );

            responseObserver.onNext(IntrospectResponse.newBuilder()
                    .setIsValid(true)
                    .setUserPlan(loadedUser.getPlan())
                    .build());
            responseObserver.onCompleted();
        } catch (UnauthorizeException e) {
            responseObserver.onError(Status.PERMISSION_DENIED
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Error introspecting token: {}", request.getToken(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error while introspecting token")
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}
