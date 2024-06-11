package org.example.app.springbootwebcontacts.exceptions.custome_exception;

import lombok.Getter;
import org.example.app.springbootwebcontacts.utils.validate.create_update_form.ValidationFormProcessing;


@Getter
public class ValidationException extends RuntimeException{
        private final ValidationFormProcessing formDataForValidate;
        private final String fragmentName;

        public ValidationException(String message, ValidationFormProcessing formDataForValidate, String fragmentName) {
            super(message);
            this.formDataForValidate = formDataForValidate;
            this.fragmentName = fragmentName;
        }

}
