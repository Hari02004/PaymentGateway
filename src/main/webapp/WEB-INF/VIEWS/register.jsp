<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - FinTech Pay</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>

<div class="page-wrapper centered">
    <div class="container-narrow">
        <div class="app-card">
            <div class="app-card-header">Create Account</div>
            <div class="app-card-body">
                <form action="${pageContext.request.contextPath}/register" method="POST">
                    <div class="form-group">
                        <label for="email">Email Address</label>
                        <input type="email" id="email" name="email" class="form-control" placeholder="you@example.com" required>
                    </div>
                    <div class="form-group">
                        <label for="name">Username</label>
                        <input type="text" id="name" name="name" class="form-control" placeholder="Choose a username" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" class="form-control" placeholder="Create a password" required>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="alert alert-error">${error}</div>
                    </c:if>

                    <button type="submit" class="btn btn-primary btn-block">Create Account</button>
                </form>

                <div class="auth-footer">
                    Already have an account? <a href="${pageContext.request.contextPath}/">Sign in here</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
