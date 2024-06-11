package org.example.app.springbootwebcontacts.utils.validate;

import org.example.app.springbootwebcontacts.utils.validate.enums.IValidateUnit;
import org.example.app.springbootwebcontacts.utils.validate.validate_entity.ValidateResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator<T extends IValidateUnit> {
    public ValidateResult validate(String value, T field) {
        ValidateResult answer = new ValidateResult();
        Pattern pattern = Pattern.compile(field.getValidationRegex());
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            answer.addError(field.getErrorMsg());
            return answer;
        }
        answer.setValid(answer.getErrorsList().isEmpty());
        return answer;
    }
}
