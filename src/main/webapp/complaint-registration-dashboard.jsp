<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Complaint Registration Dashboard</title>
<!-- Add link to CSS files -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>

<div class="container">
    <h1>Complaint Registration Dashboard</h1>
    <div id="newComplaintsSection">
        <jsp:include page="new-complaints-tables.jsp"></jsp:include>
    </div>
    <div id="manualComplaintEntrySection">
        <jsp:include page="manual-complaint-entry-form.jsp"></jsp:include>
    </div>
</div>

</body>
</html>