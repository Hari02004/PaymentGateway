<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Merchant - FinTech Pay</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>

<%@ include file="includes/navbar.jspf" %>

<div class="page-wrapper">
    <div class="container-wide">
        <div class="app-card">
            <div class="app-card-header">Edit Merchant Details</div>
            <div class="app-card-body">
                <form action="${pageContext.request.contextPath}/merchant/update" method="post">

                    <div class="form-group">
                        <label for="merchantId">Merchant ID</label>
                        <input type="text" id="merchantId" name="merchantId" class="form-control"
                               value="${merchant.merchantId}" readonly>
                    </div>

                    <div class="form-group">
                        <label for="company_name">Company Name</label>
                        <input type="text" id="company_name" name="company_name" class="form-control"
                               value="${merchant.company_name}" readonly>
                    </div>

                    <div class="form-group">
                        <label for="business_email">Business Email</label>
                        <input type="email" id="business_email" name="business_email" class="form-control"
                               value="${merchant.business_email}" readonly>
                    </div>

                    <div class="form-group">
                        <label for="owner_name">Owner Name</label>
                        <input type="text" id="owner_name" name="owner_name" class="form-control"
                               value="${merchant.owner_name}" readonly>
                    </div>

                    <div class="form-group">
                        <label for="ph_number">Phone Number</label>
                        <input type="number" id="ph_number" name="ph_number" class="form-control"
                               value="${merchant.ph_number}">
                    </div>

                    <div class="form-group">
                        <label for="address">Address</label>
                        <textarea id="address" class="form-control" rows="3" name="address">${merchant.address}</textarea>
                    </div>

                    <div class="form-group">
                        <label for="country">Country</label>
                        <input type="text" id="country" name="country" class="form-control"
                               value="${merchant.country}">
                    </div>

                    <input type="hidden" name="status" value="${merchant.status}">
                    <input type="hidden" name="created_at" value="${merchant.created_at}">

                    <div class="app-card-footer" style="border-top: none; padding-left: 0; padding-right: 0; background: transparent;">
                        <button type="submit" class="btn btn-success">Update Merchant</button>
                        <a href="${pageContext.request.contextPath}/merchant/fetch/details"
                           class="btn btn-secondary">
                            Cancel
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
