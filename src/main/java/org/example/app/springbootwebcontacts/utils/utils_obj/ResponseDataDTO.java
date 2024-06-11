package org.example.app.springbootwebcontacts.utils.utils_obj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.app.springbootwebcontacts.utils.validate.create_update_form.FormDataForValidate;

import java.util.HashMap;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataDTO<T> {
    private boolean isSuccess;
    private boolean isValid;
    private List<T> data;
    private FormDataForValidate formData;
    private String msg;
    private List<String> commonErrors;
    private HashMap<String, String> validationFormErrors;

}
