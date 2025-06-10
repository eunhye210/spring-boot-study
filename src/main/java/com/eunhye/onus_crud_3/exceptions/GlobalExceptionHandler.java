package com.eunhye.onus_crud_3.exceptions;

//package com.teddy.spring_crud.exception;
//
//import com.teddy.spring_crud.dtos.ApiResponse;
//import com.teddy.spring_crud.dtos.ErrorDetails;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
//import java.time.LocalDateTime;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
//            ResourceNotFoundException exception,
//            WebRequest webRequest
//    ) {
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                exception.getMessage(),
//                webRequest.getDescription(false),
//                "EMPLOYEE_NOT_FOUND"
//        );
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }
//
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
//        ApiResponse<String> response = ApiResponse.<String>builder()
//                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .message("서버 내부 오류: " + ex.getMessage())
//                .data(null)
//                .build();
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }
//
////    @ExceptionHandler(Exception.class)
////    public ResponseEntity<ErrorDetails> handleGlobalException(
////            Exception exception,
////            WebRequest webRequest
////    ) {
////        ErrorDetails errorDetails = new ErrorDetails(
////                LocalDateTime.now(),
////                exception.getMessage(),
////                webRequest.getDescription(false),
////                "INTERNAL_SERVER_ERROR"
////        );
////        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
////    }
//
//


//}



import com.eunhye.onus_crud_3.dtos.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 공통 에러 응답 생성 메서드
    private <T> ErrorResponseDTO<T> buildErrorResponse(HttpStatus status, String message, T data) {
        return new ErrorResponseDTO<>(
                status.value(),
                message,
                LocalDateTime.now(),
                data
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO<String>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResponse(HttpStatus.BAD_REQUEST, "유효성 검사 실패", errorMessage));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponseDTO<String>> handleDuplicateResource(DuplicateResourceException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildErrorResponse(HttpStatus.CONFLICT, "중복된 리소스가 존재합니다.", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO<String>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.", ex.getMessage()));
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ApiResponse<String>> handleIllegalArgument(IllegalArgumentException ex) {
//        return ResponseEntity.badRequest().body(
//                ApiResponse.<String>builder()
//                        .statusCode(HttpStatus.BAD_REQUEST.value())
//                        .message("잘못된 요청입니다.")
//                        .data(ex.getMessage())
//                        .build()
//        );
//    }
//
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<ApiResponse<String>> handleBadJson(HttpMessageNotReadableException ex) {
//        return ResponseEntity.badRequest().body(
//                ApiResponse.<String>builder()
//                        .statusCode(HttpStatus.BAD_REQUEST.value())
//                        .message("요청 형식이 잘못되었습니다.")
//                        .data(ex.getMessage())
//                        .build()
//        );
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ApiResponse<String>> handleConstraintViolation(ConstraintViolationException ex) {
//        String message = ex.getConstraintViolations().iterator().next().getMessage();
//        return ResponseEntity.badRequest().body(
//                ApiResponse.<String>builder()
//                        .statusCode(HttpStatus.BAD_REQUEST.value())
//                        .message("제약 조건 위반")
//                        .data(message)
//                        .build()
//        );
//    }

}

