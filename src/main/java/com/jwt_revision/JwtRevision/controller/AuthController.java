package com.jwt_revision.JwtRevision.controller;

import com.jwt_revision.JwtRevision.dto.AuthResponse;
import com.jwt_revision.JwtRevision.dto.RefreshTokenDTO;
import com.jwt_revision.JwtRevision.dto.SignUpRequest;
import com.jwt_revision.JwtRevision.entity.RefreshToken;
import com.jwt_revision.JwtRevision.jwtFiles.JwtService;
import com.jwt_revision.JwtRevision.repository.RefreshTokenRepository;
import com.jwt_revision.JwtRevision.securityUtil.CookieUtil;
import com.jwt_revision.JwtRevision.service.AuthService;
import com.jwt_revision.JwtRevision.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
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
    public AuthResponse signUpAsUser(@RequestBody SignUpRequest request, HttpServletResponse response){
        AuthResponse authResponse =  authService.signUpAsUser(request);
        CookieUtil.addRefresTokenCookie(response,authResponse.getRefreshToken());
        return new AuthResponse(authResponse.getAccessToken(),null);
    }

    @PostMapping("/signup-admin")
    public AuthResponse signUpAsAdmin(@RequestBody SignUpRequest request,HttpServletResponse response){
        AuthResponse authResponse =  authService.signUpAsAdmin(request);
        CookieUtil.addRefresTokenCookie(response,authResponse.getRefreshToken());
        return new AuthResponse(authResponse.getAccessToken(),null);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody SignUpRequest request,HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        String accessToken = jwtService.generateToken(request.getUsername());
        String refreshToken =
                refreshTokenService.createRefreshToken(request.getUsername()).getToken();

        CookieUtil.addRefresTokenCookie(response, refreshToken);

        return new AuthResponse(accessToken, null);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@CookieValue("refreshToken")String refreshToken,HttpServletResponse response){
        RefreshToken newRefreshToken =
                refreshTokenService.rotateRefreshToken(refreshToken);

        String newAccessToken =
                jwtService.generateToken(newRefreshToken.getUsername());

        CookieUtil.addRefresTokenCookie(response, newRefreshToken.getToken());

        return new AuthResponse(newAccessToken, null);
    }

    @PostMapping("/logout")
    public void logOut(Authentication authentication, HttpServletResponse response){
        refreshTokenRepository.deleteByUsername(authentication.getName());
        CookieUtil.deleteRefreshTokenCookie(response);
    }

}
