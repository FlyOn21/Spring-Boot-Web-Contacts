package org.example.app.springbootwebcontacts.exceptions.custome_exception;

import lombok.Getter;
import org.example.app.springbootwebcontacts.validate.interfaces.IFormDataProcessing;


@Getter
public class ValidationException extends RuntimeException{
    private final IFormDataProcessing<?> formDataForValidate;
    private final String fragmentName;

    public ValidationException(String message, IFormDataProcessing<?> formDataForValidate, String fragmentName) {
        super(message);
        this.formDataForValidate = formDataForValidate;
        this.fragmentName = fragmentName;
    }
}
