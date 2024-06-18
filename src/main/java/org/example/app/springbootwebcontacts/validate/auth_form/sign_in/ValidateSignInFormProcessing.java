package org.example.app.springbootwebcontacts.validate.auth_form.sign_in;

import org.example.app.springbootwebcontacts.validate.Validator;
import org.example.app.springbootwebcontacts.validate.enums.EValidateAuth;
import org.example.app.springbootwebcontacts.validate.enums.IValidateUnit;
import org.example.app.springbootwebcontacts.validate.interfaces.IFormDataProcessing;
import org.example.app.springbootwebcontacts.validate.validate_entity.ValidateResultDTO;

import java.util.HashMap;

public class ValidateSignInFormProcessing implements IFormDataProcessing<SignInFormDTO> {
    private final HashMap<String, String> validationFormErrors = new HashMap<>();
    private boolean isValidForm = true;
    private final SignInFormDTO data;

    public ValidateSignInFormProcessing(SignInFormDTO data) {
        this.data = data;
        validateEmail(data.getEmail());
    }

    @Override
    public HashMap<String, String> getValidationFormErrors() {
        return validationFormErrors;
    }

    @Override
    public boolean getIsValidForm() {
        return isValidForm;
    }

    @Override
    public SignInFormDTO getData() {
        return data;
    }

    private ValidateResultDTO validateField(String value, IValidateUnit validationType) {
        Validator<IValidateUnit> validator = new Validator<>();
        if (value==null) {
            ValidateResultDTO answer = new ValidateResultDTO();
            answer.addError(" Is empty (required)");
            return answer;
        }
        return validator.validate(value, validationType);
    }

    private void validateEmail(String email) {
        ValidateResultDTO answer = validateField(email, EValidateAuth.EMAIL);
        if (!answer.isValid()) {
            isValidForm = false;
            validationFormErrors.put("email", String.join(", ", answer.getErrorsList()));
        }
    }
}
