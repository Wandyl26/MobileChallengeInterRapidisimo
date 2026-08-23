package com.example.intercommerceapp.core.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

fun <T> safeApiCall(apiCall: suspend () -> T): Flow<ApiResult<T>> = flow {
    emit(ApiResult.Loading)
    try {
        val data = apiCall()
        emit(ApiResult.Success(data))
    } catch (e: IOException) {
        emit(ApiResult.Error(e))
    } catch (e: SocketTimeoutException) {
        emit(ApiResult.Error(e))
    } catch (e: HttpException) {
        emit(ApiResult.Error(e))
    } catch (e: Exception) {
        emit(ApiResult.Error(e))
    }
}