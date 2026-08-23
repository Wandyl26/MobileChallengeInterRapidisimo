package com.example.intercommerceapp.core.util

import java.io.IOException
import java.net.SocketTimeoutException
import retrofit2.HttpException

class ErrorHandler {
    companion object {
        fun getErrorMessage(exception: Throwable): String {
            return when (exception) {
                is IOException -> "Error de conexión. Verifica tu red."
                is SocketTimeoutException -> "Tiempo de espera agotado."
                is HttpException -> when (exception.code()) {
                    404 -> "Recurso no encontrado."
                    500 -> "Error interno del servidor."
                    else -> "Error HTTP ${exception.code()}"
                }
                else -> "Error desconocido: ${exception.message}"
            }
        }
    }
}