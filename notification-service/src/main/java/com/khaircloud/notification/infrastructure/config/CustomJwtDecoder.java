package com.khaircloud.notification.infrastructure.config;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var signJwt = SignedJWT.parse(token);

            return new Jwt(token,
                    signJwt.getJWTClaimsSet().getIssueTime().toInstant(),
                    signJwt.getJWTClaimsSet().getExpirationTime().toInstant(),
                    signJwt.getHeader().toJSONObject(),
                    signJwt.getJWTClaimsSet().getClaims());
        } catch (ParseException e) {
            throw new JwtException("Invalid token");
        }
    }
}
