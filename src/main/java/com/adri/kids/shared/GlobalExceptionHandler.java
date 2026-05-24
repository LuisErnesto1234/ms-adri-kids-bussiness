package com.adri.kids.shared;

import com.adri.kids.shared.domain.dtos.ApiErrorResponse;
import com.adri.kids.shared.domain.dtos.ApiResponse;

import com.adri.kids.shared.exceptions.AlreadyExistException;
import com.adri.kids.shared.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<ApiErrorResponse>> handleGenericException(RuntimeException e) {

        var errorResponse = ApiErrorResponse.builder()
                .statusCode(500)
                .message(e.getMessage())
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.<ApiErrorResponse>builder()
                .data(errorResponse)
                .timeStamp(Instant.now())
                .httpCode(500L)
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        var errorResponse = ApiErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(Instant.now())
                .message("Validation Failed")
                .details(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class) // O tu CustomException.class
    public ResponseEntity<ApiErrorResponse> handleBusinessRules(IllegalArgumentException ex) {
        var errorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error de entrada de datos.",
                List.of(ex.getMessage()),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {
        // Loguear el error real en el servidor para que tú lo veas
        ex.printStackTrace();

        var errorResponse = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                List.of(ex.getMessage()),
                Instant.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ApiErrorResponse> handleAlreadyExistException(AlreadyExistException ex) {
        var errorResponse = ApiErrorResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .timestamp(Instant.now())
                .message(ex.getMessage())
                .details(List.of(ex.getMessage()))
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException ex) {
        var errorResponse = ApiErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(Instant.now())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
