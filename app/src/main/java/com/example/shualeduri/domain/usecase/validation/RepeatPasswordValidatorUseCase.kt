package com.example.shualeduri.domain.usecase.validation

class RepeatPasswordValidatorUseCase {
    operator fun invoke(password: String, repeatPassword: String): Boolean =
        password.isNotBlank() && repeatPassword == password
}