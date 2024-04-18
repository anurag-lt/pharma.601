<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="bulkCategorizationModal" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Bulk Categorization Actions</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="bulkCategorizationForm">
          <div class="form-group">
            <label for="bulkCategory">Category</label>
            <select class="form-control" id="bulkCategory" required>
              <!-- Categories will be dynamically loaded -->
            </select>
          </div>
          <div class="form-group">
            <label for="bulkPriority">Priority</label>
            <select class="form-control" id="bulkPriority" required>
              <option value="">Select Priority</option>
              <option value="Low">Low</option>
              <option value="Medium">Medium</option>
              <option value="High">High</option>
              <option value="Urgent">Urgent</option>
            </select>
          </div>
          <input type="hidden" id="complaintIds" value="" /> <!-- To be dynamically populated -->
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary" id="applyCategorization">Apply Categorization</button>
      </div>
    </div>
  </div>
</div>

<script>
$(document).ready(function(){
    $('#applyCategorization').click(function(e) {
        e.preventDefault();
        var selectedComplaintIds = $('#complaintIds').val().split(','); // Assuming already populated with selected complaint IDs
        var category = $('#bulkCategory').val();
        var priority = $('#bulkPriority').val();

        if(!category || !priority) {
            alert('Both category and priority selections are required.');
            return;
        }

        // Perform the bulk categorization and priority update through AJAX call
        $.ajax({
            url: '/api/complaints/updateBulk',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify({ 
                complaintIds: selectedComplaintIds,
                category: category,
                priority: priority,
            }),
            success: function(response) {
                // Assuming response indicates success
                $('#bulkCategorizationModal').modal('hide');
                alert('Bulk categorization and priority update successful.');

                // Optionally: Refresh the Complaint Selection section to reflect new categories and priorities
            },
            error: function() {
                alert('Error during bulk update. Please try again.');
            }
        });
    });

    // Function to dynamically populate category options would be here
    // For brevity, not included.
});
</script>