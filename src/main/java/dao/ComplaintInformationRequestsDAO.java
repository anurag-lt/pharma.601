package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class ComplaintInformationRequestsDAO {

	
	/**
	 * Retrieves all information requests associated with a specific complaint.
	 * @param complaintId The unique identifier of the complaint for which information requests are being fetched.
	 * @return A list of ComplaintInformationRequests objects.
	 */
	public List<ComplaintInformationRequests> fetchComplaintInformationRequests(int complaintId) {
	    List<ComplaintInformationRequests> informationRequests = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "SELECT * FROM complaint_information_requests WHERE fk_complaint_id = ?;";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, complaintId);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            ComplaintInformationRequests request = new ComplaintInformationRequests();
	            request.setId(rs.getInt("id"));
	            request.setRequestDate(rs.getTimestamp("request_date"));
	            request.setRequestStatus(ComplaintInformationRequests.CommunicationStatus.valueOf(rs.getString("request_status")));
	            request.setRequestType(rs.getString("request_type"));
	            request.setResponseDueDate(rs.getDate("response_due_date"));
	            request.setFollowUpCount(rs.getInt("follow_up_count"));
	            request.setCustomMessage(rs.getString("custom_message"));
	            informationRequests.add(request);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ComplaintInformationRequestsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(ComplaintInformationRequestsDAO.class.getName()).log(Level.SEVERE, null, e);
	            }
	        }
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                Logger.getLogger(ComplaintInformationRequestsDAO.class.getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	    return informationRequests;
	}
	
	/**
	 * Creates a new record of an information request sent to a complainant regarding their complaint. 
	 * @param requestDate The timestamp marking when the request was made.
	 * @param requestStatus The status of the information request, such as 'SENT', 'FAILED', 'DRAFT', or 'RESENT'.
	 * @param requestType Describes the type of information being requested from the complainant.
	 * @param responseDueDate The deadline by which the complainant should respond to the information request.
	 * @param followUpCount Indicates how many follow-up requests have been made for additional information.
	 * @param customMessage A personalized message included with the information request to the complainant.
	 * @param complaintId The unique identifier of the complaint to which this information request is linked.
	 * @return boolean Returns true if the operation is successful, false otherwise.
	 */
	public boolean createInformationRequest(Timestamp requestDate, CommunicationStatus requestStatus, String requestType, Date responseDueDate, int followUpCount, String customMessage, int complaintId) {
	    Connection conn = DatabaseUtility.connect();
	    boolean isSuccess = false;
	    String sql = "INSERT INTO complaint_information_requests (request_date, request_status, request_type, response_due_date, follow_up_count, custom_message, fk_complaint_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setTimestamp(1, requestDate);
	        pstmt.setString(2, requestStatus.name());
	        pstmt.setString(3, requestType);
	        pstmt.setDate(4, new java.sql.Date(responseDueDate.getTime()));
	        pstmt.setInt(5, followUpCount);
	        pstmt.setString(6, customMessage);
	        pstmt.setInt(7, complaintId);
	        
	        int affectedRows = pstmt.executeUpdate();
	        isSuccess = affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(ComplaintInformationRequestsDAO.class.getName()).log(Level.SEVERE, "Error creating information request", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return isSuccess;
	}
	
	
	/**
	 * Updates the status of an existing information request.
	 * @param requestId The unique identifier of the information request being updated.
	 * @param newStatus The new status to be set for the information request.
	 * @return true if the update was successful, false otherwise.
	 */
	public boolean updateInformationRequestStatus(int requestId, ComplaintInformationRequests.CommunicationStatus newStatus) {
	    Connection conn = DatabaseUtility.connect();
	    boolean updateResult = false;
	    String query = "UPDATE complaint_information_requests SET request_status = ? WHERE id = ?;";
	    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, newStatus.toString());
	        pstmt.setInt(2, requestId);
	
	        int rowAffected = pstmt.executeUpdate();
	        if (rowAffected == 1) {
	            updateResult = true;
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return updateResult;
	}
	
	/**
	 * Deletes a specific information request from the database.
	 * @param requestId The unique identifier of the information request to be deleted.
	 */
	public boolean deleteInformationRequest(int requestId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean success = false;
	    String sql = "DELETE FROM complaint_information_requests WHERE id = ?;";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, requestId);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            success = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error deleting information request", e);
	    } finally {
	        DatabaseUtility.closePreparedStatement(pstmt);
	        DatabaseUtility.disconnect(conn);
	    }
	    return success;
	}
}
