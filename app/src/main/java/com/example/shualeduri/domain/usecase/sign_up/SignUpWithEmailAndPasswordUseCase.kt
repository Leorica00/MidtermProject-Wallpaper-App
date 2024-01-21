package com.example.shualeduri.domain.usecase.sign_up

import com.example.shualeduri.data.common.Resource
import com.example.shualeduri.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpWithEmailAndPasswordUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Unit>> =
        authRepository.firebaseSignUpWithEmailAndPassword(email, password)
}