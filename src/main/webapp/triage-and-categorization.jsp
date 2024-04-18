<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Triage and Categorization</title>
<!-- Include Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<!-- Include jQuery Library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>

<div class="container">
    <!-- Include Complaint Selection Section -->
    <jsp:include page="complaint-selection.jsp"></jsp:include>
    
    <!-- Include Set Priority Modal Section -->
    <jsp:include page="set-priority-modal.jsp"></jsp:include>
    
    <!-- Include Complaint Categorization Form Section -->
    <jsp:include page="complaint-categorization-form.jsp"></jsp:include>
    
    <!-- Include Bulk Categorization Actions Section -->
    <jsp:include page="bulk-categorization-actions.jsp"></jsp:include>
</div>

</body>
</html>