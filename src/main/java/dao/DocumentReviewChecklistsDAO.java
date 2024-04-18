package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DocumentReviewChecklistsDAO {

	
	/**
	 * Retrieves detailed information about a complaint, including the verification status of
	 * key documents, based on the complaint's unique identifier.
	 * @param complaintId The unique identifier of the complaint.
	 * @return DocumentReviewChecklists object containing verification statuses of key documents.
	 */
	public DocumentReviewChecklists findComplaintDetailsById(Long complaintId) {
	    DocumentReviewChecklists checklist = null;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = "SELECT * FROM document_review_checklists WHERE fk_complaint_id = ?";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, complaintId);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            checklist = new DocumentReviewChecklists();
	            checklist.setId(rs.getLong("id"));
	            checklist.setInvestigationReportVerified(rs.getBoolean("investigation_report_verified"));
	            checklist.setActionPlanVerified(rs.getBoolean("action_plan_verified"));
	            checklist.setCommunicationLogVerified(rs.getBoolean("communication_log_verified"));
	            checklist.setCorrectiveActionVerified(rs.getBoolean("corrective_action_verified"));
	            // Assuming there's a method in Complaints to set by ID only, otherwise, additional logic might be needed
	            Complaints complaint = new Complaints();
	            complaint.setId(rs.getLong("fk_complaint_id"));
	            checklist.setFkComplaintId(complaint);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try { pstmt.close(); } catch (SQLException e) { /* Ignored */ }
	        }
	        if (rs != null) {
	            try { rs.close(); } catch (SQLException e) { /* Ignored */ }
	        }
	    }
	    return checklist;
	}
	
	/**
	 * Updates the complaint record to reflect the review status post-checklist completion.
	 * @param complaintId The complaint's unique identifier to which the review checklist pertains.
	 * @param reviewStatus The overall status of the review; true if all checklist items are verified, false otherwise.
	 * @param reviewNotes Optional notes added by the Review Officer.
	 * @return boolean indicating the success of the operation.
	 */
	public boolean saveComplaintReviewStatus(Long complaintId, Boolean reviewStatus, String reviewNotes) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String query = "UPDATE document_review_checklists SET investigation_report_verified = ?, action_plan_verified = ?, communication_log_verified = ?, corrective_action_verified = ? WHERE fk_complaint_id = ?";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(query);
	        // Assuming each boolean flag is represented by each review status
	        // This part may require adjustment based on actual table structure
	        pstmt.setBoolean(1, reviewStatus);
	        pstmt.setBoolean(2, reviewStatus);
	        pstmt.setBoolean(3, reviewStatus);
	        pstmt.setBoolean(4, reviewStatus);
	        pstmt.setLong(5, complaintId);
	        int affectedRows = pstmt.executeUpdate();
	        if(affectedRows > 0) {
	            // If review notes should be saved separately, implement another query here
	            return true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try { if(pstmt != null) pstmt.close(); } catch (SQLException ex) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    return false;
	}
	
	public List<Document> getAllRequiredDocumentsForReview(Long complaintId) {
	    List<Document> documents = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String query = "SELECT * FROM documents WHERE fk_complaint_id = ?;";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(query);
	        pstmt.setLong(1, complaintId);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            Document document = new Document();
	            document.setId(rs.getLong("id"));
	            document.setDocumentName(rs.getString("document_name"));
	            document.setDocumentType(rs.getString("document_type"));
	            document.setUploadDate(rs.getDate("upload_date"));
	            documents.add(document);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e); } }
	        if (rs != null) { try { rs.close(); } catch (SQLException e) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e); } }
	    }
	    return documents;
	}
}
