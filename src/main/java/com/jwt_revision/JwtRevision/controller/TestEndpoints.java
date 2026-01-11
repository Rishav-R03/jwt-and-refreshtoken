package com.jwt_revision.JwtRevision.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin("*")
public class TestEndpoints {
    @GetMapping("/user/dashboard")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String,String>> hello() {
        return ResponseEntity.ok(Map.of("message","Hello from secured world"));
    }

    @GetMapping("/public/landing")
    public ResponseEntity<Map<String,String>> helloUnsecured() {
        return ResponseEntity.ok(Map.of("message","Hello from public landing page"));
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>> adminEndpoint(){
        return ResponseEntity.ok(Map.of("message","Welcome to admin dashboard"));
    }
    @GetMapping("/user/add-task")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String,String>> userEndpoint(){
        return ResponseEntity.ok(Map.of("message","Welcome to user create task"));
    }
}
