<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manual Complaint Entry Form</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(document).ready(function() {
    $("#complaintForm").submit(function(event) {
        event.preventDefault(); // Prevent the default form submission

        var complainantName = $("#complainantName").val();
        var complainantEmail = $("#complainantEmail").val();
        var complainantPhoneNumber = $("#complainantPhoneNumber").val();
        var productName = $("#productName").val();
        var productModel = $("#productModel").val();
        var serialNumber = $("#serialNumber").val();
        var complaintDescription = $("#complaintDescription").val();

        // Simple client-side validation example
        if (complainantName && complainantEmail && complainantPhoneNumber && productName && productModel && serialNumber && complaintDescription) {
            $.ajax({
                url: '/api/complaints/create',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    complainantName: complainantName,
                    complainantEmail: complainantEmail,
                    complainantPhoneNumber: complainantPhoneNumber,
                    productName: productName,
                    productModel: productModel,
                    serialNumber: serialNumber,
                    complaintDescription: complaintDescription
                }),
                success: function(response) {
                    // Assuming response contains { complaintId: 'someUniqueId' }
                    alert('Complaint successfully registered with ID: ' + response.complaintId);
                    window.location.href = 'complaint-registration-dashboard.jsp?section=confirmation';
                }
            });
        } else {
            alert('Please fill in all required fields.');
        }
    });
});
</script>
</head>
<body>

<form id="complaintForm">
    <label for="complainantName">Complainant's Name:</label>
    <input type="text" id="complainantName" name="complainantName" required><br>

    <label for="complainantEmail">Email:</label>
    <input type="email" id="complainantEmail" name="complainantEmail" required><br>

    <label for="complainantPhoneNumber">Phone Number:</label>
    <input type="text" id="complainantPhoneNumber" name="complainantPhoneNumber" pattern="\\d{10}" title="Phone number must be 10 digits" required><br>

    <label for="productName">Product Name:</label>
    <input type="text" id="productName" name="productName" required><br>

    <label for="productModel">Model:</label>
    <input type="text" id="productModel" name="productModel" required><br>

    <label for="serialNumber">Serial Number:</label>
    <input type="text" id="serialNumber" name="serialNumber" required><br>

    <label for="complaintDescription">Complaint Description:</label>
    <textarea id="complaintDescription" name="complaintDescription" required></textarea><br>

    <button type="submit">Submit</button>
</form>

</body>
</html>