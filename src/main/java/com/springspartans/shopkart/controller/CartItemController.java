package com.springspartans.shopkart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springspartans.shopkart.model.*;
import com.springspartans.shopkart.service.CartItemService;
import com.springspartans.shopkart.service.CustomerService;
import com.springspartans.shopkart.service.ProductService;

@Controller
@RequestMapping("/cartitem")
public class CartItemController
{
	@Autowired
	private CartItemService cartservice;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductService productService;
	
	@GetMapping("/cart")
	public String getAllCartItems(Model model)
	{
		List<CartItem> cart = cartservice.getAllCartItems();
		model.addAttribute("cart", cart);
		double totalPrice = cartservice.getCartPrice();
        model.addAttribute("totalPrice", totalPrice);
        Customer customer = customerService.getCustomer(); 
        model.addAttribute("customer", customer);
        if (customer == null) {
			 throw new ResponseStatusException(HttpStatus.UNAUTHORIZED); 
		}
        List<String> categoryList = productService.getAllCategories();
		model.addAttribute("categoryList", categoryList);
   		return "/cartitem/cart";
	}
	
	@PostMapping("/increase/{slno}")
	public String incrementQuantity(@PathVariable int slno, RedirectAttributes redirectAttributes)
	{
		if(cartservice.getBySlno(slno)==null)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		boolean incrementFlag=cartservice.incrementQuantity(slno);
		if(!incrementFlag)
			redirectAttributes.addFlashAttribute("error", "No more stock available for this item");
		return "redirect:/cartitem/cart";
	}
	
	@PostMapping("/decrease/{slno}")
	public String decrementQuantity(@PathVariable int slno)
	{
		if(cartservice.getBySlno(slno)==null)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		cartservice.decrementQuantity(slno);
		return "redirect:/cartitem/cart";
	}
	
	@PostMapping("/delete/{slno}")
	public String deleteCartItem(@PathVariable int slno, Model model)
	{
		if(cartservice.getBySlno(slno)==null)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		cartservice.deleteCartItem(slno);
		return "redirect:/cartitem/cart";
	}
	
	 @PostMapping("/add/{prod_id}")
	 public String addToCart(@PathVariable("prod_id") int id)
	 {		 
		 Customer customer = customerService.getCustomer();
		 if (customer == null) {
			 throw new ResponseStatusException(HttpStatus.UNAUTHORIZED); 
		 }
		 if (productService.getProductById(id) == null) {
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		 }
		 cartservice.addToCart(id, customer);
		 return "redirect:/cartitem/cart";
	 }
	 
}
