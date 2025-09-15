<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.springspartans.shopkart.model.Customer" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Header Template</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorScheme.css" />
  </head>
  <body>
    <nav>
      <div class="navbar">
        <div class="logo">
        	<a href="/product">ShopKart</a>
        </div>
        
        <% Customer customer = (Customer)request.getAttribute("customer"); %>
        <% List<String> categoryList = (List<String>)request.getAttribute("categoryList"); %>
        
        <form action="/product/search" method="get">
          <div class="search-bar">
            <input name="prefix" type="text" style="color: black;" placeholder="Search products...">
            <button type="submit">Search</button>
          </div>
        </form>

        <div class="category">
	        <% if (categoryList != null) { %>
	        	<label for="category" style="color : var(--base-text);">Category:</label>
		          <select name="category" id="category" onchange="window.location.href='/product/category/' + this.value">
		          	<option value="None"></option>
		            <% for (String category : categoryList) { %>
		                <option value="<%= category %>"><%= category %></option>
		            <% } %>
		          </select>
	        <% } %>
        </div>

        <div class="sections">
          <button id="aboutUsBtn">
            About us <img src="${pageContext.request.contextPath}/images/group.png" alt="" />
          </button>
          
          <button class="orders" onclick="window.location.href='/order'">
            Your Orders <img src="${pageContext.request.contextPath}/images/orders.png" alt="" />
          </button>
          
          <button class="cart" onclick="window.location.href='/cartitem/cart'">
            Your cart <img src="${pageContext.request.contextPath}/images/cart.svg" alt="" />
          </button>
          
          <% if (customer != null) { %>
	    	<button class="profile" id="profile-btn">
	            Profile <img src="${pageContext.request.contextPath}/images/profile.svg" />
	        </button>
	        
	    	<div class="profile-form" id="profile-form">
		      <!-- Add the back button at the top-left corner -->
		      <button id="back-btn" class="back-button">&larr; Back</button>
		
		      <h2>Hey <span style="color : var(--high-text-button);"><%= customer.getName()%></span> ! See your Profile</h2>
		      <div class="avatar">
		      	<% String profilePicture = customer.getProfilePic(); %>
		      	<% if (profilePicture != null) { %>
		      		<img src="${pageContext.request.contextPath}/uploads/customer/<%= profilePicture %>" alt="" />
		      	<% } else { %>
		      		<img src="${pageContext.request.contextPath}/images/avatar.jpg" alt="" />
		      	<% } %>
		      </div>	  
			  	  <div><b>Name :</b> <%= customer.getName() %></div>
			      <div><b>Email-id :</b> <%= customer.getEmail() %></div>
			      <div><b>Address :</b> <%= customer.getAddress() %></div>
			      <div><b>Phone number :</b> <%= customer.getPhone() %></div>
			      <button id="update-btn" onclick="window.location.href='/update'">Update</button>
			      <button id="log-out-btn" onclick="window.location.href='/logout'">Log out</button>
			</div>
	      <% } else {%>
	      	<button id="log-in" onclick="window.location.href='/'">
	            Login <img src="${pageContext.request.contextPath}/images/profile.svg" />
	        </button>
	      <% } %>
          
        </div>
        
      </div>
    </nav> 
    
    <script src="${pageContext.request.contextPath}/js/home.js"></script>
    <script src="${pageContext.request.contextPath}/js/about.js"></script>
  </body>
</html>