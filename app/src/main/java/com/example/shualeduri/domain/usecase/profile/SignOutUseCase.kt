package com.example.shualeduri.domain.usecase.profile

import com.example.shualeduri.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val authRepository: AuthRepository){
    operator fun invoke() {
        return authRepository.signOut()
    }
}