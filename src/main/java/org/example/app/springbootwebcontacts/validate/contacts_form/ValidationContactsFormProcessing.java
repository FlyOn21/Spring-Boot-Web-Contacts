package org.example.app.springbootwebcontacts.validate.contacts_form;

import org.example.app.springbootwebcontacts.contacts_app.entity.Contact;
import org.example.app.springbootwebcontacts.contacts_app.repository.ContactsRepository;
import org.example.app.springbootwebcontacts.validate.enums.EValidateContacts;
import org.example.app.springbootwebcontacts.validate.Validator;
import org.example.app.springbootwebcontacts.validate.enums.IValidateUnit;
import org.example.app.springbootwebcontacts.validate.interfaces.IFormDataProcessing;
import org.example.app.springbootwebcontacts.validate.validate_entity.ValidateResultDTO;

import java.util.HashMap;
import java.util.Optional;


public class ValidationContactsFormProcessing implements IFormDataProcessing<FormDataForValidateDTO> {
    private final HashMap<String, String> validationFormErrors = new HashMap<>();
    private boolean isValidForm = true;
    private final FormDataForValidateDTO data;
    private final ContactsRepository repository;
    private Long id;


    public ValidationContactsFormProcessing(ContactsRepository repository, FormDataForValidateDTO data) {
        this.repository = repository;
        this.data = data;
        validateFirstName(data.getFirstName());
        validateLastName(data.getLastName());
        validateEmail(data.getEmail());
        validatePhone(data.getPhoneNumber());
    }



    public ValidationContactsFormProcessing(ContactsRepository repository, FormDataForValidateDTO data, Long id) {
        this.repository = repository;
        this.id = id;
        this.data = data;
        validateFirstName(data.getFirstName());
        validateLastName(data.getLastName());
        validateEmail(data.getEmail());
        validatePhone(data.getPhoneNumber());
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
    public FormDataForValidateDTO getData() {
        return data;
    }


    private ValidateResultDTO validateField(String value, IValidateUnit validationType) {
        Validator<IValidateUnit> validator = new Validator<>();
        if (id != null) {
            if (value == null) {
                ValidateResultDTO answer = new ValidateResultDTO();
                answer.setValid(true);
                return answer;
            }
            return validator.validate(value, validationType);
        } 
        if (value==null) {
            ValidateResultDTO answer = new ValidateResultDTO();
            answer.addError(" Is empty (required)");
            return answer;
        }
        return validator.validate(value, validationType);
    }

    private void validateFirstName(String firstName) {
            ValidateResultDTO answer = validateField(firstName, EValidateContacts.FIRST_NAME);
            if (!answer.isValid()) {
                isValidForm = false;
                validationFormErrors.put("firstName", String.join(", ", answer.getErrorsList()));
            }

    }

    private void validateLastName(String  lastName) {
        ValidateResultDTO answer = validateField(lastName, EValidateContacts.LAST_NAME);
        if (!answer.isValid()) {
            isValidForm = false;
            validationFormErrors.put("lastName", String.join(", ", answer.getErrorsList()));
        }
    }

    private void validateEmail(String  email) {
        ValidateResultDTO answer = validateField(email, EValidateContacts.EMAIL);
        if (!answer.isValid()) {
            isValidForm = false;
            validationFormErrors.put("email", String.join(", ", answer.getErrorsList()));
        }
        if (id == null && isEmailExists(email)) {
            isValidForm = false;
            answer.addError("Email already exists");
            validationFormErrors.put("email", String.join(", ", answer.getErrorsList()));
        }
        if (id != null && isEmailExists(email) && !isCustomerEmail(email, id)) {
            isValidForm = false;
            answer.addError("Email already exists");
            validationFormErrors.put("email", String.join(", ", answer.getErrorsList()));
        }
    }

    private void validatePhone(String  phone) {
        ValidateResultDTO answer = validateField(phone, EValidateContacts.PHONE);
        if (!answer.isValid()) {
            isValidForm = false;
            validationFormErrors.put("phoneNumber", String.join(", ", answer.getErrorsList()));
        }
    }

    private boolean isEmailExists(String email) {
        Optional<Contact> contact = repository.findByEmail(email);
        return contact.isPresent();
    }

    private boolean isCustomerEmail(String email, Long id) {
        Optional<Contact> contact = repository.findByEmailAndId(email, id);
        return contact.isPresent();
    }
}
