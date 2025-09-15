package com.springspartans.shopkart.controller;

import com.springspartans.shopkart.exception.InvalidImageUploadException;
import com.springspartans.shopkart.exception.InvalidPasswordException;
import com.springspartans.shopkart.model.Customer;
import com.springspartans.shopkart.service.CustomerService;
import com.springspartans.shopkart.service.ProductService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public String login() {
        return "customer/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "customer/signup";
    }

    @GetMapping("/update")
    public String updateInfo(Model model) {
        Customer customer = customerService.getCustomer();
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        model.addAttribute("customer", customer);
        List<String> categoryList = productService.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        return "customer/updateinfo";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        if (email == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        boolean success = customerService.login(email, password);
        if (success) {
            return "redirect:/product";
        } else {
        	return "redirect:/?msg=failed";
        }
    }

    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute Customer customer) {
        boolean success = false;
		try {
			success = customerService.signup(customer);
		} catch (InvalidPasswordException e) {
			return "redirect:/signup?msg=invalidPassword";
		}
        if (success) {
            return "redirect:/";
        } else {
        	return "redirect:/signup?msg=failed";
        }
    }

    @PostMapping("/update")
    public String updateCustomer(
    	@RequestParam String newName, @RequestParam long newPhone, @RequestParam String newAddress, 
        @RequestParam String newPassword, @RequestParam String oldPassword, 
        @RequestParam(required = false) MultipartFile profilePicture, Model model
    ) {
        if (newName == null || newPassword == null || oldPassword == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        boolean success = false;
		try {
			success = customerService.updateCustomer(newName, newPhone, newAddress, newPassword, oldPassword, profilePicture);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InvalidPasswordException e) {
			e.printStackTrace();
			return "redirect:/update?msg=invalidPassword";
		} catch (InvalidImageUploadException e) {
			e.printStackTrace();
			return "redirect:/update?msg=invalidFile";
		}
        if (success) {
            Customer customer = customerService.getCustomer();
            model.addAttribute("customer", customer);
            return "redirect:/product";
        } else {
        	return "redirect:/update?msg=failed";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        customerService.logout();
        return "redirect:/?logout=true";
    }
    
}
