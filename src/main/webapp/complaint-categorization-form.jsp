<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="col-12">
    <form id="complaintCategorizationForm">
        <div class="form-group">
            <label for="categorySelect">Category</label>
            <select class="form-control" id="categorySelect" required>
                <!-- Categories will be dynamically loaded here -->
            </select>
        </div>
        <div class="form-group">
            <label for="subcategorySelect">Subcategory</label>
            <select class="form-control" id="subcategorySelect" required>
                <!-- Subcategories will be dynamically loaded based on the category -->
            </select>
        </div>
        <div class="form-group">
            <label for="notesText">Notes (optional)</label>
            <textarea class="form-control" id="notesText" rows="3"></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Submit Categorization</button>
    </form>
    
    <!-- Placeholder for Confirmation Modal follows... -->
</div>

<script>
$(document).ready(function(){
    // Fetch categories for dropdown
    function fetchCategories() {
        $.ajax({
            url: "/api/categories",
            type: "GET",
            success: function(data) {
                // Assuming 'data' is an array of categories
                $("#categorySelect").empty().append('<option value="">Select Category</option>');
                $.each(data, function(index, category) {
                    $("#categorySelect").append($('<option>', {
                        value: category.categoryID,
                        text: category.categoryName
                    }));
                });
            },
            error: function() {
                alert('Failed to load categories');
            }
        });
    }

    // Fetch subcategories based on the selected category
    $('#categorySelect').change(function() {
        var categoryId = $(this).val();
        fetchSubCategories(categoryId);
    });

    function fetchSubCategories(categoryId) {
        $.ajax({
            url: "/api/subcategories?categoryId=" + categoryId,
            type: "GET",
            success: function(data) {
                // Assuming 'data' is an array of subcategories
                $("#subcategorySelect").empty().append('<option value="">Select Subcategory</option>');
                $.each(data, function(index, subcategory) {
                    $("#subcategorySelect").append($('<option>', {
                        value: subcategory.subcategoryId,
                        text: subcategory.subcategoryName
                    }));
                });
            },
            error: function() {
                alert('Failed to load subcategories');
            }
        });
    }

    // Form submission
    $('#complaintCategorizationForm').submit(function(e) {
        e.preventDefault();
        var complaintId = /* Assume this is dynamically set */;
        var categoryId = $('#categorySelect').val();
        var subcategoryId = $('#subcategorySelect').val();
        var notes = $('#notesText').val();

        // Perform AJAX call to submit the categorization
        $.ajax({
            url: '/api/complaintCategorize',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify({ 
                complaintId: complaintId,
                categoryId: categoryId,
                subcategoryId: subcategoryId,
                notes: notes 
            }),
            success: function(response) {
                // Assuming 'response' signals successful operation
                // Display confirmation modal or message
                alert('Complaint categorized successfully.');
                // Optionally, direct or refresh the page to show the updated status
            },
            error: function() {
                alert('Error during categorization. Please try again.');
            }
        });
    });

    // Initial load of categories
    fetchCategories();
});
</script>