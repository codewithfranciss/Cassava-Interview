# Cassava Auth System

A robust Authentication and Authorization system built with Spring Boot, connecting to a Supabase PostgreSQL instance. This project was developed as part of the Cassava Backend Interview.

## Getting Started

### Prerequisites
- Java 1.8+
- Maven
- Supabase Account (PostgreSQL)


## Features & Task Tracking

### Authentication Flow
- [x] **Registration** `POST /auth/register`
- [x] **Email Verification** `POST /auth/verify-email`
- [x] **Login (JWT)** `POST /auth/login`

### Password Management Flow
- [x] **Request Password Reset** `POST /auth/forgot-password`
- [x] **Confirm OTP** `POST /auth/confirm-otp`
- [x] **Reset Password** `POST /auth/reset-password`

## Tech Stack
- **Framework**: Spring Boot 2.7.18
- **Database**: PostgreSQL (Supabase)
- **Security**: Spring Security + JWT
- **Persistence**: Spring Data JPA
- **Validation**: Spring Validation
- **Email Service**: Custom SMTP Server

---

## API Documentation

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

### Forgot Password
- **URL**: `/auth/forgot-password`
- **Method**: `POST`
- **Body**: `{ "email": "..." }`
- **Response**: `200 OK` - Sends password reset OTP to email.

### Confirm OTP
- **URL**: `/auth/confirm-otp`
- **Method**: `POST`
- **Body**: `{ "email": "...", "otp": "..." }`
- **Response**: `200 OK` - Confirms OTP is valid.

### Reset Password
- **URL**: `/auth/reset-password`
- **Method**: `POST`
- **Body**: `{ "email": "...", "otp": "...", "newPassword": "..." }`
- **Response**: `200 OK` - Password successfully changed.
