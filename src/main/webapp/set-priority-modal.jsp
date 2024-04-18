<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="setPriorityModal" class="modal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Set Priority</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="priorityForm">
          <div class="form-group">
            <label for="priorityLevel">Priority Level</label>
            <select class="form-control" id="priorityLevel" required>
              <option value="">Select Priority</option>
              <option value="Low">Low</option>
              <option value="Medium">Medium</option>
              <option value="High">High</option>
              <option value="Urgent">Urgent</option>
            </select>
          </div>
          <div class="form-group">
            <label for="justificationText">Justification</label>
            <textarea class="form-control" id="justificationText" rows="3" required minlength="20"></textarea>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary" id="submitPriority">Save changes</button>
      </div>
    </div>
  </div>
</div>

<script>
$(document).ready(function(){
    $('#submitPriority').click(function(e) {
        e.preventDefault();
        var complaintId = $('#setPriorityModal').data('complaintId'); // Assume complaintId is set when modal opens
        var priorityLevel = $('#priorityLevel').val();
        var justificationText = $('#justificationText').val();

        if(!priorityLevel || justificationText.length < 20){
            alert('Please fill in all fields correctly.');
            return;
        }

        // Perform AJAX call to update the priority
        $.ajax({
            url: '/api/complaints/updatePriority',
            type: 'POST',
            data: JSON.stringify({ 
                complaintId: complaintId, 
                priorityLevel: priorityLevel, 
                justificationText: justificationText 
            }),
            contentType: "application/json",
            success: function(response) {
                // Assuming response to be boolean indicating success
                if(response) {
                    $('#setPriorityModal').modal('hide');
                    // Refresh the Complaint Selection section to reflect the new priority
                    // This should actually re-fetch the complaints, but for brevity, we'll just close the modal here
                    alert('Priority updated successfully.');
                } else {
                    alert('Failed to update the priority.');
                }
            },
            error: function() {
                alert('Error updating priority. Please try again.');
            }
        });
    });
    
    // Functionality to open modal with complaint ID would be here
    // For brevity, the functionality to populate the modal based on a complaint ID is omitted
});
</script>