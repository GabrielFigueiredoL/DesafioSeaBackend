package com.gabrielfigueiredol.seaApi.infra;

import com.gabrielfigueiredol.seaApi.dto.ErrorResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        String errorMessage = String.join(" || ", errorMessages);

        ErrorResponseDTO responseDTO = new ErrorResponseDTO(errorMessage, String.format("Error(s) found: %d", errorMessages.size()), LocalDateTime.now());
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO("Credenciais inválidas", "Login ou senha incorretos", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException (AccessDeniedException ex) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO("Acesso negado", "Credenciais de acesso negadas", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDTO);
    }
}
