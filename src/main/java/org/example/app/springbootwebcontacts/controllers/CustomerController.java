package org.example.app.springbootwebcontacts.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.springbootwebcontacts.entity.customer.Customer;
import org.example.app.springbootwebcontacts.service.CustomerService;
import org.example.app.springbootwebcontacts.service.DTO.CustomerDTO;
import org.example.app.springbootwebcontacts.utils.utils_obj.ResponseDataDTO;
import org.example.app.springbootwebcontacts.utils.validate.create_update_form.FormDataForValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class CustomerController {
    private static final Logger CONSOLE_LOGGER =
            LogManager.getLogger("console_logger");

    @Autowired
    private CustomerService customerService;

    @GetMapping("customers")
    public String fetchAllCustomers(Model model) {
        ResponseDataDTO<CustomerDTO> allCustomers = customerService.getAll("customers_list");
        model.addAttribute("listCustomers", allCustomers.getData());
        model.addAttribute("fragmentName", "customers_list");
        return "index";
    }

    @GetMapping("customers/create")
    public String createCustomer(Model model) {
        model.addAttribute("fragmentName", "customer_add");
        return "index";
    }

    @PostMapping("customers/create")
    public RedirectView addCustomer(@ModelAttribute FormDataForValidate input, RedirectAttributes attributes) {
        ResponseDataDTO<Customer> createResponse = customerService.create(input, "customer_add");
        RedirectView successRedirectView = new RedirectView("/customers");
        attributes.addFlashAttribute("successMsg", createResponse.getMsg());
        CONSOLE_LOGGER.info("Customer created successfully.");
        return successRedirectView;
    }

    @GetMapping("customers/update")
    public String updateCustomer(Model model, @RequestParam(name = "id") Long id) {
        ResponseDataDTO<Customer> getById = customerService.getById(id, "customer_update");
        model.addAttribute("fragmentName", "customer_update");
        model.addAttribute("customer", getById.getData().getFirst());
        return "index";
        }

    @PostMapping("customers/update/processing")
    public RedirectView updateCustomer(@ModelAttribute FormDataForValidate input, RedirectAttributes attributes) {
        ResponseDataDTO<Customer> updateResponse = customerService.update(input, "customer_update");
        RedirectView successRedirectView = new RedirectView("/customers");
        attributes.addFlashAttribute("successMsg", updateResponse.getMsg());
        CONSOLE_LOGGER.info("Customer update successfully.");
        return successRedirectView;
    }

    @PostMapping("customers/delete")
    public RedirectView deleteCustomer(@ModelAttribute("id") Long id, RedirectAttributes attributes) {
        ResponseDataDTO<Customer> deleteResponse = customerService.delete(id, "customers_list");
            RedirectView successRedirectView = new RedirectView("/customers");
            attributes.addFlashAttribute("successMsg", deleteResponse.getMsg());
            CONSOLE_LOGGER.info("Customer deleted successfully.");
            return successRedirectView;
    }
}
