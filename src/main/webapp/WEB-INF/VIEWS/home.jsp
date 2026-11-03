<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - FinTech Pay</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>

<%@ include file="includes/navbar.jspf" %>

<div class="page-wrapper">
    <div class="container-wide">
        <h1 class="page-title">Merchant Dashboard</h1>
        <p class="page-subtitle">Manage your merchant profile, API keys, and payment settings.</p>

        <div class="dashboard-grid">
            <div class="action-card">
                <div class="icon">🏪</div>
                <h3>Register Merchant</h3>
                <p>Set up your business profile and start accepting payments.</p>
                <a href="${pageContext.request.contextPath}/merchant" class="btn btn-primary">Add Merchant Details</a>
            </div>

            <div class="action-card">
                <div class="icon">📋</div>
                <h3>View Profile</h3>
                <p>View your registered merchant details and account status.</p>
                <a href="${pageContext.request.contextPath}/merchant/fetch/details" class="btn btn-primary">View Details</a>
            </div>

            <div class="action-card">
                <div class="icon">🔑</div>
                <h3>API Keys</h3>
                <p>Generate and manage your payment gateway API credentials.</p>
                <a href="${pageContext.request.contextPath}/merchant/apikey" class="btn btn-primary">Manage API Keys</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>
