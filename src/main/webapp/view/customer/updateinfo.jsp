<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>ShopKart | Update Profile</title>
    <link rel="stylesheet" href="../../css/updateinfo.css" >
    <link rel="stylesheet" href="../../css/colorScheme.css" >
    <link rel="stylesheet" href="../../css/siteMapBanner.css">
  </head>
  <body>
    <%@ include file="../../templates/header.jsp" %>
    <form action="/update" method="post" enctype="multipart/form-data">
    	<div style="margin-top: 100px">
    		<div class="SiteMapBanner">
		        <img src="../../images/siteMapBanner.jpg">
		        <p>Home > Product Catalogue > Update Profile</p>
		    </div>
	      <h1>Update your profile :</h1>
	      <% String msg = request.getParameter("msg"); %>
	    	<% if (msg != null && msg.equals("failed")) { %>
	    		<h3 id="error-msg">Wrong Password entered!</h3>
	    	<% } %>
	    	<% if (msg != null && msg.equals("invalidFile")) { %>
	    		<h3 id="error-msg">The uploaded file exceeds the mentioned specifications!</h3>
	    	<% } %>
	    	<% if (msg != null && msg.equals("invalidPassword")) { %>
	    		<h3 id="error-msg">The new password doesn't fulfill the requirements!</h3>
	    	<% } %>
	      <div class="container">	      
	      <div class="image-element">
                <h3>Upload your profile picture :</h3>
                <input type="file" name="profilePicture" accept="image/*" />
                <small>Accepted formats: JPG,JPEG,PNG; Max.size=200KB; Max.resolution=500px</small>
          </div>
	        <div class="grid">              
	          <div class="element">
	            <h3>Your new name :</h3>
	            <input type="text" name="newName" value="<%= customer.getName()%>" placeholder="Enter new name " required />
	          </div>

	          <div class="element">
	            <h3>Your new phone number :</h3>
	            <input type="tel" name="newPhone" pattern="[0-9]{10}" value="<%= customer.getPhone()%>" placeholder="Enter new phone number " required />
	          </div>

	          <div class="element">
	            <h3>Your new address :</h3>
	            <input type="text" name="newAddress" value="<%= customer.getAddress() %>" placeholder="Enter new address " required />
	          </div>

	          <div class="element">
	            <h3>Your new password :</h3>
	            <input type="password" name="newPassword" placeholder="Enter new Password "/>
	            <small>The password must contain 8-15 characters. Atleast one lowercase, one uppercase, one digit and one special character</small>
	          </div>
	        </div>

	        <div class="confirm-element">
	          <h3>Type your old password to confirm :</h3>
	          <input type="password" name="oldPassword" placeholder="Enter old password " required />
	        </div>
	      </div>

	      <div class="buttons">
	          <button class="save">Save Changes</button>
	          <button class="go-back" onclick="window.location.href='/product'">Go back</button>
	      </div>
	    </div>
    </form>

    <%@ include file="../../templates/footer.jsp" %>
  </body>
</html>