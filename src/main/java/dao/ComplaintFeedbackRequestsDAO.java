package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class ComplaintFeedbackRequestsDAO {

	
	/**
	 * Saves a complaint feedback request into the database.
	 * @param complaint The Complaints object linked to the specific complaint.
	 * @param complainantContactInfo The contact information where the feedback request is sent.
	 * @param messageTemplate The CommunicationTemplates object representing the template used for the feedback request.
	 * @param customMessage A personalized message included in the feedback request.
	 * @param communicationStatus The status of the feedback request.
	 */
	public boolean saveComplaintFeedbackRequest(Complaints complaint, String complainantContactInfo, CommunicationTemplates messageTemplate, String customMessage, ComplaintFeedbackRequests.CommunicationStatus communicationStatus) {
	    Connection conn = DatabaseUtility.connect();
	    boolean result = false;
	    try {
	        String query = "INSERT INTO complaint_feedback_requests (complaint_id, complainant_contact_info, message_template_id, custom_message, communication_status) VALUES (?, ?, ?, ?, ?::communication_status)";
	        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	            pstmt.setInt(1, complaint.getId());
	            pstmt.setString(2, complainantContactInfo);
	            pstmt.setInt(3, messageTemplate.getId());
	            pstmt.setString(4, customMessage);
	            pstmt.setString(5, communicationStatus.toString());
	            int affectedRows = pstmt.executeUpdate();
	            if (affectedRows > 0) {
	                result = true;
	            }
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error saving complaint feedback request", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return result;
	}
	
	/**
	 * Retrieves all feedback requests related to a specific complaint.
	 * @param complaintId The unique identifier of the complaint for which feedback requests are to be fetched.
	 * @return A list of ComplaintFeedbackRequests objects related to the specified complaint.
	 */
	public List<ComplaintFeedbackRequests> fetchFeedbackRequestsByComplaintId(int complaintId) {
	    List<ComplaintFeedbackRequests> feedbackRequests = new ArrayList<>();
	    String sql = "SELECT * FROM complaint_feedback_requests WHERE complaint_id = ?";
	    try (Connection conn = DatabaseUtility.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, complaintId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                ComplaintFeedbackRequests feedbackRequest = new ComplaintFeedbackRequests();
	                feedbackRequest.setId(rs.getInt("id"));
	                // Assuming complaint and messageTemplate are fetched separately to fill
	                feedbackRequest.setComplaint(new Complaints()); // Placeholder for actual fetching
	                feedbackRequest.getComplaint().setId(rs.getInt("complaint_id"));
	                feedbackRequest.setComplainantContactInfo(rs.getString("complainant_contact_info"));
	                feedbackRequest.setMessageTemplate(new CommunicationTemplates()); // Placeholder for actual fetching
	                feedbackRequest.getMessageTemplate().setId(rs.getInt("message_template_id"));
	                feedbackRequest.setCustomMessage(rs.getString("custom_message"));
	                feedbackRequest.setCommunicationStatus(ComplaintFeedbackRequests.CommunicationStatus.valueOf(rs.getString("communication_status")));
	                feedbackRequest.setSentTime(rs.getTimestamp("sent_time"));
	                feedbackRequests.add(feedbackRequest);
	            }
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ComplaintFeedbackRequests.class.getName()).log(Level.SEVERE, "Could not fetch feedback requests by complaintId: " + complaintId, e);
	    } finally {
	        DatabaseUtility.disconnect();
	    }
	    return feedbackRequests;
	}
	
	
	/**
	 * Updates the status of a specific feedback request based on the outcome of the feedback request process.
	 * @param feedbackRequestId The unique identifier of the feedback request to be updated.
	 * @param newStatus The new status indicating the updated communication state.
	 * @return boolean indicating if the operation was successful.
	 */
	public boolean updateFeedbackRequestStatus(int feedbackRequestId, ComplaintFeedbackRequests.CommunicationStatus newStatus) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean updateSuccess = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE complaint_feedback_requests SET communication_status = ? WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, newStatus.toString());
	        pstmt.setInt(2, feedbackRequestId);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            updateSuccess = true;
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	        } catch (SQLException ex) {
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    return updateSuccess;
	}
	
	
	/**
	 * Deletes a feedback request from the database based on its unique identifier.
	 * @param feedbackRequestId The unique identifier of the feedback request to be deleted.
	 */
	public boolean deleteFeedbackRequest(int feedbackRequestId) {
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    boolean isSuccess = false;
	    String sql = "DELETE FROM complaint_feedback_requests WHERE id = ?;";
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, feedbackRequestId);
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            Logger.getLogger(this.getClass().getName()).info("Feedback request deleted successfully.");
	            isSuccess = true;
	        } else {
	            Logger.getLogger(this.getClass().getName()).warning("No feedback request found with ID: " + feedbackRequestId);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error deleting feedback request with ID: " + feedbackRequestId, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing PreparedStatement", e);
	            }
	        }
	    }
	    return isSuccess;
	}
}
