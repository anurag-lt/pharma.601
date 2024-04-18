<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>New Complaints Table</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(document).ready(function() {
    function fetchNewComplaints() {
        $.ajax({
            url: '/api/complaints/fetch-new',
            type: 'GET',
            data: {
                limit: 10,
                offset: 0,
                filters: {},
                sortBy: 'date',
                sortDirection: 'asc'
            },
            success: function(response) {
                // Assuming the response is an array of complaints
                response.forEach(function(complaint) {
                    var row = '<tr>' +
                        '<td>' + complaint.complaintId + '</td>' +
                        '<td>' + complaint.complainantName + '</td>' +
                        '<td>' + complaint.productDetails + '</td>' +
                        '<td>' + complaint.complaintSummary + '</td>' +
                        '<td>' + complaint.dateSubmitted + '</td>' +
                        '<td><button class="assign" data-id="' + complaint.complaintId + '">Assign for Triage</button></td>' +
                        '<td><button class="update" data-id="' + complaint.complaintId + '">Update Status</button></td>' +
                        '<td><button class="edit" data-id="' + complaint.complaintId + '">Edit Details</button></td>' +
                        '</tr>';
                    $('#complaintsTable tbody').append(row);
                });
            }
        });
    }

    fetchNewComplaints(); // Load complaints on page load

    $('#complaintsTable').on('click', '.assign', function() {
        var complaintId = $(this).data('id');
        window.location.href = `/triage-and-categorization?modal=assign-triage&complaintId=${complaintId}`;
    });

    $('#complaintsTable').on('click', '.update', function() {
        var complaintId = $(this).data('id');
        // Example of dynamically loading a modal for status update. Actual implementation may vary.
        alert('Redirect to status update modal/page for Complaint ID: ' + complaintId);
    });

    $('#complaintsTable').on('click', '.edit', function() {
        var complaintId = $(this).data('id');
        window.location.href = `/complaint-detail-edit?complaintId=${complaintId}`;
    });
});
</script>
</head>
<body>

<table id="complaintsTable" border="1">
    <thead>
        <tr>
            <th>Complaint ID</th>
            <th>Complainant Name</th>
            <th>Product Details</th>
            <th>Complaint Summary</th>
            <th>Date Submitted</th>
            <th colspan="3">Actions</th>
        </tr>
    </thead>
    <tbody>
        <!-- Complaints are dynamically added here -->
    </tbody>
</table>

</body>
</html>