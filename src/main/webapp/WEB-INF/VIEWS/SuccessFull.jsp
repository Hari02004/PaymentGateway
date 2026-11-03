<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Merchant Profile - FinTech Pay</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>

<%@ include file="includes/navbar.jspf" %>

<div class="page-wrapper">
    <div class="container-wide">
        <div class="app-card">
            <div class="app-card-header">Merchant Profile</div>
            <div class="app-card-body">
                <table class="data-table">
                    <tr>
                        <th>Merchant ID</th>
                        <td>${MerchantDetails.merchantId}</td>
                    </tr>
                    <tr>
                        <th>Company Name</th>
                        <td>${MerchantDetails.company_name}</td>
                    </tr>
                    <tr>
                        <th>Owner Name</th>
                        <td>${MerchantDetails.owner_name}</td>
                    </tr>
                    <tr>
                        <th>Business Email</th>
                        <td>${MerchantDetails.business_email}</td>
                    </tr>
                    <tr>
                        <th>Phone Number</th>
                        <td>${MerchantDetails.ph_number}</td>
                    </tr>
                    <tr>
                        <th>Address</th>
                        <td>${MerchantDetails.address}</td>
                    </tr>
                    <tr>
                        <th>Country</th>
                        <td>${MerchantDetails.country}</td>
                    </tr>
                    <tr>
                        <th>Status</th>
                        <td>
                            <span class="badge ${MerchantDetails.status == 'ACTIVE' ? 'badge-active' : 'badge-inactive'}">
                                ${MerchantDetails.status}
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <th>Created At</th>
                        <td>${MerchantDetails.created_at}</td>
                    </tr>
                </table>
            </div>
            <div class="app-card-footer">
                <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">Back</a>
                <a href="${pageContext.request.contextPath}/merchant/fetch/edit/${MerchantDetails.merchantId}" class="btn btn-primary">Edit Merchant</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>
