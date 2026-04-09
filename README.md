# Cassava Auth System

A robust Authentication and Authorization system built with Spring Boot, connecting to a Supabase PostgreSQL instance. This project was developed as part of the Cassava Backend Interview.

## Getting Started

### Prerequisites
- Java 1.8+
- Maven
- Supabase Account (PostgreSQL)

### Configuration
Update the database connection details in `src/main/resources/application.properties`.

## Features & Task Tracking

### Authentication Flow
- [ ] **Registration** `POST /auth/register`
- [ ] **Email Verification** `POST /auth/verify-email`
- [ ] **Login (JWT)** `POST /auth/login`

### Password Management Flow
- [ ] **Request Password Reset** `POST /auth/forgot-password`
- [ ] **Confirm OTP** `POST /auth/confirm-otp`
- [ ] **Reset Password** `POST /auth/reset-password`

## Tech Stack
- **Framework**: Spring Boot 2.7.18
- **Database**: PostgreSQL (Supabase)
- **Security**: Spring Security + JWT
- **Persistence**: Spring Data JPA
- **Validation**: Spring Validation
- **Email Service**: Unosend

---

## API Documentation (Planned)

### Registration
- **URL**: `/auth/register`
- **Method**: `POST`
- **Body**: `{ "email": "...", "password": "...", "fullName": "..." }`
- **Response**: `201 Created` - Sends OTP to email.

### Verify Email
- **URL**: `/auth/verify-email`
- **Method**: `POST`
- **Body**: `{ "email": "...", "otp": "..." }`
- **Response**: `200 OK` - Marks user as verified.

### Login
- **URL**: `/auth/login`
- **Method**: `POST`
- **Body**: `{ "email": "...", "password": "..." }`
- **Response**: `200 OK` - Returns JWT.
