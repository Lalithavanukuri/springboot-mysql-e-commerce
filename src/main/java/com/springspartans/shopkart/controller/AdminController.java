package com.springspartans.shopkart.controller;

import com.springspartans.shopkart.exception.InvalidImageUploadException;
import com.springspartans.shopkart.model.Admin;
import com.springspartans.shopkart.model.Customer;
import com.springspartans.shopkart.model.Order;
import com.springspartans.shopkart.model.Product;
import com.springspartans.shopkart.model.Order.OrderStatus;
import com.springspartans.shopkart.service.AdminService;
import com.springspartans.shopkart.service.CustomerService;
import com.springspartans.shopkart.service.OrderService;
import com.springspartans.shopkart.service.ProductService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    /*
     * Methods to maintain Session management for an Administrator
     */
    @GetMapping
    public String login() {
        return "admin/admin_login";
    }

    @GetMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password , @RequestParam String security_key) {
        if (email == null || password == null || security_key == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        boolean success = adminService.login(email, password , security_key);
        if (success) {
            return "redirect:/admin/dashboard";
        } else {
        	return "redirect:/admin?msg=failed";
        }
    }
    @GetMapping("/logout")
    public String logout() {
        adminService.logout();
        return "redirect:/admin?msg=logout";
    }
    
    /*
     * Methods to render dash-board components
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
    	Admin admin = adminService.getAdmin();
		Customer customer = customerService.getCustomer();
    	if(admin == null &&  customer == null) {
    		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    	}
    	else if (admin == null && customer != null) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    	}
    	
    	// Counts & pie chart
    	int orderCountByStatusArr[] = {
        	orderService.countOrdersByStatus(OrderStatus.Pending),
        	orderService.countOrdersByStatus(OrderStatus.Shipped),
        	orderService.countOrdersByStatus(OrderStatus.Delivered),
        	orderService.countOrdersByStatus(OrderStatus.Cancelled)
        };
    	model.addAttribute("orderCountByStatusArr", orderCountByStatusArr);
    	double totalSales = orderService.totalSalesLastWeek();
    	model.addAttribute("totalSales", totalSales);
    	int prodCount = productService.countProducts();
    	model.addAttribute("prodCount", prodCount);
    	int custCount = customerService.countCustomers();  
    	model.addAttribute("custCount", custCount);
    	
    	// Top selling products
    	List<Integer[]> topSellersId = orderService.getTopSellingProducts(6);
    	List<Object[]> topSellers = new ArrayList<>();
    	for (Integer[] obj : topSellersId) {
    		topSellers.add(new Object[] {productService.getProductById(obj[0]), obj[1]});
    	}
    	model.addAttribute("topSellers", topSellers);
    	
    	// Day by day sales
    	List<Double> lastWeekSales = new ArrayList<>();
    	List<Timestamp> lastWeek = new ArrayList<>();
    	LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 7; i++) {
            LocalDateTime date = now.minusDays(i);
            Timestamp timestamp = Timestamp.valueOf(date.toLocalDate().atStartOfDay());
            lastWeek.add(timestamp);
            lastWeekSales.add(orderService.getSalesForDate(timestamp));
        }
        model.addAttribute("lastWeek", lastWeek);
        model.addAttribute("lastWeekSales", lastWeekSales);
    	
    	// Customer activity
    	int[] custActivity = {    		
    		customerService.countSignupByDate(new Timestamp(System.currentTimeMillis())),
    		customerService.countLoginByDate(new Timestamp(System.currentTimeMillis())),
    		orderService.countCustomersWhoPlacedOrderOnDate(new Timestamp(System.currentTimeMillis()))
    	};
    	model.addAttribute("custActivity", custActivity);
    	
        return "admin/dashboard";
    }

    @GetMapping("/dashboard/siteWidgets")
    public String getSiteWidgets() {
    	Admin admin = adminService.getAdmin();
		Customer customer = customerService.getCustomer();
    	if(admin == null &&  customer == null) {
    		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    	}
    	else if (admin == null && customer != null) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    	}
        return "admin/siteWidgets";
    }

    @GetMapping("/dashboard/product/add")
    public String addNewProduct() {
    	Admin admin = adminService.getAdmin();
		Customer customer = customerService.getCustomer();
    	if(admin == null &&  customer == null) {
    		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    	}
    	else if (admin == null && customer != null) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    	}
        return "admin/addNewProduct";
    }

    @GetMapping("/dashboard/product")
    public String manageProductCatalogue(Model model) {
    	Admin admin = adminService.getAdmin();
		Customer customer = customerService.getCustomer();		
    	if (admin == null &&  customer == null) {
    		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    	}
    	else if (admin == null && customer != null) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    	}
    	List<Product> productList = productService.getAllProducts();
		model.addAttribute("productList", productList);
        return "admin/manageProductCatalogue";
    }

    @GetMapping("/dashboard/customer")
    public String manageCustomers(Model model) {
    	Admin admin = adminService.getAdmin();
		Customer customer = customerService.getCustomer();
    	if(admin == null &&  customer == null) {
    		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    	}
    	else if (admin == null && customer != null) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    	}
    	List<Customer> customerList = customerService.getAllCustomers();
    	model.addAttribute("customerList", customerList);
        return "admin/manageCustomers";
    }

    @GetMapping("/dashboard/order")
    public String manageOrder(Model model) {
    	Admin admin = adminService.getAdmin();
		Customer customer = customerService.getCustomer();
    	if(admin == null &&  customer == null) {
    		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    	}
    	else if (admin == null && customer != null) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    	}
    	List<Order> orderList = orderService.getAllOrders();
    	model.addAttribute("orderList", orderList);
    	int orderCountByStatusArr[] = {
    		orderService.countOrdersByStatus(OrderStatus.Pending),
    		orderService.countOrdersByStatus(OrderStatus.Shipped),
    		orderService.countOrdersByStatus(OrderStatus.Delivered),
    		orderService.countOrdersByStatus(OrderStatus.Cancelled)
    	};
    	model.addAttribute("orderCountByStatusArr", orderCountByStatusArr);
        return "admin/manageOrders";
    }
    
    /*
     * Methods to add functionalities to the dash-board
     */
    @PostMapping("/dashboard/product/add")
    public String addNewProductAction(
    		@RequestParam String name, @RequestParam String category, @RequestParam String brand, @RequestParam double price,
    		@RequestParam int stock, @RequestParam double discount, @RequestParam MultipartFile image
    	) {
    	try {
			productService.addProduct(0, name, category, brand, price, image, stock, discount);
		} catch (IOException e) {			
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InvalidImageUploadException e) {			
			e.printStackTrace();
			return "redirect:/admin/dashboard/product/add?msg=failed";
		}
    	return "redirect:/admin/dashboard/product";
    }
    
    @PostMapping("/dashboard/product/delete/{id}")
    public String manageProductCatalogueDeleteAction(@PathVariable int id) {
    	productService.deleteProduct(id);
    	return "redirect:/admin/dashboard/product";
    }
    
    @GetMapping("/dashboard/product/update/{id}")
    public String manageProductCatalogueUpdate(@PathVariable int id, Model model) {
    	Admin admin = adminService.getAdmin();
		Customer customer = customerService.getCustomer();
    	if(admin == null &&  customer == null) {
    		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    	}
    	else if (admin == null && customer != null) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    	}
    	Product product = productService.getProductById(id);
    	model.addAttribute("product", product);
        return "admin/addNewProduct";
    }
    
    @PostMapping("/dashboard/product/update/{id}")
    public String manageProductCatalogueUpdateAction(
    		@PathVariable int id, @RequestParam String name, @RequestParam String category, @RequestParam String brand, 
    		@RequestParam double price, @RequestParam int stock, @RequestParam double discount, @RequestParam MultipartFile image
    	) {
    	try {
			productService.updateProduct(id, name, category, brand, price, image, stock, discount);
		} catch (IOException e) {			
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InvalidImageUploadException e) {			
			e.printStackTrace();
			return "redirect:/admin/dashboard/product/update/" + id + "?msg=failed";
		}
    	return "redirect:/admin/dashboard/product";
    }
    
    @PostMapping("/dashboard/customer/delete/{id}")
    public String manageCustomersDeleteAction(@PathVariable int id) {
    	customerService.deleteCustomer(id);
        return "redirect:/admin/dashboard/customer";
    }
    
    @GetMapping("/dashboard/order/status/{status}")
    public String manageOrderFilterByStatusAction(@PathVariable String status, Model model) {
    	Admin admin = adminService.getAdmin();
		Customer customer = customerService.getCustomer();
    	if(admin == null &&  customer == null) {
    		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    	}
    	else if (admin == null && customer != null) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    	}
    	model.addAttribute("status", status);
    	List<Order> orderList = orderService.filterByStatus(status);
    	model.addAttribute("orderList", orderList);
    	int orderCountByStatusArr[] = {
    		orderService.countOrdersByStatus(OrderStatus.Pending),
    		orderService.countOrdersByStatus(OrderStatus.Shipped),
    		orderService.countOrdersByStatus(OrderStatus.Delivered),
    		orderService.countOrdersByStatus(OrderStatus.Cancelled)
    	};
    	model.addAttribute("orderCountByStatusArr", orderCountByStatusArr);
        return "admin/manageOrders";
    }
    
    @GetMapping("/dashboard/order/search")
    public String manageOrderSearchAction(@RequestParam int custId, Model model) {
    	Admin admin = adminService.getAdmin();
		Customer customer = customerService.getCustomer();
    	if(admin == null &&  customer == null) {
    		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    	}
    	else if (admin == null && customer != null) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    	}
    	List<Order> orderList = orderService.getOrdersByCustId(custId);
    	model.addAttribute("orderList", orderList);
    	int orderCountByStatusArr[] = {
    		orderService.countOrdersByStatusAndCustId(OrderStatus.Pending, custId),
    		orderService.countOrdersByStatusAndCustId(OrderStatus.Shipped, custId),
    		orderService.countOrdersByStatusAndCustId(OrderStatus.Delivered, custId),
    		orderService.countOrdersByStatusAndCustId(OrderStatus.Cancelled, custId)
    	};
    	model.addAttribute("orderCountByStatusArr", orderCountByStatusArr);
        return "admin/manageOrders";
    }
    
    @PostMapping("/dashboard/order/update/{id}")
    public String manageOrderUpdateAction(@PathVariable int id) {
    	orderService.updateStatus(id);
    	return "redirect:/admin/dashboard/order";
    }
    
    @PostMapping("/dashboard/order/cancel/{id}")
    public String manageOrderCancelAction(@PathVariable int id) {
    	orderService.cancelOrder(id);
    	return "redirect:/admin/dashboard/order";
    }
    
}
