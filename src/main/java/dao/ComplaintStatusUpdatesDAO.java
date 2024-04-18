package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;

public class ComplaintStatusUpdatesDAO {

	
	/**
	 * Updates the status of a complaint based on the provided complaint ID and new status.
	 * This method is pivotal across various sections to reflect the current state of a complaint as it moves through different stages of resolution.
	 * @param complaintId The unique identifier of the complaint whose status is being updated.
	 * @param newStatus The new status to set for the specified complaint.
	 * @return boolean indicating operation success or failure.
	 */
	public boolean updateComplaintStatus(String complaintId, ComplaintStatusUpdates.ComplaintStatus newStatus) {
	    Connection conn = DatabaseUtility.connect();
	    try {
	        String query = "UPDATE complaint_status_updates SET complaint_status = ? WHERE id = ?";
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, newStatus.name());
	        pstmt.setString(2, complaintId);
	        int updatedRows = pstmt.executeUpdate();
	        DatabaseUtility.disconnect(conn);
	        return updatedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        DatabaseUtility.disconnect(conn);
	        return false;
	    }
	}
	
	/**
	 * Fetches historical status updates for a specific complaint based on its unique identifier.
	 * This method aids in the analysis of complaint handling effectiveness and timeline efficiency.
	 * @param complaintId The unique identifier of the complaint to retrieve status updates for.
	 * @return List of ComplaintStatusUpdates representing the historical status updates of the complaint.
	 */
	public List<ComplaintStatusUpdates> fetchComplaintStatusUpdatesByComplaintId(String complaintId) {
	  List<ComplaintStatusUpdates> statusUpdates = new ArrayList<>();
	  Connection conn = DatabaseUtility.connect();
	  PreparedStatement pstmt = null;
	  ResultSet rs = null;
	  String sql = "SELECT * FROM complaint_status_updates WHERE fk_complaint_id = ? ORDER BY update_timestamp ASC";
	  try {
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, complaintId);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
	      ComplaintStatusUpdates update = new ComplaintStatusUpdates();
	      update.setId(rs.getLong("id"));
	      update.setUpdateTimestamp(rs.getTimestamp("update_timestamp"));
	      update.setComplaintStatus(ComplaintStatus.valueOf(rs.getString("complaint_status")));
	      update.setReasonForChange(rs.getString("reason_for_change"));
	      update.setChangedByRole(rs.getString("changed_by_role"));
	      // Assuming Complaint, InvestigationRecords, and StaffMembers linking is handled within their respective setters
	      statusUpdates.add(update);
	    }
	  } catch (SQLException e) {
	    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error fetching complaint status updates by ID", e);
	  } finally {
	    DatabaseUtility.closeQuietly(rs);
	    DatabaseUtility.closeQuietly(pstmt);
	    DatabaseUtility.disconnect(conn);
	  }
	  return statusUpdates;
	}
	
	/*
	 This method logs each status update of a complaint, capturing critical details for auditing and tracking.
	 Used in sections: Complaint Registration Dashboard, Initial Assessment Workspace, Investigation Module Page.
	*/
	public boolean logStatusUpdate(String complaintId, Timestamp updateTimestamp, ComplaintStatusUpdates.ComplaintStatus complaintStatus, String reasonForChange, String changedByRole) {
	    Connection conn = DatabaseUtility.connect();
	    String query = "INSERT INTO complaint_status_updates (complaint_id, update_timestamp, complaint_status, reason_for_change, changed_by_role) VALUES (?, ?, CAST(? AS complaint_status), ?, ?)";
	    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, complaintId);
	        pstmt.setTimestamp(2, updateTimestamp);
	        pstmt.setString(3, complaintStatus.toString());
	        pstmt.setString(4, reasonForChange);
	        pstmt.setString(5, changedByRole);
	
	        int affectedRows = pstmt.executeUpdate();
	        DatabaseUtility.disconnect(conn);
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        DatabaseUtility.disconnect(conn);
	        return false;
	    }
	}
}
