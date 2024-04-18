<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-12">
    <div class="table-responsive">
        <table class="table">
            <thead>
                <tr>
                    <th>Complaint ID</th>
                    <th>Submission Date</th>
                    <th>Complainant Name</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <!-- Dynamic rows will be added here -->
            </tbody>
        </table>
    </div>
</div>

<script>
$(document).ready(function(){
    // Fetch complaints for triage
    function fetchComplaints() {
        $.ajax({
            url: "/api/complaints/triage",
            type: "GET",
            data: {
                offset: 0,
                limit: 20,
                sortBy: "submissionDate",
                filterBy: null,
                filterValue: null
            },
            success: function(data) {
                /* Assuming the response is an array of complaints */
                $.each(data, function(i, complaint) {
                    $("table tbody").append(
                        "<tr>" +
                        "<td>" + complaint.complaintId + "</td>" +
                        "<td>" + complaint.submissionDate + "</td>" +
                        "<td>" + complaint.complainantName + "</td>" +
                        "<td>" + complaint.description + "</td>" +
                        "<td><button class='btn btn-primary set-priority' data-id='" + complaint.complaintId + "'>Set Priority</button> " +
                        "<button class='btn btn-secondary categorize' data-id='" + complaint.complaintId + "'>Categorize</button></td>" +
                        "</tr>"
                    );
                });
            },
            error: function(error) {
                console.log("Error fetching complaints: ", error);
            }
        });
    }

    // Initial fetch complaints
    fetchComplaints();

    // Set priority action
    $(document).on("click", ".set-priority", function() {
        var complaintId = $(this).data("id");
        // Navigate to Set Priority Modal
        // This would typically open a modal, but for the sake of this example, assume it's a simple alert
        alert("Navigate to Set Priority Modal for Complaint ID: " + complaintId);
        // Here you would typically populate the modal fields fetching details by complaintId if needed
    });

    // Categorize action
    $(document).on("click", ".categorize", function() {
        var complaintId = $(this).data("id");
        // Navigate within the same page to Complaint Categorization Form section
        // For simplicity, assume it navigates/alerts, in a real-world scenario, it might highlight or scroll to the form
        alert("Navigate to Complaint Categorization Form for Complaint ID: " + complaintId);
        // Here the form would be prepared for the specific complaintId
    });
});
</script>