<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - FinTech Pay</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>

<div class="page-wrapper centered">
    <div class="container-narrow">
        <div class="app-card">
            <div class="app-card-header">Welcome Back</div>
            <div class="app-card-body">
                <form action="${pageContext.request.contextPath}/" method="POST">
                    <div class="form-group">
                        <label for="username">Email Address</label>
                        <input type="email" id="username" name="username" class="form-control" placeholder="Enter your email" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" class="form-control" placeholder="Enter your password" required>
                    </div>

                    <c:if test="${not empty message}">
                        <div class="alert alert-warning">${message}</div>
                    </c:if>

                    <c:if test="${not empty error}">
                        <div class="alert alert-error">
                            ${error}
                        </div>
                    </c:if>

                    <button type="submit" class="btn btn-primary btn-block">Sign In</button>
                </form>

                <div class="auth-footer">
                    Don't have an account? <a href="${pageContext.request.contextPath}/register">Register here</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
