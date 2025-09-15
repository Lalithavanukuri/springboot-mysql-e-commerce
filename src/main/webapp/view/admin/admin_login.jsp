<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>ShopKart | Admin Login</title>
    <link rel="stylesheet" href="../../css/admin_login.css">
    <link rel="stylesheet" href="../../css/colorScheme.css" />
</head>
<body>
    <form action="/admin/login" method="get">
        <div class="adminLogin-container">
            <h1>Admin Login</h1>
            <% String msg = request.getParameter("msg"); %>
			<% if (msg != null && msg.equals("failed")) { %>
				<h3 id="custom-msg">Invalid credentials!</h3>
			<% } %>	
			<% if (msg != null && msg.equals("logout")) { %>
				<h3 id="custom-msg">You've been logged out!</h3>
			<% } %>
            <div class="form-group">
                <input type="email" name="email" placeholder="Email" required />
            </div>
            <div class="form-group">
                <input type="password" name="password" placeholder="Password" required />
            </div>
            <div class="form-group">
                <input type="password" name="security_key" placeholder="Security Key" required />
            </div>
            <div class="form-group">
                <button type="submit" class="btn">Log in</button>
            </div>
           
        </div>
    </form>
</body>
</html>
