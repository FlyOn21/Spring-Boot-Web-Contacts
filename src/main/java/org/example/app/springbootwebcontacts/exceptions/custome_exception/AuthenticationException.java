package org.example.app.springbootwebcontacts.exceptions.custome_exception;

public class AuthenticationException extends RuntimeException{
    private final String fragmentName;

    public AuthenticationException(String message, String fragmentName) {
        super(message);
        this.fragmentName = fragmentName;
    }
}
