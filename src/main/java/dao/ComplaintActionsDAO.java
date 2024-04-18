package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class ComplaintActionsDAO {

	
	/**
	 * Fetches a list of actions taken on a specific complaint, supporting pagination.
	 * @param complaintId The unique ID of the complaint for which actions are being retrieved.
	 * @param limit The maximum number of actions to return.
	 * @param offset The offset from where to start fetching actions.
	 * @return A list of ComplaintActions corresponding to the specified complaint ID.
	 */
	public List<ComplaintActions> fetchComplaintActions(int complaintId, int limit, int offset) {
	    List<ComplaintActions> actions = new ArrayList<>();
	    Connection conn = DatabaseUtility.connect();
	    try {
	        String sql = "SELECT * FROM complaint_actions WHERE fk_complaint_id = ? LIMIT ? OFFSET ?";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, complaintId);
	            pstmt.setInt(2, limit);
	            pstmt.setInt(3, offset);
	
	            try (ResultSet rs = pstmt.executeQuery()) {
	                while (rs.next()) {
	                    ComplaintActions action = new ComplaintActions();
	                    action.setId(rs.getInt("id"));
	                    action.setActionType(rs.getString("action_type"));
	                    action.setActionDate(rs.getTimestamp("action_date"));
	                    action.setActionDescription(rs.getString("action_description"));
	                    action.setComplaintStatus(ComplaintStatus.valueOf(rs.getString("complaint_status")));
	                    // Assuming Complaint object is correctly instantiated
	                    actions.add(action);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ComplaintActionsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return actions;
	}
	
	
	/**
	 * Records an action taken on a specific complaint in the database.
	 * @param actionType Describes the type of action taken on the complaint.
	 * @param actionDate The exact time and date when the action was performed.
	 * @param actionDescription A detailed description of the action taken.
	 * @param complaintStatus The status of the complaint after the action has been taken.
	 * @param fkComplaint The specific complaint record the action pertains to.
	 * @return boolean indicating the success or failure of the action record creation.
	 */
	public boolean createComplaintAction(String actionType, Timestamp actionDate, String actionDescription, ComplaintStatus complaintStatus, Complaints fkComplaint) {
	  Connection conn = DatabaseUtility.connect();
	  PreparedStatement pstmt = null;
	  boolean success = false;
	  try {
	    String sql = "INSERT INTO complaint_actions (action_type, action_date, action_description, complaint_status, fk_complaint_id) VALUES (?, ?, ?, ?, ?)";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, actionType);
	    pstmt.setTimestamp(2, actionDate);
	    pstmt.setString(3, actionDescription);
	    pstmt.setString(4, complaintStatus.name());
	    pstmt.setInt(5, fkComplaint.getId());
	    int affectedRows = pstmt.executeUpdate();
	    if (affectedRows > 0) {
	      success = true;
	    }
	  } catch (SQLException e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error creating complaint action", e);
	  } finally {
	    DatabaseUtility.close(pstmt);
	    DatabaseUtility.disconnect(conn);
	  }
	  return success;
	}
	
	/**
	 * Updates the details and outcome of actions taken on a complaint.
	 * Used in the 'Review and Closure Module' to update the details and outcome of actions taken on a complaint before closure.
	 *
	 * @param id The unique identifier of the complaint action to update.
	 * @param actionDescription Updated description of the action.
	 * @param complaintStatus Updated status of the complaint following the action.
	 * @return boolean indicating if the update was successful.
	 */
	public boolean updateComplaintAction(int id, String actionDescription, ComplaintStatus complaintStatus) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String updateSQL = "UPDATE complaint_actions SET action_description = ?, complaint_status = ? WHERE id = ?;";
	
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(updateSQL);
	        pstmt.setString(1, actionDescription);
	        pstmt.setString(2, complaintStatus.name());
	        pstmt.setInt(3, id);
	
	        int rowsAffected = pstmt.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try {
	            if (pstmt != null) { pstmt.close(); }
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
	        }
	    }
	}
}
