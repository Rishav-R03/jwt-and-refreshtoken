package com.jwt_revision.JwtRevision.exception;

public class RefreshTokenExpireException extends RuntimeException {
    public RefreshTokenExpireException(String message) {
        super(message);
    }
}
