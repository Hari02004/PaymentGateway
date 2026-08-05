<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Merchant Registration - FinTech Pay</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>

<%@ include file="includes/navbar.jspf" %>

<div class="page-wrapper">
    <div class="container-wide">
        <div class="app-card">
            <div class="app-card-header">Merchant Registration</div>
            <div class="app-card-body">
                <form action="${pageContext.request.contextPath}/merchant" method="POST">

                    <div class="form-section-title">Account Credentials</div>

                    <div class="form-group">
                        <label for="username">User Email / Username</label>
                        <input type="email" id="username" name="username" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" class="form-control" required>
                    </div>

                    <div class="form-section-title">Business Profile</div>

                    <div class="form-group">
                        <label for="company_name">Company Name</label>
                        <input type="text" id="company_name" name="company_name" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="owner_name">Owner Full Name</label>
                        <input type="text" id="owner_name" name="owner_name" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="business_email">Business Operations Email</label>
                        <input type="email" id="business_email" name="business_email" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="ph_number">Phone Number</label>
                        <input type="tel" id="ph_number" name="ph_number" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="address">Address</label>
                        <input type="text" id="address" name="address" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="country">Country</label>
                        <input type="text" id="country" name="country" class="form-control" required>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="alert alert-error">${error}</div>
                    </c:if>
                    <c:if test="${not empty message}">
                        <div class="alert alert-success">${message}</div>
                    </c:if>

                    <button type="submit" class="btn btn-primary btn-block">Submit Registration</button>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
