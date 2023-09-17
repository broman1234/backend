package com.mm.backend.exception

import mu.KLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionHandler {
    companion object : KLogging()

    @ExceptionHandler(Exception::class)
    fun internalServerErrorHandler(exception: Exception, request: WebRequest): ResponseEntity<Any> {
        return returnApiErrorResponseEntity(request, exception, httpStatus = HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(exception: IllegalArgumentException, request: WebRequest): ResponseEntity<Any> {
        return returnApiErrorResponseEntity(request, exception, httpStatus = HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    fun handleUsernameExistsException(exception: UsernameAlreadyExistsException, request: WebRequest): ResponseEntity<Any> {
        return returnApiErrorResponseEntity(request, exception, httpStatus = HttpStatus.CONFLICT)
    }

    @ExceptionHandler(BookAlreadyExistsException::class)
    fun handleUniqueConstraintsException(exception: BookAlreadyExistsException, request: WebRequest): ResponseEntity<Any> {
        return returnApiErrorResponseEntity(request, exception, httpStatus = HttpStatus.CONFLICT)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(exception: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<Any> {
        return returnApiErrorResponseEntity(request, exception, httpStatus = HttpStatus.BAD_REQUEST)
    }

    private fun returnApiErrorResponseEntity(
        request: WebRequest,
        exception: Exception,
        httpStatus: HttpStatus
    ): ResponseEntity<Any> {
        if (exception is MethodArgumentNotValidException) {
            val errors = exception.bindingResult.fieldErrors.map { it.defaultMessage }.toList()
            logger.error(
                "Error occurred while processing request: $request, " +
                        "httpStatus: $httpStatus, " +
                        "exceptionType: $exception.javaClass.simpleName, " +
                        "bindResult: $errors, " +
                        "message: ${exception.message}"
            )
        } else {
            logger.error(
                "Error occurred while processing request: $request, " +
                        "httpStatus: $httpStatus, " +
                        "exceptionType: $exception.javaClass.simpleName, " +
                        "message: ${exception.message}",
            )
        }

        return ResponseEntity.status(httpStatus).body(exception.javaClass.simpleName)
    }
}