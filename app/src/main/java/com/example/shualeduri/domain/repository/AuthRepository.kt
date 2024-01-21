package com.example.shualeduri.domain.repository

import com.example.shualeduri.data.common.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

typealias SignUpResource = Flow<Resource<Unit>>
typealias SignInResource = Flow<Resource<Unit>>
typealias SendPasswordResetEmailResource = Flow<Resource<Unit>>
typealias UpdateDisplayResource = Flow<Resource<Unit>>
typealias SetUserImage = Flow<Resource<Unit>>

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String): SignUpResource

    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): SignInResource

    suspend fun updateDisplayName(fullName: String): UpdateDisplayResource

    suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResource

    fun signOut()

    fun getUser(): FirebaseUser?

    suspend fun setUserImage(url: String): SetUserImage
}