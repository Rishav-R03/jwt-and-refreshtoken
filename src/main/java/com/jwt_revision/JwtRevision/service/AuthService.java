package com.jwt_revision.JwtRevision.service;

import com.jwt_revision.JwtRevision.dto.AuthResponse;
import com.jwt_revision.JwtRevision.dto.SignUpRequest;
import com.jwt_revision.JwtRevision.entity.UserEntity;
import com.jwt_revision.JwtRevision.exception.UserAlreadyExistsException;
import com.jwt_revision.JwtRevision.jwtFiles.JwtService;
import com.jwt_revision.JwtRevision.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthResponse signUpAsUser(SignUpRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
        // temp
        String accessToken = jwtService.generateToken(user.getUsername());
        String refreshToken = refreshTokenService.createRefreshToken(user.getUsername()).getToken();
        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse signUpAsAdmin(SignUpRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_ADMIN")
                .build();

        userRepository.save(user);
        // temp
        String accessToken = jwtService.generateToken(user.getUsername());
        String refreshToken = refreshTokenService.createRefreshToken(user.getUsername()).getToken();
        return new AuthResponse(accessToken, refreshToken);
    }
}
