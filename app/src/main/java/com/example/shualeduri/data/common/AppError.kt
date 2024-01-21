package com.example.shualeduri.data.common

import android.util.Log.d
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

sealed class AppError(open val message: String) {
    data class NetworkError(override val message: String) : AppError(message)
    data class HttpError(override val message: String) : AppError(message)
    data class TimeoutError(override val message: String) : AppError(message)
    data class ServerError(override val message: String) : AppError(message)
    data class ClientError(override val message: String) : AppError(message)
    data class UnknownError(override val message: String) : AppError(message)
    data class InvalidAuthenticationInputs(override val message: String): AppError(message)
    data class UserAlreadyExistsError(override val message: String): AppError(message)

    companion object {
        fun fromException(t: Throwable): AppError {
            d("showError", t.toString())
            return when (t) {
                is IOException -> NetworkError("Network error occurred: No Internet")
                is HttpException -> {
                    when (t.code()) {
                        in 400..499 -> {
                            ClientError(t.response()!!.errorBody().toString())
                        }
                        in 500..599 -> ServerError("Server error occurred")
                        else -> HttpError("Http error occurred")
                    }
                }
                is TimeoutException -> TimeoutError("Can not process task")
                is FirebaseAuthInvalidCredentialsException -> InvalidAuthenticationInputs("Email or Password is incorrect try again")
                is FirebaseTooManyRequestsException -> InvalidAuthenticationInputs("Too many wrong tries. try again later")
                is FirebaseNetworkException -> NetworkError("Network error: Not Internet")
                is FirebaseAuthUserCollisionException -> UserAlreadyExistsError(t.message.toString())
                else -> UnknownError("An unexpected error occurred")
            }
        }
    }
}