package org.example.app.springbootwebcontacts.utils.validate.create_update_form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormDataForValidate {
    private Long id;
    private UUID customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;


    public FormDataForValidate(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
