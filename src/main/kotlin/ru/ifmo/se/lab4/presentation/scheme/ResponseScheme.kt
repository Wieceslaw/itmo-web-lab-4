package ru.ifmo.se.lab4.presentation.scheme

data class ResponseScheme<T>(
    val message: String?,
    val status: ResponseStatus,
    val data: T? = null
)

enum class ResponseStatus {
    SUCCESS,
    ERROR,
    AUTH_ERROR
}
