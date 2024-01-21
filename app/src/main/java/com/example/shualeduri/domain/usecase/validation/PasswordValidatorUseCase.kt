package com.example.challenge.domain.usecase.validator

class PasswordValidatorUseCase {
    operator fun invoke(password: String): Boolean =
        password.isNotBlank() && password.trim().length >= 8
}