<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.springspartans.shopkart.model.Product" %>    
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ShopKart | Product Details</title>
    <link rel="stylesheet" href="../../css/details.css" />
    <link rel="stylesheet" href="../../css/colorScheme.css" />
    <link rel="stylesheet" href="../../css/colorSchemeForOrderStatus.css" />   
    <link rel="stylesheet" href="../../css/siteMapBanner.css">
</head>
<body>
    
    <%@ include file="../../templates/header.jsp" %>

    <div style="margin-top: 100px">	  
    	<div class="SiteMapBanner">
	        <img src="../../images/siteMapBanner.jpg">
	        <p>Home > Product Catalogue > Product Details</p>
	    </div>    
	      	<% Product product = (Product)request.getAttribute("product"); %>
	        <% if (product == null) { %>
	        	<h2 class="error"> Sorry, we're unable to fetch the product you're looking for! </h2>
	        <% } else { %>
	        	<h2 class="heading">Product Details</h2>
			    <div class="product-container">
			      <div class="container-product-image">
			        <div class="image-container">
			          <img src="${pageContext.request.contextPath}/uploads/product/<%= product.getImage() %>" />
			        </div>
			    </div>
	        	<div class="container-product-details">
		        	<h2>Product Info :</h2>
			        <br>
			        <div class="name">
			          <div><b>Name : </b></div>
			          <div><%= product.getName() %></div>
			        </div>
			        <div class="category">
			          <div><b>Category : </b></div>
			          <div><%= product.getCategory() %></div>
			        </div>
			        <div class="price">
			          <div><b>Price : </b></div>
			          <div>₹ <%= String.format("%.2f", product.getPrice()) %></div>
			        </div>
			        <div class="brand-name">
			          <div><b>Brand : </b></div>
			          <div><%= product.getBrand() %></div>
			        </div>
			        <div class="discount">
			          <div><b>Discount : </b></div>
			          <div><%= String.format("%.2f", product.getDiscount()) %>%</div>
			        </div>
			        <div class="availability">
			          <div><b>Availability : </b></div>
			          <% int stock = product.getStock(); %>
			          <% if (stock == 0) { %>
			          	<div class="left0">Out Of Stock</div>
			          <% } else if (stock <= 5) {  %>
				       	<div class="left5">Only 5 left</div>
				      <% } else { %>
				      	<div class="In-stock">In Stock</div>
				      <% } %>				          
			        </div>
		        </div>
	        <% } %>
	    </div>
	
	    <div class="buttons">
	      <button id="continue-button" onclick="window.location.href='/product'">Continue Shopping</button>
	      <% if (product != null && customer != null && product.getStock() > 0) { %>
	      		<form action="/cartitem/add/<%= product.getId()%>" method="post">
	      			<button id="cart-button">Add to Cart</button>
	      		</form>
	      <% } %>
	    </div>
    </div>
    
    <%@ include file="../../templates/footer.jsp" %>
</body>
</html>