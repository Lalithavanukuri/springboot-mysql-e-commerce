<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import="com.springspartans.shopkart.model.Admin" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Header Template</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorScheme.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorSchemeForOrderStatus.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_header.css" />

</head>
<body>
	<% Admin admin = (Admin)request.getAttribute("admin"); %>
	<% admin = (Admin)session.getAttribute("loggedInAdmin"); %>
	<% if (admin != null) { %>
	<div class="admin_header">
	<div class="header-content">
		<div class="details">
			<p><%= admin.getUsername()%></p>
   			<p><%= admin.getEmail()%></p>
		</div>
		<div>
			<img style="height: 40px; width: 40px; border-radius: 50%; object-fit: cover;" src="${pageContext.request.contextPath}/images/avatar.jpg">
		</div>
	</div>
   	</div>
     <% } %>
   	<div class="breaker"></div>
</body>
</html>