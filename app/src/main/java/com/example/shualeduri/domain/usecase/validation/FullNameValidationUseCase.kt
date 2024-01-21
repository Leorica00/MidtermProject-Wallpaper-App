package com.example.shualeduri.domain.usecase.validation

class FullNameValidationUseCase {
    operator fun invoke(fullName: String): Boolean {
        return fullName.isNotBlank()
    }
}