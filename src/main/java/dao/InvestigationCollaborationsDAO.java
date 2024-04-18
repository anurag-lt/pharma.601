package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.time.LocalDateTime;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class InvestigationCollaborationsDAO {

	
	/**
	 * Creates a collaboration request between staff members during an investigation.
	 * @param senderId The ID of the staff member initiating the collaboration.
	 * @param receiverId The ID of the staff member receiving the collaboration request.
	 * @param message The content of the collaboration request message.
	 * @param timestamp The time when the collaboration request is made.
	 * @return boolean Indicates whether the collaboration request was successfully created.
	 */
	public boolean createCollaborationRequest(String senderId, String receiverId, String message, LocalDateTime timestamp) {
	    Connection conn = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO investigation_collaborations (sender_id, receiver_id, message, request_date) VALUES (?, ?, ?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, senderId);
	        pstmt.setString(2, receiverId);
	        pstmt.setString(3, message);
	        pstmt.setTimestamp(4, Timestamp.valueOf(timestamp));
	        int affectedRows = pstmt.executeUpdate();
	        DatabaseUtility.disconnect(conn);
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(InvestigationCollaborationsDAO.class.getName()).log(Level.SEVERE, "Error creating collaboration request", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return false;
	}
	
	public boolean logCollaborationActivity(String investigationId, String activityDescription, LocalDateTime timestamp) {
	    Connection conn = DatabaseUtility.connect();
	    String sql = "INSERT INTO investigation_collaborations (fk_investigation_id, notes, request_date) VALUES (?, ?, ?);";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, investigationId);
	        pstmt.setString(2, activityDescription);
	        pstmt.setTimestamp(3, Timestamp.valueOf(timestamp));
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	}
	
	/**
	 * Fetches a list of team members associated with an investigation to facilitate collaboration.
	 * @param investigationId The unique ID of the investigation for which to fetch team members.
	 * @return A list of team member IDs involved in the investigation.
	 */
	public List<String> fetchTeamMembersByInvestigationId(String investigationId) {
	    Connection conn = DatabaseUtility.connect();
	    List<String> teamMemberIds = new ArrayList<>();
	    String sql = "SELECT fk_staff_member_id FROM investigation_collaborations WHERE fk_investigation_id = ?;";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, investigationId);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            teamMemberIds.add(rs.getString("fk_staff_member_id"));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error fetching team members by investigation ID", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return teamMemberIds;
	}
	
	/**
	 * Updates the status of an investigation after completing collaboration activities.
	 * @param investigationId Unique ID of the investigation to update status.
	 * @param newStatus The new status to be set for the investigation.
	 */
	public boolean updateInvestigationStatus(String investigationId, String newStatus) {
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    String sql = "UPDATE investigation_collaborations SET collaboration_status = ? WHERE id = ?";
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, newStatus);
	        pstmt.setString(2, investigationId);
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating investigation status", e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	        } catch (SQLException e) {
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing resources", e);
	        }
	    }
	}
}
