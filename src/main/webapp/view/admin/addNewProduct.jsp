<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.springspartans.shopkart.model.Product" %> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ShopKart | Add New Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorScheme.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addNewProduct.css" />
</head>
<body>
	<%@ include file="../../templates/admin_sidebar.jsp" %>
    <div style="margin-left : 300px; margin-right : 100px;">
    	<%@ include file="../../templates/admin_header.jsp" %>
        <div class="form-container">
        	<% Product product = (Product)request.getAttribute("product"); %>
        	<% String msg = request.getParameter("msg"); %>
        	<% if (msg != null && msg.equals("failed")) { %>
        		<h3 id="error-msg">The uploaded file exceeds the mentioned specifications!</h3>
        	<% } %>
        	
        	<% String action = "/admin/dashboard/product"; %>
        	<% if (product != null) { %>
        		<% action += "/update/" + product.getId(); %>
        		<h2>Update Product</h2>
        	<% } else { %>
        		<% action += "/add"; %>
        		<h2>Add New Product</h2>
        	<% } %>
        	
            <form action="<%= action %>" method="post" enctype="multipart/form-data">
                <div class="form-group">
                	<% String nameVal = (product != null) ? product.getName() : ""; %>
                    <label for="name">Product Name</label>
                    <input type="text" id="name" name="name" value="<%= nameVal %>" required>
                </div>
                <div class="form-group">
                	<% String categoryVal = (product != null) ? product.getCategory() : ""; %>
                    <label for="category">Category</label>
                    <input type="text" id="category" name="category" value="<%= categoryVal %>" required>
                </div>
                <div class="form-group">
                	<% String brandVal = (product != null) ? product.getBrand() : ""; %>
                    <label for="brand">Brand</label>
                    <input type="text" id="brand" name="brand"  value="<%= brandVal %>" required>
                </div>
                <div class="form-group">
                	<% double priceVal = (product != null) ? product.getPrice() : 0.00; %>
                    <label for="price">Price (₹)</label>
                    <input type="number" step="0.01" id="price" name="price" value="<%= priceVal %>" required>
                </div>
                <div class="form-group">
                	<% int stockVal = (product != null) ? product.getStock() : 0; %>
                    <label for="stock">Stock</label>
                    <input type="number" id="stock" name="stock" value="<%= stockVal %>" required>
                </div>
                <div class="form-group">
                	<% double discountVal = (product != null) ? product.getDiscount() : 0.00; %>
                    <label for="discount">Discount (%)</label>
                    <input type="number" step="0.01" id="discount" name="discount" value="<%= discountVal %>">
                </div>
                <div class="form-group">                	
                    <label for="image">Product Image</label>
                    <% if (product != null && product.getImage() != null) { %>
				        <img src="${pageContext.request.contextPath}/uploads/product/<%= product.getImage() %>" alt="<%= product.getImage() %>" width="100" height="100">
				        <input type="file" id="image" name="image" value="<%= product.getImage() %>" accept="image/*">
				    <% } else { %>
				    	<input type="file" id="image" name="image" accept="image/*" required>
				    <% } %>                    
                    <small>Accepted formats: JPG,JPEG,PNG; Max.size=200KB; Max.resolution=500px</small>
                </div>
                <div class="button">
                	<% if (product != null) { %>
		        		<button type="submit">Update Product</button>
		        	<% } else { %>
		        		<button type="submit">Add New Product</button>
		        	<% } %>                    
                </div>
            </form>
        </div>
    </div>
</body>
</html>


