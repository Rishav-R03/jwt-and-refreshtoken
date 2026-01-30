# Auth Service – JWT & Refresh Token

## Overview
Production-ready authentication service implementing JWT, refresh tokens, role-based access control, and secure session handling.

## Features
- Access & Refresh Token flow
- Token rotation
- Stateless authentication
- Secure password hashing
- Role-based authorization

## Architecture
- Spring Security filter chain
- Custom AuthenticationProvider
- Stateless JWT validation
- PostgreSQL persistence

## Tech Stack
Java 21+, Spring Boot, Spring Security, PostgreSQL, Docker

## API Flow
Client → JWT Filter → Authentication → SecurityContext → Controller

## Future Improvements
- Redis token blacklist
- OAuth2 integration
- Rate limiting
