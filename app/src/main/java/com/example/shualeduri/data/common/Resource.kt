package com.example.shualeduri.data.common

sealed class Resource<T>(
    val isLoading: Boolean = false,
    val successData: T? = null,
    val errorMessage: String = "",
    val error: Throwable? = null
) {
    data class Loading<T>(val loading: Boolean) : Resource<T>(isLoading = loading)
    data class Success<T>(val response: T) : Resource<T>(successData = response)
    data class Error<T>(val message: String, val throwable: Throwable) : Resource<T>(errorMessage = message, error = throwable)
}