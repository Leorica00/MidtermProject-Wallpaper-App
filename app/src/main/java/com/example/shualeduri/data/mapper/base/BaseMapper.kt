package com.example.shualeduri.data.mapper.base

import com.example.shualeduri.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun <Dto : Any, Domain : Any> Flow<Resource<Dto>>.asResource(
    onSuccess: suspend (Dto) -> Domain,
): Flow<Resource<Domain>> {
    return this.map {
        when (it) {
            is Resource.Success -> Resource.Success(response = onSuccess.invoke(it.successData!!))
            is Resource.Error -> Resource.Error(message = it.message, throwable = it.throwable)
            is Resource.Loading -> Resource.Loading(loading = it.loading)
        }
    }
}
