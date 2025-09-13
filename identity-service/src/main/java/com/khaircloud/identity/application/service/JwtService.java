package com.khaircloud.identity.application.service;

import com.khaircloud.identity.application.dto.request.ClaimPayload;
import com.khaircloud.identity.common.exception.UnauthorizeException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.signerKey}")
    protected String singerKey;

    @Value("${jwt.valid-duration}")
    protected long validDuration;

    @Value("${jwt.refreshable-duration}")
    protected long refreshDuration;

    public String generateAccessToken(ClaimPayload claims) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimSet = new JWTClaimsSet.Builder()
                .subject(claims.getUserId())
                .issuer("khaiir-cloud.com")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(new Date(
                        Instant.now().plusSeconds(validDuration).toEpochMilli()
                ))
                .claim("email", claims.getEmail())
                .claim("scope", claims.getRole())
                .claim("plan", claims.getUserPlan())
                .build();

        JWSObject jwsObject = new JWSObject(header, new Payload(claimSet.toJSONObject()));

        try {
            jwsObject.sign(new MACSigner(singerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    public String generateRefreshToken() {
        return Base64.getEncoder().
                encodeToString((System.currentTimeMillis()+UUID.randomUUID()
                        .toString().substring(0, 8)).getBytes());
    }

    public SignedJWT verify(String accessToken) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(accessToken);
        MACVerifier verifier = new MACVerifier(singerKey.getBytes());

        boolean isVerified = signedJWT.verify(verifier);
        if(!isVerified && signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date())) {
            throw new UnauthorizeException("");
        }

        return signedJWT;
    }
}
