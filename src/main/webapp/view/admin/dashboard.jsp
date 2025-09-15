<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.springspartans.shopkart.model.Product" %> 
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ShopKart | Analytics Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorScheme.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorSchemeForOrderStatus.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css" />
</head>

<body>
    <%@ include file="../../templates/admin_sidebar.jsp" %>
    <div style="margin-left : 300px ; margin-right : 100px;">
        <%@ include file="../../templates/admin_header.jsp" %>
        <header>
            <h1>Analytics Dashboard</h1>
        </header>

        <%
            // Check if admin is logged in
            if (admin != null) {
        %>
        <!-- Summary Statistics -->
        <% int orderCountByStatusArr[] = (int[])request.getAttribute("orderCountByStatusArr"); %>
        <% double totalSales = (double)request.getAttribute("totalSales"); %>
        <% int prodCount = (int)request.getAttribute("prodCount"); %>
        <% int custCount = (int)request.getAttribute("custCount"); %>
        
        <div class="card-summary-stats">
            <div class="card-name total-sales">
                <h2>Sales Last week </h2>
                <h1>₹ <%= String.format("%.0f", totalSales) %></h1>
            </div>
            <div class="card-name product-count">
                <h2>Product Count</h2>
                <h1><%= prodCount %></h1>
            </div>
            <div class="card-name users-count">
                <h2>Customer Count</h2>
                <h1><%= custCount %></h1>
            </div>
            <div class="card-name active-orders">
                <h2>Active Orders</h2>
                <h1><%= orderCountByStatusArr[0] + orderCountByStatusArr[1] %></h1>
            </div>
        </div>

        <div class="card-container">
            <div class="card">
                <h2>Order Fulfillment Ratio</h2>
                <canvas id="order-fulfillment-chart" width="200" height="200"></canvas>
            </div>

            <!-- Lists -->
            <% List<Object[]> topSellers = (List<Object[]>)request.getAttribute("topSellers"); %>
            <div class="card">
                <h2>Top Selling Products</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Product Name</th>
                            <th>Stock sold</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Object[] obj : topSellers) { %>
	                        <tr>
	                        	<% Product prod = (Product) obj[0]; %>
	                        	<% Integer stock = (Integer) obj[1]; %>
	                            <% if (prod != null) { %>
	                            	<td>
		                                <div class="name">
		                                    <img src="${pageContext.request.contextPath}/uploads/product/<%= prod.getImage() %>">
		                                    <h4><%= prod.getName() %></h4>
		                                </div>
		                            </td>
		                            <td><p><%= stock %></p></td>
	                            <% } %>
	                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="card-container">
            <!-- Line Graph -->
            <div class="card line-graph">
                <h2>Day by Day Sales (Last 7 Days)</h2>
                <canvas id="sales-line-chart" width="400" height="250"></canvas>
            </div>

            <!-- Bar Graph -->
            <div class=" card bar-graph">
                <h2>Customer Activity (Today)</h2>
                <canvas id="customer-activity-chart" width="150" height="150"></canvas>
            </div>
        </div>
        <%
            }
        %>

        <footer>
            <p>© 2024 Analytics Dashboard</p>
        </footer>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
		 	// Pie Chart
		 	<% int orderCountByStatusArr[] = (int[])request.getAttribute("orderCountByStatusArr"); %>
		 	const pending = [<%= orderCountByStatusArr[0] %>];
		 	const shipped = [<%= orderCountByStatusArr[1] %>];
		 	const delivered = [<%= orderCountByStatusArr[2] %>];
		 	const cancelled = [<%= orderCountByStatusArr[3] %>];
		    const pieCtx = document.getElementById('order-fulfillment-chart').getContext('2d');
		    new Chart(pieCtx, {
		        type: 'pie',
		        data: {
		            labels: ['Pending', 'Shipped', 'Delivered', 'Cancelled'],
		            datasets: [{
		                label: 'Order Fulfillment Ratio',
		                data: [pending, shipped, delivered, cancelled], 
		                backgroundColor: ['#FFBF00', '#0047AB', '#568203', '#B31B1B']
		            }]
		        }
		    });
    
		 	// Line Graph
		 	<% List<Timestamp> lastWeek = (List<Timestamp>) request.getAttribute("lastWeek"); %>
		 	<% List<Double> lastWeekSales = (List<Double>) request.getAttribute("lastWeekSales"); %>
		    const lineCtx = document.getElementById('sales-line-chart').getContext('2d');
		    const myLabels = [
		        <%
		            for (int i = 0; i < lastWeek.size(); i++) {
		                Timestamp timestamp = lastWeek.get(i);
		                String formattedDate = new java.text.SimpleDateFormat("dd-MMM").format(timestamp);
		                out.print("\"" + formattedDate + "\"");
		                if (i < lastWeek.size() - 1) {
		                    out.print(", ");
		                }
		            }
		        %>
		    ];
		    const myData = [
		        <%
		            for (int i = 0; i < lastWeekSales.size(); i++) {
		                Double sales = lastWeekSales.get(i);
		                out.print(sales);
		                if (i < lastWeekSales.size() - 1) {
		                    out.print(", ");
		                }
		            }
		        %>
		    ];
		    new Chart(lineCtx, {
		        type: 'line',
		        data: {
		            labels: myLabels, 
		            datasets: [{
		                label: 'Sales (in ₹)',
		                data: myData, 
		                borderColor: 'var(--low-text-button)',
		                backgroundColor: 'rgba(0, 123, 158, 0.5)',
		                fill: true
		            }]
		        }
		    });
		
		    // Bar Graph
		    <% int[] custActivity = (int[])request.getAttribute("custActivity"); %>
		    const barCtx = document.getElementById('customer-activity-chart').getContext('2d');
		    new Chart(barCtx, {
		        type: 'bar',
		        data: {
		            labels: ['New Signups', 'Logins', 'Customers who ordered'],
		            datasets: [
		            	{
		                    label: 'Customer Activity',
		                    data: [<%= custActivity[0] %>, <%= custActivity[1] %>, <%= custActivity[2] %>], 
		                    backgroundColor: ['#007B9E', '#6D6D6D', '#FF6C6C']
		                }
		            ]
		        }
		    });
    </script>
</body>

</html>