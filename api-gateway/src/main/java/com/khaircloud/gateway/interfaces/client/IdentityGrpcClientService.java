package com.khaircloud.gateway.interfaces.client;

import com.khaircloud.gateway.dto.IntrospectResponse;
import com.khaircloud.identity.IdentityAuthServiceGrpc;
import com.khaircloud.identity.IntrospectRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class IdentityGrpcClientService {

    @GrpcClient("identity-service")
    IdentityAuthServiceGrpc.IdentityAuthServiceBlockingStub syncClient;

    public IntrospectResponse introspect(String token) {
        var response = syncClient.introspect(IntrospectRequest.newBuilder().setToken(token).build());
        return IntrospectResponse.builder()
                .isValid(response.getIsValid())
                .userPlan(response.getUserPlan())
                .build();
    }

}
