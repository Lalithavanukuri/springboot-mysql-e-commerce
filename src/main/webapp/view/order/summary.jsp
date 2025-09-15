<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="com.springspartans.shopkart.model.Order"%>
<%@ page import="com.springspartans.shopkart.model.Order.OrderStatus"%>
<%@ page import="com.springspartans.shopkart.model.Product"%>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/summary.css">
    <link rel="stylesheet" href="../../css/colorSchemeForOrderStatus.css">
    <link rel="stylesheet" href="../../css/siteMapBanner.css">
    <title>ShopKart | Order Summary</title>
</head>
<body>
<%@ include file="../../templates/header.jsp" %>
<div style="margin-top : 100px;">
	<div class="SiteMapBanner">
        <img src="../../images/siteMapBanner.jpg">
        <p>Home > Product Catalogue > Order Summary</p>
    </div>
    <% Order order = (Order)request.getAttribute("order"); %>
    <% if (order != null) { %>
    	<div class="container">
	        <h1>Order Summary</h1>
				<%
				  Timestamp orderDate = order.getOrder_date();
				  
				  SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
				  String formattedOrderDate = dateFormat.format(orderDate);
				  
				  Calendar calendar = Calendar.getInstance();
				  calendar.setTime(orderDate);
				  calendar.add(Calendar.DAY_OF_MONTH, 5); // Add 5 days
				  Timestamp deliveryDate = new Timestamp(calendar.getTimeInMillis());
				  
				  String formattedDeliveryDate = dateFormat.format(deliveryDate);
				%>
				<% OrderStatus status = order.getStatus(); %>
				<% if (!status.equals(OrderStatus.Cancelled)) { %>
					<div class="thank-you-message">
					 <h3>Thank you for shopping with us!</h3>
					 <p>Order placed on: <strong id="order-date"><%= formattedOrderDate %></strong></p>
					 <% if (!status.equals(OrderStatus.Delivered)) { %>
					 	<p>Expected delivery by: <strong id="delivery-date"><%= formattedDeliveryDate %></strong></p>
					 <% } %>				 
					 
					 <% if (status.equals(OrderStatus.Pending)) { %>
					 	<div class="order-status pending">Order Status: Pending</div>
					 <% } else if (status.equals(OrderStatus.Shipped)) { %>
					 	<div class="order-status shipped">Order Status: Shipped</div>
					 <% } else if (status.equals(OrderStatus.Delivered)) { %>
					 	<div class="order-status delivered">Order Status: Delivered</div>
					 <% } else if (status.equals(OrderStatus.Cancelled)) { %>
					 	<div class="order-status cancelled">Order Status: Cancelled</div>
					 <% } %>
				</div>
				<% } %>
	        </div>
	
	        <!-- Shipping and Bill Summary -->
	        <div class="summary-container">
	            <div class="shipping-details">
	                <h2>Shipping Details</h2>
	                <% Customer orderCustomer = order.getCustomer(); %>
	                <p><strong>Name: </strong><%= orderCustomer.getName() %></p>
	                <p><strong>Email: </strong><%= orderCustomer.getEmail() %></p>
	                <p><strong>Address: </strong><%= orderCustomer.getAddress() %></p>
	            </div>
	        
	            <div class="bill-summary">
	                <h2>Bill Summary</h2>
	                <p><strong>Total Amount: </strong><%= String.format("%.2f", order.getProduct().getPrice() * order.getQuantity()) %></p>
	                <p><strong>Shipping Charge: </strong>0.00</p>
	                <p><strong>Discount Applied: </strong><%= String.format("%.2f", order.getProduct().getDiscount()) %></p>
	                <p><strong>Payable Amount: </strong><%= String.format("%.2f", order.getTotal_amount()) %></p>
	            </div>
	        </div>     
	
	        <!-- Product Summary -->
	        <div class="product-summary">
	            <h2>Product Summary</h2>
	            <% Product orderProduct = order.getProduct(); %>
	            <div class="product-item">
	                <div class="product-details">
	                    <p><strong>Product Name: </strong><%= orderProduct.getName() %></p>
	                    <p><strong>Category: </strong><%= orderProduct.getCategory() %></p>
	                    <p><strong>Price: </strong><%= String.format("%.2f", orderProduct.getPrice()) %></p>
	                    <p><strong>Brand: </strong><%= orderProduct.getBrand() %></p>
	                    <p><strong>Quantity: </strong><%= order.getQuantity() %></p>
	                </div>
	                <a href="/product/<%= orderProduct.getId() %>" class="product-image">
	                    <img src="${pageContext.request.contextPath}/uploads/product/<%= orderProduct.getImage() %>" alt="<%= orderProduct.getImage() %>" />
	                </a>
	            </div>
	        </div>
	
	        <!-- Buttons -->
	        <div class="button-container">	            
	            <form action="/order/again/<%= order.getId() %>" method="post">
	            	<button class="buy-again" type="submit">Buy Again</button>
	            </form>
	            <form action="/order/cancel/<%= order.getId() %>" method="post">
	            	<button class="cancel-order" type="submit">Cancel Order</button>
	            </form>
	        </div>
    <% } %>
    <%@ include file="../../templates/footer.jsp" %>
</div>

</body>
</html>