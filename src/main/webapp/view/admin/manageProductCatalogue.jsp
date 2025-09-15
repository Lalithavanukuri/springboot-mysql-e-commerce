<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.springspartans.shopkart.model.Product" %>        
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ShopKart | Manage Product Catalogue</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorScheme.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/manageProductCatalogue.css"/>
</head>
<body>
	<%@ include file="../../templates/admin_sidebar.jsp" %>
	
    <div style="margin-left : 300px; margin-right : 100px;">
    	<%@ include file="../../templates/admin_header.jsp" %>
        <h2 style="font-size : 40px;">Manage Product Catalogue</h2>
        
    <% List<Product> productList = (List<Product>)request.getAttribute("productList");  %>
    <table>
        <thead>
            <tr>
                <th>Product-ID</th>
                <th>Product-Name</th>
                <th>Product-Category</th>
                <th>Brand</th>
                <th>Price</th>
                <th>Stock</th>
                <th>Discount</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% if (productList != null) { %>
            	<% for (Product prod : productList) {  %>
		            <tr>
		                <td><h4>PROD<%= String.format("%04d", prod.getId()) %></h4></td>
		                <td>
		                    <div class="details-product">
		                        <img src="${pageContext.request.contextPath}/uploads/product/<%= prod.getImage() %>">
		                        <h4><%= prod.getName() %></h4>
		                    </div>
		                </td>
		                <td><h4><%= prod.getCategory() %></h4></td>
		                <td><h4><%= prod.getBrand() %></h4></td>
		                <td><h4>₹<%= String.format("%.2f", prod.getPrice()) %></h4></td>
		                <td>
		                    <h4><%= prod.getStock() %></h4>
		                </td>
		                <td>
		                    <h4><%= String.format("%.2f", prod.getDiscount()) %>%</h4>
		                </td>
		                <td>
		                    <div class="buttons">
		                    	<form action="/admin/dashboard/product/delete/<%= prod.getId() %>" method="post">
		                    		<button class="delete">Delete</button>
		                    	</form>
		                        <form action="/admin/dashboard/product/update/<%= prod.getId() %>" method="get">
		                        	<button class="update">Update</button>
		                        </form>		                        
		                    </div>
		                </td>
		            </tr>
	            <% } %>
            <% } %>
        </tbody>
    </table>
    </div>
    
</body>
</html>
    