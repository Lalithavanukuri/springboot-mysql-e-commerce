<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Sidebar Template</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorScheme.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_sidebar.css" />

</head>
<body>
    <div class="sidebar">
    	<div class="logo">ShopKart</div>
    	<div class="breaker"></div>
        <ul>
            <li><a href="/admin/dashboard/siteWidgets">Site-Widgets</a></li>
            <li><a href="/admin/dashboard">Analytics</a></li>
            <li><a href="/admin/dashboard/product/add">Add New Product</a></li>
            <li><a href="/admin/dashboard/product">Manage Product Catalogue</a></li>
            <li><a href="/admin/dashboard/customer">Manage Customers</a></li>
            <li><a href="/admin/dashboard/order">Manage Orders</a></li>
            <li><a href="/admin/logout">Log-Out</a></li>
        </ul>
    </div>
</body>
</html>