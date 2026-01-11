package com.jwt_revision.JwtRevision.controller;

import com.jwt_revision.JwtRevision.dto.AuthResponse;
import com.jwt_revision.JwtRevision.dto.SignUpRequest;
import com.jwt_revision.JwtRevision.entity.RefreshToken;
import com.jwt_revision.JwtRevision.jwtFiles.JwtService;
import com.jwt_revision.JwtRevision.repository.RefreshTokenRepository;
import com.jwt_revision.JwtRevision.service.AuthService;
import com.jwt_revision.JwtRevision.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    @PostMapping("/signup-user")
    public AuthResponse signUpAsUser(@RequestBody SignUpRequest request){
        return authService.signUpAsUser(request);
    }

    @PostMapping("/signup-admin")
    public AuthResponse signUpAsAdmin(@RequestBody SignUpRequest request){
        return authService.signUpAsAdmin(request);
    }
    @PostMapping("/login")
    public AuthResponse login(@RequestBody SignUpRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        String accessToken = jwtService.generateToken(request.getUsername());
        String refreshToken = refreshTokenService.createRefreshToken(request.getUsername()).getToken();
        return new AuthResponse(accessToken,refreshToken);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody String refreshToken){
        RefreshToken token = refreshTokenService.validateRefreshToken(refreshToken);
        String newAccessToken = jwtService.generateToken(token.getUsername());

        return new AuthResponse(newAccessToken,refreshToken);
    }

    @PostMapping("/logout")
    public void logOut(Authentication authentication){
        refreshTokenRepository.deleteByUsername(authentication.getName());
    }

}
