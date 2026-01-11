package com.jwt_revision.JwtRevision.exception;

import com.jwt_revision.JwtRevision.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(UserAlreadyExistsException.class)
        public ResponseEntity<Map<String, Object>> handleUserExists(
                        UserAlreadyExistsException ex) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                                Map.of("error", "USER_ALREADY_EXISTS",
                                                "message", ex.getMessage()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> handleValidation(
                        MethodArgumentNotValidException ex) {
                return ResponseEntity.badRequest().body(
                                Map.of("error", "VALIDATION_FAILED",
                                                "message", ex.getMessage()));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Map<String, Object>> handleException(
                        Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                Map.of("error", "INTERNAL_SERVER_ERROR",
                                                "message", ex.getMessage()));
        }

        @ExceptionHandler({InvalidRefreshTokenException.class, RefreshTokenExpireException.class})
        public ResponseEntity<ErrorResponseDTO> handleRefreshToken(
                RuntimeException ex,
                HttpServletRequest request
        ){
                return buildError(
                        HttpStatus.UNAUTHORIZED,
                        ex.getMessage(),
                        request.getRequestURI()
                );
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<ErrorResponseDTO> handleAuthError(AuthenticationException ex,HttpServletRequest request){
                return buildError(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid username or password",
                        request.getRequestURI()
                );
        }

        private ResponseEntity<ErrorResponseDTO> buildError(
                HttpStatus status,
                String message,
                String path
        ){
                ErrorResponseDTO response = ErrorResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .message(message)
                        .path(path)
                        .build();
                return new ResponseEntity<>(response,status);
        }

}
