package com.jwt_revision.JwtRevision.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin("*")
public class TestEndpoints {
    @GetMapping("/user/dashboard")
    @PreAuthorize("hasRole('USER')")
    public String hello() {
        return "Hello secured world.";
    }

    @GetMapping("/public/landing")
    public String helloUnsecured() {
        return "Hello unsecured world.";
    }
}
