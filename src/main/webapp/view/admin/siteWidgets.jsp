<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ShopKart | Site Widgets</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorScheme.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/siteWidgets.css" />
</head>
<body>
	<%@ include file="../../templates/admin_sidebar.jsp" %>
    <div style="margin-left : 300px; margin-right : 100px;">
    	<%@ include file="../../templates/admin_header.jsp" %>
        <div class="logo-details">
            <h3>Brand logo </h3>
            <div class="logo">ShopKart</div>
        </div>
        <div class="site-map-banner-details">
            <h3>Site-Map Banner </h3>
            <div class="siteMapBanner"><img src="../../images/siteMapBanner.jpg"></div>
        </div>
        <div class="advertisement-banner-details">
            <h3>Advertisement Banners </h3>
            <div class="advertisement-Banner">
                <img src="../../images/banners/banner1.jpg">
                <img src="../../images/banners/banner2.jpg">
                <img src="../../images/banners/banner3.jpg">
                <img src="../../images/banners/banner4.jpg">
            </div>
        </div>
    </div>
    
</body>
</html>

