<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment - FinTech Pay</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>

<div class="page-wrapper centered">
    <div class="container-narrow">
        <div class="app-card">
            <div class="app-card-header">Complete Your Payment</div>
            <div class="app-card-body">
                <div class="payment-detail">
                    <span>Transaction ID</span>
                    <span>${txnId}</span>
                </div>
                <div class="payment-amount">&#8377; ${amount}</div>

                <form action="${pageContext.request.contextPath}/processPayment" method="POST">
                    <input type="hidden" name="txnId" value="${txnId}"/>
                    <button type="submit" class="btn btn-primary btn-block">Pay Now</button>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
