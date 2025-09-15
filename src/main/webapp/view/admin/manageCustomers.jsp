<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="java.util.List" %>
<%@ page import="com.springspartans.shopkart.model.Customer" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ShopKart | Manage Customers</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorScheme.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/manageCustomers.css"/>
</head>
<body>
	<%@ include file="../../templates/admin_sidebar.jsp" %>
    <div style="margin-left : 300px; margin-right : 100px;">
    	<%@ include file="../../templates/admin_header.jsp" %>
        <h2 style="font-size : 40px;">Manage Customers</h2>
    <% List<Customer> customerList = (List<Customer>)request.getAttribute("customerList"); %>
    <table>
        <thead>
            <tr>
                <th>Customer-ID</th>
                <th>Customer-name</th>
                <th>Customer-email</th>                
                <th>Address</th>
                <th>Phone Number</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        	<% if (customerList != null) { %>
        		<% for (Customer cust : customerList) { %>
		            <tr>
		                <td><h4>CUST<%= String.format("%04d", cust.getId()) %></h4></td>
		                <td>
			                <div class="name">
			                	<% String profilePic = cust.getProfilePic(); %>
			                	<% if (profilePic != null) { %>
			                		<img src="${pageContext.request.contextPath}/uploads/customer/<%= profilePic %>" alt="<%= profilePic %>">
			                	<% } else { %>
			                		<img src="${pageContext.request.contextPath}/images/avatar.jpg" alt="avatar" height=50>
			                	<% } %>
			                    <h4><%= cust.getName() %></h4>
			                </div>
		                </td>
		                <td><h4><%= cust.getEmail() %></h4></td>
		                <td><h4><%= cust.getAddress() %></h4></td>
		                <td>
		                    <h4><%= cust.getPhone() %></h4>
		                </td>               
		                <td>
		                    <form action="/admin/dashboard/customer/delete/<%= cust.getId() %>" method="post">
		                    	<div class="buttons">
			                        <button class="delete" type="submit">Remove</button>
			                    </div>
		                    </form>
		                </td>
		            </tr>
				<% } %>
        	<% } %>
        </tbody>
    </table>
    </div>
    
</body>
</html>

