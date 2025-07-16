package com.one.alura.ForumHub.exception;

public class DuplicatedContentException extends RuntimeException {
    public DuplicatedContentException(String message) {
        super(message);
    }
}
