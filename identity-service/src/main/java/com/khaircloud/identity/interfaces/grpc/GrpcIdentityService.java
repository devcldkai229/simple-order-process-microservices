package com.khaircloud.identity.interfaces.grpc;

import com.khaircloud.identity.application.service.AuthService;
import io.grpc.stub.StreamObserver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GrpcIdentityService extends IdentityServiceGrpc.IdentityServiceImplBase {

    AuthService authService;

    @Override
    public void introspect(IntrospectRequest request,
                           StreamObserver<IntrospectResponse> responseObserver) {
        try {
            var dtoRequest = com.khaircloud.identity.application.dto.request.IntrospectRequest
                    .builder()
                    .token(request.getToken())
                    .build();

            var dtoResponse = authService.introspect(dtoRequest);

            IntrospectResponse grpcResponse = IntrospectResponse.newBuilder()
                    .setIsValid(dtoResponse.isValid())
                    .build();

            responseObserver.onNext(grpcResponse);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
