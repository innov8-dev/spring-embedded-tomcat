package dev.innov8.set.exceptions;

public class ForbiddenRequestException extends RuntimeException {
    public ForbiddenRequestException() {
        super("A forbidden request was made to a protected endpoint!");
    }
}
