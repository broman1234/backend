package com.mm.backend.exception

import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionHandler {
    companion object : KLogging()

    @ExceptionHandler(Exception::class)
    fun internalServerErrorHandler(exception: Exception, request: WebRequest): ResponseEntity<Any> {
        return returnApiErrorResponseEntity(request, exception, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(exception: IllegalArgumentException, request: WebRequest): ResponseEntity<Any> {
        return returnApiErrorResponseEntity(request, exception, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    private fun returnApiErrorResponseEntity(
        request: WebRequest,
        exception: Exception,
        httpStatus: HttpStatus
    ): ResponseEntity<Any> {
        logger.error(
            "Error occurred while processing request: $request, " +
                    "httpStatus: $httpStatus, " +
                    "exceptionType: $exception.javaClass.simpleName, " +
                    "message: ${exception.message}",
        )

        return ResponseEntity(
            exception.javaClass.simpleName,
            httpStatus
        )
    }
}