package com.e8vu7t.datamanipulation.app.service;

import java.time.Instant;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.e8vu7t.datamanipulation.domain.auth.model.SigninUser;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final JwtEncoder encoder;
    private final UserDetailsManager users;
    private final PasswordEncoder passwordEncoder;

    public String issueToken(Authentication authentication){
        Instant now = Instant.now();
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
            // トークン発行者
            .issuer("self")
            // トークン発行日時
            .issuedAt(now)
            // トークンの有効期限を1時間とする
            .expiresAt(now.plusSeconds(36000L))
            .subject(authentication.getName())
            .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public void addUser(SigninUser SigninUser){
        UserDetails user = User.builder()
            .username(SigninUser.getUsername())
            .password(passwordEncoder.encode(SigninUser.getPassword()))
            .roles("USER")
            .build();
        users.createUser(user);
    }
}
