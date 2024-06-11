package org.example.app.springbootwebcontacts.exceptions.resolve;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.springbootwebcontacts.exceptions.custome_exception.CRUDException;
import org.example.app.springbootwebcontacts.exceptions.custome_exception.ValidationException;
import org.example.app.springbootwebcontacts.utils.validate.create_update_form.FormDataForValidate;
import org.example.app.springbootwebcontacts.utils.validate.create_update_form.ValidationFormProcessing;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionsResolvers {

    private static final Logger SERVICE_LOGGER =
            LogManager.getLogger(ExceptionsResolvers.class);
    private static final Logger CONSOLE_LOGGER =
            LogManager.getLogger("console_logger");

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView mainExceptionResolver(RuntimeException ex) {
        SERVICE_LOGGER.error("ERROR: {}", ex.getMessage());
        CONSOLE_LOGGER.error("ERROR: {}", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("errorMsg", String.format("ERROR: %s", ex.getMessage()));
        modelAndView.addObject("fragmentName", "list_customers");
        return modelAndView;
    }

    @ExceptionHandler(ValidationException.class)
    public ModelAndView validateExceptionResolver(ValidationException ex) {
        SERVICE_LOGGER.error("ERROR Validated: {}", ex.getMessage());
        CONSOLE_LOGGER.error("ERROR Validated: {}", ex.getMessage());
        ValidationFormProcessing formDataForValidate = ex.getFormDataForValidate();
        String fragmentName = ex.getFragmentName();
        ModelAndView modelAndView = new ModelAndView("index");
        FormDataForValidate formData = formDataForValidate.getData();
        modelAndView.addObject("fragmentName", fragmentName);
        modelAndView.addObject("errorMsg", ex.getMessage());
        modelAndView.addObject("formData", formData);
        modelAndView.addObject("validationFormErrors", formDataForValidate.getValidationFormErrors());
        if (fragmentName.equals("customer_update")) {
            modelAndView.addObject("customer", formData);
        }
        return modelAndView;
    }

    @ExceptionHandler(CRUDException.class)
    public ModelAndView crudExceptionResolver(CRUDException ex) {
        SERVICE_LOGGER.error("ERROR CRUD: {}", ex.getMessage());
        CONSOLE_LOGGER.error("ERROR CRUD: {}", ex.getMessage());
        String fragmentName = ex.getFragmentName();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("errorMsg", String.format("ERROR: %s", ex.getMessage()));
        modelAndView.addObject("fragmentName", fragmentName);
        return modelAndView;
    }
}
