<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>ShopKart | Error 401</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorScheme.css" />
</head>
<body>
	<div class="error-no">Error 401!</div>
	<div class="error-name">Unauthorized Page</div>
	<div class="error-msg">
		<p><b>Access Denied!</b></p>
		
		<p>It looks like you don’t have permission to view this page. Your request requires authentication, but the credentials you provided are either missing or invalid.</p>
		
		<p>If you have an account, please log in with your credentials. If you believe you should have access, please contact our support team for assistance. If you don’t have an account yet, consider signing up to gain access!</p>
	</div>
	<button class="log-in" onclick="window.location.href='/'">
		Back to Login
	</button>
	<div class="particle" style="width: 10px; height: 10px; top: 5%; left: 5%; animation-delay: 2s;"></div>
    <div class="particle" style="width: 10px; height: 10px; top: 10%; left: 80%; animation-delay: 1s;"></div>
    <div class="particle" style="width: 10px; height: 10px; top: 85%; left: 5%; animation-delay: 0s;"></div>
	<div class="particle" style="width: 10px; height: 10px; top: 15%; left: 20%; animation-delay: 0s;"></div>
	<div class="particle" style="width: 10px; height: 10px; top: 25%; left: 30%; animation-delay: 0s;"></div>
    <div class="particle" style="width: 10px; height: 10px; top: 90%; left: 90%; animation-delay: 1s;"></div>
    <div class="particle" style="width: 10px; height: 10px; top: 95%; left: 20%; animation-delay: 0s;"></div>
    <div class="particle" style="width: 10px; height: 10px; top: 15%; left: 95%; animation-delay: 1s;"></div>
    <div class="particle" style="width: 10px; height: 10px; top: 5%; left: 60%; animation-delay: 0s;"></div>
    <div class="particle" style="width: 10px; height: 10px; top: 85%; left: 75%; animation-delay: 1s;"></div>
    <div class="particle" style="width: 10px; height: 10px; top: 95%; left: 50%; animation-delay: 2s;"></div>
</body>
</html>