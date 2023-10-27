package org.spaghetti.chatbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.time.Instant;
import java.util.*;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TokenServiceTest {

    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;
    private TokenService tokenService;

    @BeforeEach
    void setUp(){
        jwtEncoder = mock(JwtEncoder.class);
        jwtDecoder = mock(JwtDecoder.class);
        tokenService = new TokenService(jwtEncoder,jwtDecoder);
    }
    @Test
    void generateJwt_ShouldReturnStringToken(){
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "subject");
        Jwt jwt = new Jwt("testTokenValue", Instant.MIN,Instant.MAX,headers,claims);
        when(jwtEncoder.encode(any())).thenReturn(jwt);
        Authentication authentication = mock(Authentication.class);
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        doReturn(authorities).when(authentication).getAuthorities();
        String jwtToken = tokenService.generateJwt(authentication);
        assertEquals(jwt.getTokenValue(),jwtToken);
    }
}
