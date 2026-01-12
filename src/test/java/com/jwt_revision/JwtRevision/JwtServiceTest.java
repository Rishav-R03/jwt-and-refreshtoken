package com.jwt_revision.JwtRevision;

import com.jwt_revision.JwtRevision.jwtFiles.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
    private JwtService jwtService;
    @BeforeEach
    void setUp(){
        jwtService = new JwtService();
    }
    @Test
    void shouldGenerateAndValidateToken(){
        String token = jwtService.generateToken("rishav");
        assertNotNull(token);
        assertEquals("rishav",jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token,new User("rishav","", List.of())));
    }
}
