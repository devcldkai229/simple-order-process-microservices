package com.khaircloud.product.infrastructure.config;

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
            var signJWT = SignedJWT.parse(token);

            return new Jwt(token,
                    signJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                    signJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
                    signJWT.getHeader().toJSONObject(),
                    signJWT.getJWTClaimsSet().getClaims());
        } catch (ParseException e) {
            throw new JwtException("Invalid token");
        }
    }
}
