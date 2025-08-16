package com.one.alura.ForumHub.handler;


import com.one.alura.ForumHub.dto.APIResponse;
import com.one.alura.ForumHub.dto.ErrorDTO;
import com.one.alura.ForumHub.exception.DuplicatedContentException;
import com.one.alura.ForumHub.exception.InvalidCredentialsException;
import com.one.alura.ForumHub.exception.ResourceNotFoundException;
import com.one.alura.ForumHub.util.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalHandlerException {

    //trata exceções com annotations @Valid no controller
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorDTO> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorDTO errorDTO = new ErrorDTO(error.getField(), error.getDefaultMessage());
                    errors.add(errorDTO);
                });

        return new ResponseEntity<>(ResponseUtils.buildErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ResponseUtils.buildSingleError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DuplicatedContentException.class)
    public ResponseEntity<APIResponse<Void>> handleDuplicatedContent(DuplicatedContentException ex) {
        return new ResponseEntity<>(ResponseUtils.buildSingleError(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<APIResponse<Void>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return new ResponseEntity<>(ResponseUtils.buildSingleError(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }




}
