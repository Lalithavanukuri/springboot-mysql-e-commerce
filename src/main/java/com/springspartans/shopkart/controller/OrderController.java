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

import com.springspartans.shopkart.model.Customer;
import com.springspartans.shopkart.model.Order;
import com.springspartans.shopkart.model.Order.OrderStatus;
import com.springspartans.shopkart.service.CustomerService;
import com.springspartans.shopkart.service.OrderService;
import com.springspartans.shopkart.service.ProductService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CustomerService customerService;
	
	@GetMapping
	public String getOrdersOfLoggedInCustomer(Model model) {
		List<String> categoryList = productService.getAllCategories();
		model.addAttribute("categoryList", categoryList);
		Customer customer = customerService.getCustomer();
        model.addAttribute("customer", customer);
        if (customer == null) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }	
        List<Order> orderList = orderService.getOrdersOfLoggedInCustomer();
		model.addAttribute("orderList", orderList);
		int orderCountByStatusArr[] = {
			orderService.countOrdersByStatusForCustId(OrderStatus.Pending),
			orderService.countOrdersByStatusForCustId(OrderStatus.Shipped),
			orderService.countOrdersByStatusForCustId(OrderStatus.Delivered),
			orderService.countOrdersByStatusForCustId(OrderStatus.Cancelled)
		};
		model.addAttribute("orderCountByStatusArr", orderCountByStatusArr);
		return "order/history";
	}
	
	@GetMapping("/{id}")
	public String getOrderById(@PathVariable int id, Model model) {
		List<String> categoryList = productService.getAllCategories();
		model.addAttribute("categoryList", categoryList);
		Customer customer = customerService.getCustomer();
        model.addAttribute("customer", customer);
        if (customer == null) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }		
		Order order = orderService.getOrderById(id);
		if (order == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		model.addAttribute("order", order);
		return "order/summary";
	}
	
	@PostMapping
	public String orderAll() {
		orderService.orderAll();
		return "redirect:/order";
	}
	
	@PostMapping("/{slno}")
	public String orderCartItem(@PathVariable int slno) {
		int orderId = orderService.orderCartItem(slno);
		if (orderId == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return "redirect:/order/" + orderId;
	}
	
	@PostMapping("/again/{id}")
	public String orderAgain(@PathVariable("id") int orderId) {
		int newOrderId = orderService.orderAgain(orderId);
		if (newOrderId == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return "redirect:/order/" + newOrderId;
	}
	
	@PostMapping("/cancel/{id}")
	public String cancelOrder(@PathVariable("id") int orderId) {
		orderService.cancelOrder(orderId);
		return "redirect:/order";
	}
	
	@GetMapping("/status/{status}")
	public String filterByStatusForCustId(@PathVariable String status, Model model) {
		List<String> categoryList = productService.getAllCategories();
		model.addAttribute("categoryList", categoryList);
		Customer customer = customerService.getCustomer();
        model.addAttribute("customer", customer);
        if (customer == null) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (status == null) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
		List<Order> orderList = orderService.filterByStatusForCustId(status);
		model.addAttribute("orderList", orderList);
		int orderCountByStatusArr[] = {
			orderService.countOrdersByStatusForCustId(OrderStatus.Pending),
			orderService.countOrdersByStatusForCustId(OrderStatus.Shipped),
			orderService.countOrdersByStatusForCustId(OrderStatus.Delivered),
			orderService.countOrdersByStatusForCustId(OrderStatus.Cancelled)
		};
		model.addAttribute("orderCountByStatusArr", orderCountByStatusArr);
		return "order/history";
	}
	
}
