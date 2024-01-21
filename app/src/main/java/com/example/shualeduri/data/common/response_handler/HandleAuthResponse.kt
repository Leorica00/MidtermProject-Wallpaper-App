package com.example.shualeduri.data.common.response_handler

import com.example.shualeduri.data.common.AppError
import com.example.shualeduri.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HandleAuthResponse {
    suspend fun <T> safeAuthCall(call: suspend () -> T): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading(true))
        try {
            call()
            emit(Resource.Success(Unit))
        } catch (t: Throwable) {
            emit(Resource.Error(AppError.fromException(t).message, t))
        }
        emit(Resource.Loading(false))
    }
}
