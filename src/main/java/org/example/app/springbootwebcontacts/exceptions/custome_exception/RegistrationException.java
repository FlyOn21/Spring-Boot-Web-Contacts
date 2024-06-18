package org.example.app.springbootwebcontacts.exceptions.custome_exception;

public class RegistrationException extends RuntimeException {
    private final String fragmentName;

    public RegistrationException(String message, String fragmentName) {
        super(message);
        this.fragmentName = fragmentName;
    }
}
