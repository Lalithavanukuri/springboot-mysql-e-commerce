package com.springspartans.shopkart.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springspartans.shopkart.model.CartItem;
import com.springspartans.shopkart.model.Order;
import com.springspartans.shopkart.model.Order.OrderStatus;
import com.springspartans.shopkart.model.Product;
import com.springspartans.shopkart.repository.OrderRepository;
import com.springspartans.shopkart.repository.ProductRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartItemService cartItemService;

	public List<Order> getOrdersOfLoggedInCustomer() {
		int custId = customerService.getCustomer().getId();
		List<Order> orderList = orderRepository.findByCustIdReverse(custId);
		return orderList;
	} 
	
	public List<Order> getOrdersByCustId(int custId) {
		List<Order> orderList = orderRepository.findByCustIdReverse(custId);
		return orderList;
	} 

	public List<Order> getAllOrders() {
		List<Order> orderList = orderRepository.findAllReverse();
		return orderList;
	}
	
	public Order getOrderById(int id) {
		return orderRepository.findById(id).orElse(null);
	}

	public void orderAll() {
		int custId = customerService.getCustomer().getId();	
		List<CartItem> cart = cartItemService.getAllCartItemsforCustomer(custId);
		cartItemService.clearCart();
		for (CartItem cartItem : cart) {
			Order order = new Order();
			order.setCustomer(cartItem.getCustomer());
			order.setProduct(cartItem.getProduct());
			order.setQuantity(cartItem.getQuantity());
			order.setTotal_amount(cartItem.getProduct().getPrice() 
					* (100.0 - cartItem.getProduct().getDiscount())/100.0
					* cartItem.getQuantity());
			Product product = order.getProduct();
			if (order.getQuantity() <= product.getStock()) {
				product.setStock(product.getStock() - order.getQuantity());
				orderRepository.save(order);
				productRepository.save(product);				
			}			
		}
	}

	public int orderCartItem(int slno) {
		CartItem cartItem = cartItemService.getBySlno(slno);
		cartItemService.deleteCartItem(slno);
		Order order = new Order();
		order.setCustomer(cartItem.getCustomer());
		order.setProduct(cartItem.getProduct());
		order.setQuantity(cartItem.getQuantity());
		order.setTotal_amount(cartItem.getProduct().getPrice() 
				* (100.0 - cartItem.getProduct().getDiscount())/100.0
				* cartItem.getQuantity());
		Product product = order.getProduct();
		if (order.getQuantity() <= product.getStock()) {
			product.setStock(product.getStock() - order.getQuantity());
			order = orderRepository.save(order);
			productRepository.save(product);			
			return order.getId();
		}	
		return 0;
	}

	public int orderAgain(int orderId) {
		Order order = orderRepository.findById(orderId).orElse(null);
		if (order == null) {
			return 0;
		}
		Order newOrder = new Order();
		newOrder.setCustomer(order.getCustomer());
		newOrder.setProduct(order.getProduct());
		newOrder.setQuantity(1);
		newOrder.setTotal_amount(order.getProduct().getPrice()
				* (100.0 - order.getProduct().getDiscount())/100.0);
		newOrder = orderRepository.save(newOrder);	
		Product product = order.getProduct();
		if (newOrder.getQuantity() <= product.getStock()) {
			product.setStock(product.getStock() - newOrder.getQuantity());
			newOrder = orderRepository.save(newOrder);
			productRepository.save(product);
			return newOrder.getId();
		}	
		return 0;
	}

	public void cancelOrder(int orderId) {
		Order order = orderRepository.findById(orderId).orElse(null);
		if (order == null || order.getStatus() == OrderStatus.Delivered) {
			return;
		}
		order.setStatus(OrderStatus.Cancelled);
		Product product = order.getProduct();
		product.setStock(product.getStock() + order.getQuantity());
		productRepository.save(product);
		orderRepository.save(order);
	}
	
	public void updateStatus(int orderId) {
		Order order = orderRepository.findById(orderId).orElse(null);
		if (order == null) {
			return;
		}
		if (order.getStatus() == OrderStatus.Pending) {
			order.setStatus(OrderStatus.Shipped);
		}
		else if (order.getStatus() == OrderStatus.Shipped) {
			order.setStatus(OrderStatus.Delivered);
			order.setDelivered_date(new Timestamp(System.currentTimeMillis()));
		}
		orderRepository.save(order);
	}

	public List<Order> filterByStatusForCustId(String status) {	
		int custId = customerService.getCustomer().getId();
		if (status.equals("All")) {
			return orderRepository.findByCustIdReverse(custId);
		}
		return orderRepository.findByStatusCustIdReverse(status, custId);
	}
	
	public List<Order> filterByStatus(String status) {	
		if (status.equals("All")) {
			return orderRepository.findAllReverse();
		}
		return orderRepository.findByStatusReverse(status);
	}
	
	public int countOrdersByStatusForCustId(OrderStatus status) {
		int custId = customerService.getCustomer().getId();
		return orderRepository.countByStatusCustId(status.toString(), custId);
	}
	
	public int countOrdersByStatusAndCustId(OrderStatus status, int custId) {
		return orderRepository.countByStatusCustId(status.toString(), custId);
	}
	
	public int countOrdersByStatus(OrderStatus status) {
		return orderRepository.countByStatus(status.toString());
	}
	
	public double getSalesForDate(Timestamp date) {
		try {
            LocalDate localDate = date.toLocalDateTime().toLocalDate();
            Timestamp startTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
            Timestamp endTimestamp = Timestamp.valueOf(localDate.atTime(23, 59, 59));
            return orderRepository.getSalesForDuration(startTimestamp, endTimestamp);
        } catch (DateTimeParseException e) {
            return 0;
        }
	}
	
	public int countCustomersWhoPlacedOrderOnDate(Timestamp orderDate) {
		return orderRepository.countCustomersWhoPlacedOrderOnDate(orderDate);
	}
	
	public List<Order> searchByCustId(int custId) {
		return orderRepository.findByCustIdReverse(custId);
	}
	
	public List<Integer[]> getTopSellingProducts(int n) {
		return orderRepository.getTopSellingProducts(n);
	}
	
	public double totalSalesLastWeek() {
	    LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
	    Timestamp end = Timestamp.valueOf(endDateTime);
	    LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now().minusDays(6), LocalTime.MIN);
	    Timestamp start = Timestamp.valueOf(startDateTime);
		return orderRepository.getSalesForDuration(start, end);
	}
	
}
