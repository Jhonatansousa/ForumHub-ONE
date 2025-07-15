package com.one.alura.ForumHub.exception;

public class TopicContentAlreadyExistsException extends RuntimeException {
    public TopicContentAlreadyExistsException(String message) {
        super(message);
    }
}
