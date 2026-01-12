package com.jwt_revision.JwtRevision;

import com.jwt_revision.JwtRevision.entity.RefreshToken;
import com.jwt_revision.JwtRevision.repository.RefreshTokenRepository;
import com.jwt_revision.JwtRevision.service.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Test
    void shouldRotateRefreshToken() {

        RefreshToken oldToken = RefreshToken.builder()
                .token("old-token")
                .username("rishav")
                .expiryDate(Instant.now().plusSeconds(600))
                .build();

        when(refreshTokenRepository.findByToken("old-token"))
                .thenReturn(Optional.of(oldToken));

        // 👇 IMPORTANT PART
        when(refreshTokenRepository.save(any(RefreshToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RefreshToken newToken =
                refreshTokenService.rotateRefreshToken("old-token");

        assertNotNull(newToken);
        assertNotEquals("old-token", newToken.getToken());
        assertEquals("rishav", newToken.getUsername());

        verify(refreshTokenRepository).delete(oldToken);
    }
}

