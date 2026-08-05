<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>API Keys - FinTech Pay</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>

<%@ include file="includes/navbar.jspf" %>

<div class="page-wrapper">
    <div class="container-wide">
        <div class="app-card">
            <div class="app-card-header">API Credentials</div>
            <div class="app-card-body">
                <p class="page-subtitle" style="margin-bottom: 1.25rem;">
                    Use these keys to integrate the payment gateway into your application.
                </p>

                <form action="${pageContext.request.contextPath}/merchant/apikey/getDetails" method="get">
                    <button type="submit" class="btn btn-primary">Get API Details</button>
                </form>

                <c:if test="${not empty error}">
                    <div class="alert alert-error" style="margin-top: 1.25rem;">
                        <strong>Error:</strong> <c:out value="${error}" />
                    </div>
                </c:if>

                <c:if test="${not empty apiDetails}">
                    <div style="margin-top: 1.5rem;">
                        <c:forEach items="${apiDetails}" var="apiItem">
                            <div class="api-item">
                                <p><strong>Public Key:</strong> <code><c:out value="${apiItem.publicKey}" /></code></p>
                                <p>
                                    <strong>Status:</strong>
                                    <span class="badge ${apiItem.active ? 'badge-active' : 'badge-inactive'}">
                                        <c:out value="${apiItem.active ? 'Active' : 'Inactive'}" />
                                    </span>
                                </p>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>

                <c:if test="${not empty secretKey}">
                    <div class="alert alert-warning" style="margin-top: 1.25rem;">
                        <strong>Secret Key:</strong> <code><c:out value="${secretKey}" /></code>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

</body>
</html>
