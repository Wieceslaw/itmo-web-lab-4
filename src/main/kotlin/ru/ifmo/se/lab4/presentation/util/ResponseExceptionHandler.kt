package ru.ifmo.se.lab4.presentation.util

import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.ifmo.se.lab4.presentation.scheme.ResponseScheme
import ru.ifmo.se.lab4.presentation.scheme.ResponseStatus
import ru.ifmo.se.lab4.util.exceptions.UserNotFoundException


@ControllerAdvice
class ResponseExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(
        ex: ResponseStatusException,
        request: WebRequest
    ): ResponseEntity<ResponseScheme<Nothing>> {
        return ResponseEntity(
            ResponseScheme(
                ex.reason,
                ResponseStatus.ERROR
            ),
            ex.status
        )
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(
        ex: RuntimeException,
        request: WebRequest
    ): ResponseEntity<ResponseScheme<Nothing>> {
        return ResponseEntity(
            ResponseScheme(
                ex.message,
                ResponseStatus.ERROR
            ),
            HttpStatus.NOT_FOUND
        )
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return ResponseEntity(
            ResponseScheme(
                "Validation error",
                ResponseStatus.ERROR,
                ex.allErrors
            ),
            HttpStatus.BAD_REQUEST
        )
    }
}
