package com.example.shualeduri.domain.usecase.profile

import com.example.shualeduri.domain.repository.AuthRepository
import javax.inject.Inject

class SetUserImageUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(url: String) = authRepository.setUserImage(url)
}