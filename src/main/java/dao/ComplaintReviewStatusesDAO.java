package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ComplaintReviewStatusesDAO {

	
	/**
	 * Fetches complaint details including investigation reports, action plan outcomes, 
	 * communication logs, and any corrective actions based on a provided complaint ID. 
	 * @param complaintId The unique identifier of the complaint for which review details are being fetched.
	 * @return A ComplaintReviewStatuses object containing the details of the review status for the complaint.
	 * Used in the Review and Closure Module page, specifically within the Complaint Review Checklist section.
	 */
	public ComplaintReviewStatuses findComplaintDetailsById(long complaintId) {
	    ComplaintReviewStatuses reviewStatus = null;
	    Connection conn = DatabaseUtility.connect();
	    try {
	        String query = "SELECT * FROM complaint_review_statuses WHERE fk_complaint_id = ?";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setLong(1, complaintId);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            reviewStatus = new ComplaintReviewStatuses();
	            reviewStatus.setId(rs.getInt("id"));
	            reviewStatus.setReviewStatus(rs.getBoolean("review_status"));
	            reviewStatus.setReviewNotes(rs.getString("review_notes"));
	            reviewStatus.setReviewDate(rs.getDate("review_date"));
	            // Assuming Complaints object initialization here
	            // reviewStatus.setFkComplaintId(...);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error fetching complaint review details by ID", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return reviewStatus;
	}
	
	/**
	* Updates the complaint record to reflect the review status post-checklist completion.
	* @param complaintId The unique identifier of the complaint being reviewed.
	* @param reviewStatus The overall status of the review.
	* @param reviewNotes Optional notes added by the Review Officer.
	* @return boolean indicating the success of the operation.
	*/
	public boolean saveComplaintReviewStatus(long complaintId, boolean reviewStatus, String reviewNotes) {
	    Connection conn = DatabaseUtility.connect();
	    String sql = "UPDATE complaint_review_statuses SET review_status = ?, review_notes = ? WHERE fk_complaint_id = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setBoolean(1, reviewStatus);
	        pstmt.setString(2, reviewNotes);
	        pstmt.setLong(3, complaintId);
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            return true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error updating complaint review status", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return false;
	}
	
	/**
	 * Retrieves a list of required documents for completing the review based on the provided complaint ID.
	 * @param complaintId The unique ID of the complaint for which required documents for the review are being retrieved.
	 * @return A list of DocumentDetail objects representing the required documents for the review.
	 */
	public List<DocumentDetail> getAllRequiredDocumentsForReview(long complaintId) {
	    Connection conn = null;
	    List<DocumentDetail> documentDetails = new ArrayList<>();
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "SELECT document_name, document_type, upload_date FROM documents WHERE fk_complaint_id = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, complaintId);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            DocumentDetail detail = new DocumentDetail();
	            detail.setDocumentName(rs.getString("document_name"));
	            detail.setDocumentType(rs.getString("document_type"));
	            detail.setUploadDate(rs.getDate("upload_date"));
	            documentDetails.add(detail);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(DatabaseUtility.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return documentDetails;
	}
}
