package com.example.shualeduri.data.repository

import android.net.Uri
import com.example.shualeduri.data.common.response_handler.HandleAuthResponse
import com.example.shualeduri.domain.repository.AuthRepository
import com.example.shualeduri.domain.repository.SetUserImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val handleAuthResponse: HandleAuthResponse
) : AuthRepository {
    override val currentUser get() = auth.currentUser

    override suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String) =
        handleAuthResponse.safeAuthCall {
            auth.createUserWithEmailAndPassword(email, password).await()
        }

    override suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String) =
        handleAuthResponse.safeAuthCall {
            auth.signInWithEmailAndPassword(email, password).await()
        }

    override suspend fun updateDisplayName(fullName: String) =
        handleAuthResponse.safeAuthCall {
            currentUser?.updateProfile(userProfileChangeRequest {
                displayName = fullName
            })?.await()
        }

    override suspend fun sendPasswordResetEmail(email: String) =
        handleAuthResponse.safeAuthCall {
            auth.sendPasswordResetEmail(email).await()
        }

    override fun signOut() = auth.signOut()

    override fun getUser(): FirebaseUser? {
        return auth.currentUser
    }

    override suspend fun setUserImage(url: String): SetUserImage =
        handleAuthResponse.safeAuthCall {
            currentUser?.updateProfile(userProfileChangeRequest {
                photoUri = Uri.parse(url)
            })?.await()
        }
}