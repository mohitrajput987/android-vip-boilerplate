package com.otb.vipboilerplate.common

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException

/**
 * Created by Mohit Rajput on 10/5/19.
 * Handle server error and convert error models for other layers
 */
enum class ErrorType {
    SESSION_EXPIRED,
    UNEXPECTED_ERROR,
    INVALID_INPUT,
    RESOURCE_NOT_FOUND
}

private interface ResponseCode {
    companion object {
        val TOKEN_EXPIRED = 401
        val BAD_REQUEST = 400
        val NOT_FOUND = 404
        val UNPROCESSABLE_ENTITY = 422
    }
}

data class Error(
    val message: String,
    val type: ErrorType
)

class ErrorHandler {
    companion object {
        fun getError(throwable: Throwable): Error {
            val message = throwable.message
            return Error(getErrorMessage(throwable), getErrorType(throwable))
        }

        private fun getErrorMessage(throwable: Throwable): String {
            var message: String = throwable.message ?: Constants.MESSAGE.UNEXPECTED_ERROR
            if (throwable is HttpException) {
                val errorBody = throwable.response().errorBody()
                if (errorBody != null) {
                    val errorMessage = errorBody.string()
                    message = errorMessage ?: message
                }
            }
            return message
        }

        private fun getErrorType(throwable: Throwable): ErrorType {
            if (throwable is HttpException) {
                when (throwable.code()) {
                    ResponseCode.TOKEN_EXPIRED -> return ErrorType.SESSION_EXPIRED
                    ResponseCode.UNPROCESSABLE_ENTITY -> return ErrorType.INVALID_INPUT
                    ResponseCode.BAD_REQUEST -> return ErrorType.INVALID_INPUT
                    ResponseCode.NOT_FOUND -> return ErrorType.RESOURCE_NOT_FOUND
                    else -> return ErrorType.UNEXPECTED_ERROR
                }
            } else {
                return ErrorType.UNEXPECTED_ERROR
            }
        }
    }
}