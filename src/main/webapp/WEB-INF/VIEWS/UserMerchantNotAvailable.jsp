
<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Error - FinTech Pay</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/app.css">
          <style> .error-message { color: red; font-weight: 600; text-align: center; } </style>
</head>

<body>

<%@ include file="includes/navbar.jspf" %>

<div class="page-wrapper">

    <div class="container-wide">

        <div class="app-card">

            <div class="app-card-header">
                Error
            </div>

            <div class="app-card-body">

                <p class="error-message" >
                    ${error}
                </p>

            </div>

            <div class="app-card-footer">

                <a href="${pageContext.request.contextPath}/home"
                   class="btn btn-secondary">
                    Back
                </a>

            </div>

        </div>

    </div>

</div>

</body>
</html>
```
