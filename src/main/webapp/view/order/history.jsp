<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.springspartans.shopkart.model.Order"%>
<%@ page import="com.springspartans.shopkart.model.Order.OrderStatus"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>ShopKart | Your orders</title>
	<link rel="stylesheet" href="../../css/history.css"/>
	<link rel="stylesheet" href="../../css/colorScheme.css" />
	<link rel="stylesheet" href="../../css/colorSchemeForOrderStatus.css" />
	<link rel="stylesheet" href="../../css/siteMapBanner.css">
</head>
<body>
<%@ include file="../../templates/header.jsp" %>
	<div style="margin-top: 100px">
		<div class="SiteMapBanner">
	        <img src="../../images/siteMapBanner.jpg">
	        <p>Home > Product Catalogue > Your Orders</p>
	    </div>
		<div class="container-filter-shippingCategory">
		
			<% List<Order> orderList = (List<Order>)request.getAttribute("orderList"); %>
			<% int[] orderCountByStatusArr = (int[])request.getAttribute("orderCountByStatusArr"); %>
			<% String orderStatus = (String)request.getAttribute("status"); %>
			
	        <div class="dropdown-container">
	            <label style="color : var(--base-text);"for="shippingCategory">Select Order Status:</label>
	            <select name="shippingCategory" id="shippingCategory" onchange="window.location.href='/order/status/' + this.value">
	                <option value=""></option>
	                <option value="All">All</option>
	                <option value="Pending">Pending</option>
	                <option value="Shipped">Shipped</option>
	                <option value="Delivered">Delivered</option>
	                <option value="Cancelled">Cancelled</option>
	            </select>
	        </div>
	        
	        <% if (orderCountByStatusArr != null) { %>
	        	<% if (orderStatus == null || orderStatus.equals("All")) { %>
	        		<div class="categry pending">
			            <h2 id="pending-text">Pending</h2>
			            <h1><%= orderCountByStatusArr[0] %></h1>
			        </div>
			        <div class="categry shipped">
			            <h2>Shipped</h2>
			            <h1><%= orderCountByStatusArr[1] %></h1>
			        </div>
			        <div class="categry delivered">
			            <h2>Delivered</h2>
			            <h1><%= orderCountByStatusArr[2] %></h1>
			        </div>
			        <div class="categry cancelled">
			            <h2>Cancelled</h2>
			            <h1><%= orderCountByStatusArr[3] %></h1>
			        </div>
	        	<% } else if (orderStatus.equals("Pending")) { %>
	        		<div class="categry pending">
			            <h2 id="pending-text">Pending</h2>
			            <h1><%= orderCountByStatusArr[0] %></h1>
			        </div>
	        	<% } else if (orderStatus.equals("Shipped")) { %>
	        		<div class="categry shipped">
			            <h2>Shipped</h2>
			            <h1><%= orderCountByStatusArr[1] %></h1>
			        </div>
	        	<% } else if (orderStatus.equals("Delivered")) { %>
	        		<div class="categry delivered">
			            <h2>Delivered</h2>
			            <h1><%= orderCountByStatusArr[2] %></h1>
			        </div>
	        	<% } else if (orderStatus.equals("Cancelled")) { %>
	        		<div class="categry cancelled">
			            <h2>Cancelled</h2>
			            <h1><%= orderCountByStatusArr[3] %></h1>
			        </div>
	        	<% } %>
	        <% } %>
	        
	    </div>
	
	    <h2 style="font-size : 40px; margin-bottom : 40px;">Your Order History</h2>
	
	    <% if (orderList == null) { %>
	    	<h2 id="empty-msg">You haven't placed any orders yet!</h2>
	    	<h2 id="empty-msg">Try exploring our product catalogue and then order from your cart</h2>
	    <% } else { %>
	    	<table>
	        <thead>
	            <tr>
	                <th>Order-ID</th>
	                <th>Product-details</th>
	                <th>Paid-amount</th>
	                <th>Order-Date</th>
	                <th>Delivery-Date</th>
	                <th>Status</th>
	                <th>Actions</th>
	            </tr>
	        </thead>
	        <tbody>
	        	<% for (Order order : orderList) { %>
	        		<tr>
		            	<td><h4>ORD<%= String.format("%04d", order.getId()) %></h4></td>
		                <td>
		                    <div class="details-product">
		                        <a href="/product/<%= order.getProduct().getId() %>">
		                        	<img src="${pageContext.request.contextPath}/uploads/product/<%= order.getProduct().getImage() %>" 
		                        	alt="<%= order.getProduct().getImage() %>">		                      
		                        </a>
		                        <h4><%= order.getProduct().getName() %></h4>
		                    </div>
		                </td>
		                <td><h4>₹ <%= String.format("%.2f", order.getTotal_amount()) %></h4></td>
		                <td>
		                	<%
								Timestamp orderDate = order.getOrder_date();
								String formattedOrderDate = "";
								if (orderDate != null) {
									Date date = new Date(orderDate.getTime());
									SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
									formattedOrderDate = formatter.format(date);
								} else {
									formattedOrderDate = "NA";
								}
							%>
		                	<h4><%= formattedOrderDate %></h4>
		                </td>
		                <td>
		                	<%
								Timestamp deliveredDate = order.getDelivered_date();
								String formattedDeliveredDate = "";
								if (deliveredDate != null) {
									Date date = new Date(deliveredDate.getTime());
									SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
									formattedDeliveredDate = formatter.format(date);
								} else {
									formattedDeliveredDate = "Not delivered";
								}
							%>
		                	<h4><%= formattedDeliveredDate %></h4>
		                </td>
		                <td>
		                	<% OrderStatus status = order.getStatus(); %> 
		                	<% if (status.equals(OrderStatus.Pending)) { %>  
		                		<div class="status-pending">
				                        <h3>Pending</h3>
				                    </div> 
		                	<% } else if (status.equals(OrderStatus.Shipped)) { %> 
		                		<div class="status-shipped">
				                        <h3>Shipped</h3>
				                    </div>
		                	<% } else if (status.equals(OrderStatus.Delivered)) { %> 
		                		<div class="status-delivered">
				                        <h3>Delivered</h3>
				                    </div>
		                	<% } else if (status.equals(OrderStatus.Cancelled)) { %> 
		                		<div class="status-cancelled">
				                        <h3>Cancelled</h3>
				                    </div>
		                	<% } %>         	                 	               	
		                </td>
		                <td>
		                    <div class="buttons">
		                    	<button class="view-button" onclick="window.location.href='/order/<%= order.getId() %>'">View order</button>
		                        <form action="/order/cancel/<%= order.getId() %>" method="post">		                        	
		                        	<button class="cancel-button" type="submit">Cancel order</button>
		                        </form>		                        
		                    </div>
		                </td>
		            </tr>
		        <% } %>
	        </tbody>
	    </table>
	    <% } %>
	    
	    <%@ include file="../../templates/footer.jsp" %>
	</div>
</body>
</html>