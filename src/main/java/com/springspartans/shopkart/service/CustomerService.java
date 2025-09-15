package com.springspartans.shopkart.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.springspartans.shopkart.exception.InvalidImageUploadException;
import com.springspartans.shopkart.exception.InvalidPasswordException;
import com.springspartans.shopkart.model.Customer;
import com.springspartans.shopkart.repository.CustomerRepository;
import com.springspartans.shopkart.util.ImageUploadValidator;
import com.springspartans.shopkart.util.PasswordEncoder;
import com.springspartans.shopkart.util.PasswordValidator;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class CustomerService {
	
	@Autowired
	private String uploadPath; 
	
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private HttpSession httpSession;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private ImageUploadValidator imageUploadValidator;

    public boolean login(String email, String password) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent() && passwordEncoder.matches(password, customer.get().getPassword())) {
        	 Customer loggedInCustomer = customer.get();
             loggedInCustomer.setLast_login_date(Timestamp.from(Instant.now()));
             customerRepository.save(loggedInCustomer);
//             System.out.println("New Last Login Date: " + loggedInCustomer.getLast_login_date());
             httpSession.setAttribute("loggedInCustomer", loggedInCustomer);
            return true;
        }
        return false;
    }

    public boolean signup(Customer customer) throws InvalidPasswordException {
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            return false;
        }
        if (!passwordValidator.isValidPassword(customer.getPassword())) {
        	throw new InvalidPasswordException("Invalid password entered!");
        }
        customer.setSignup_date(Timestamp.from(Instant.now()));
        System.out.println("Signup Date: " + customer.getSignup_date());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
        return true;
    }

    public Customer getCustomer() {
        return (Customer) httpSession.getAttribute("loggedInCustomer");
    }

    public boolean updateCustomer(
    	String newName, long newPhone, String newAddress, 
    	String newPassword, String oldPassword, MultipartFile profilePicture
    ) throws IOException, InvalidPasswordException, InvalidImageUploadException {
        Customer loggedInCustomer = (Customer) httpSession.getAttribute("loggedInCustomer");
        if (loggedInCustomer != null && passwordEncoder.matches(oldPassword, loggedInCustomer.getPassword())) {
        	if (!newPassword.isEmpty() && !passwordValidator.isValidPassword(newPassword)) {
        		throw new InvalidPasswordException("Invalid password entered!");
        	}
            String encodedPassword = newPassword.isEmpty() ? loggedInCustomer.getPassword() : passwordEncoder.encode(newPassword);
            String profilePictureName = (profilePicture != null && !profilePicture.isEmpty()) ? "user" + loggedInCustomer.getId() + ".jpg" : null;
            Customer updatedCustomer = new Customer(loggedInCustomer.getId(), newName, loggedInCustomer.getEmail(), encodedPassword, newAddress, newPhone, profilePictureName, loggedInCustomer.getSignup_date(), loggedInCustomer.getLast_login_date());
            customerRepository.save(updatedCustomer);
            httpSession.setAttribute("loggedInCustomer", updatedCustomer);
            
            if (imageUploadValidator.isValidImage(profilePicture)) {
            	String customerUploadPath = uploadPath + "/customer";
                File destination = new File(customerUploadPath);
                if (!destination.exists()) {
                	boolean created = destination.mkdirs();
                	destination.setWritable(true);
                	if (created) {
                		System.out.println("Created directory : " + destination.getAbsolutePath());
                	} else {
                		System.out.println(destination.getAbsolutePath() + "already exists");
                	}
                }
                File fileToSave = new File(destination, profilePictureName);
                profilePicture.transferTo(fileToSave);
                System.out.println("Saved file : " + fileToSave.getAbsolutePath());
            }  else if (profilePicture != null && !profilePicture.isEmpty()) {
            	throw new InvalidImageUploadException("Improper file format!");
            }
            return true;
        }
        return false;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    public void deleteCustomer(int customerId) {
            customerRepository.deleteById(customerId);
    }
    
    public int countCustomers() {
        return (int) customerRepository.count();
    }
    
    public int countSignupByDate(Timestamp date) {
        try {
            LocalDate localDate = date.toLocalDateTime().toLocalDate();
            Timestamp startTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
            Timestamp endTimestamp = Timestamp.valueOf(localDate.atTime(23, 59, 59));
            return customerRepository.countBySignup_dateBetween(startTimestamp, endTimestamp);
        } catch (DateTimeParseException e) {
            return 0;
        }
    }

    public int countLoginByDate(Timestamp date) {
        try {
            LocalDate localDate = date.toLocalDateTime().toLocalDate();
            Timestamp startTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
            Timestamp endTimestamp = Timestamp.valueOf(localDate.atTime(23, 59, 59));
            return customerRepository.countByLast_login_dateBetween(startTimestamp, endTimestamp);
        } catch (DateTimeParseException e) {
            return 0;
        }
    }

    public void logout() {
        httpSession.invalidate();
    }
    
}
