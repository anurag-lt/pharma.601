<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="confirmationModal" tabindex="-1" aria-labelledby="confirmationModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="confirmationModalLabel">Complaint Submission Confirmation</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        Your complaint has been successfully submitted. Your unique complaint ID is: <span id="complaintId"></span>.
        <p>Please use this ID to track the status of your complaint.</p>
        <a href="#" id="viewComplaintStatusLink">View Complaint Status</a>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" id="confirmationOkBtn">OK</button>
      </div>
    </div>
  </div>
</div>

<script>
$(document).ready(function() {
    var complaintId = 'GetThisFromQueryParamsOrSomeOtherMethod'; // Placeholder: Implement logic to retrieve and set this value accordingly
    $('#complaintId').text(complaintId);
    $('#viewComplaintStatusLink').attr('href', `complaint-status-page?complaintId=${complaintId}`);

    $('#confirmationOkBtn').click(function() {
        $('#confirmationModal').modal('hide');
        // Simulate refreshing the page to allow for the submission of a new complaint
        location.reload();
    });

    // Assuming this is called when the modal should be shown
    $('#confirmationModal').modal('show');

    // Optional: Implement the call to logConfirmationAcknowledgement if needed
    // Example API call to log the acknowledgement
    /*
    $.ajax({
        url: '/api/logConfirmationAcknowledgement',
        type: 'POST',
        data: JSON.stringify({ complaintId: complaintId, dateTime: new Date().toISOString() }),
        contentType: 'application/json',
        success: function(response) {
            console.log('Acknowledgement logged');
        },
        error: function() {
            console.log('Error logging acknowledgement');
        }
    });
    */
});
</script>