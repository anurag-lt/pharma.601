<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-6">
        <form id="complaintForm">
            <div class="mb-3">
                <label for="productName" class="form-label">Product Name</label>
                <input type="text" class="form-control" id="productName" name="productName" required>
            </div>
            <div class="mb-3">
                <label for="model" class="form-label">Model</label>
                <input type="text" class="form-control" id="model" name="model" required>
            </div>
            <div class="mb-3">
                <label for="serialNumber" class="form-label">Serial Number</label>
                <input type="text" class="form-control" id="serialNumber" name="serialNumber" required>
            </div>
            <div class="mb-3">
                <label for="complaintDescription" class="form-label">Complaint Description</label>
                <textarea class="form-control" id="complaintDescription" name="complaintDescription" required></textarea>
            </div>
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="mb-3">
                <label for="phoneNumber" class="form-label">Phone Number</label>
                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" required pattern="\\d+">
            </div>
            <button type="submit" class="btn btn-primary">Submit Complaint</button>
        </form>
    </div>
</div>

<script>
$(document).ready(function() {
    $('#complaintForm').on('submit', function(e) {
        e.preventDefault();
        
        // Form validation
        var isValid = true;
        $('#complaintForm').find(':input[required]:visible').each(function(i, requiredField) {
            if (!$(requiredField).val()) {
                isValid = false;
                $(requiredField).addClass('is-invalid');
            } else {
                $(requiredField).removeClass('is-invalid');
            }
        });

        if (!isValid) {
            return;
        }

        // Dummy API endpoint and payload
        var apiEndpoint = '/api/saveComplaint';
        var formData = {
            productName: $('#productName').val(),
            model: $('#model').val(),
            serialNumber: $('#serialNumber').val(),
            complaintDescription: $('#complaintDescription').val(),
            name: $('#name').val(),
            email: $('#email').val(),
            phoneNumber: $('#phoneNumber').val()
        };

        $.ajax({
            url: apiEndpoint,
            type: 'POST',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function(response) {
                // Assuming the response contains an object with a complaintId
                // Navigational flow after successful submission
                window.location.href = `complaint-submission-form?section=confirmation-modal&complaintId=${response.complaintId}`;
            },
            error: function() {
                alert('An error occurred while submitting your complaint. Please try again.');
            }
        });
    });
});
</script>