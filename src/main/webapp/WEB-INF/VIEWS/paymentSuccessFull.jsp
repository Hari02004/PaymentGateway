<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Successful - FinTech Pay</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>

<div class="page-wrapper centered">
    <div class="container-wide">
        <div class="app-card">
            <div class="success-header">
                <h1>Payment Successful</h1>
            </div>
            <div class="app-card-body">
                <div class="success-message">
                    Your payment has been processed successfully.
                </div>

                <table class="data-table">
                    <tr>
                        <th>Transaction ID</th>
                        <td>${transactionDetails.transactionId}</td>
                    </tr>
                    <tr>
                        <th>Customer Name</th>
                        <td>${transactionDetails.customerName}</td>
                    </tr>
                    <tr>
                        <th>Business Email</th>
                        <td>${transactionDetails.businessEmail}</td>
                    </tr>
                    <tr>
                        <th>Amount</th>
                        <td>&#8377; ${transactionDetails.amount}</td>
                    </tr>
                    <tr>
                        <th>Currency</th>
                        <td>${transactionDetails.currency}</td>
                    </tr>
                    <tr>
                        <th>Status</th>
                        <td>
                            <span class="badge badge-active">${transactionDetails.status}</span>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="app-card-footer">
                <a href="http://localhost:4200/dashboard" class="btn btn-primary">Go to Home</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>
