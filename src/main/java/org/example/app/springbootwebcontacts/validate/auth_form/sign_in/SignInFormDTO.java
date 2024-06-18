package org.example.app.springbootwebcontacts.validate.auth_form.sign_in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInFormDTO {
    private String email;
    private String password;
}
