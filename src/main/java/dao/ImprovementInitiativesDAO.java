package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class ImprovementInitiativesDAO {

	
	/*
	 * Creates a new improvement initiative in the database with the provided details.
	 * @param title The title of the improvement initiative.
	 * @param category The complaint category the initiative aims to address.
	 * @param startDate The official start date of the initiative.
	 * @param endDate The anticipated or actual end date of the initiative.
	 * @param outcome Narrative description of the anticipated results or impacts of the initiative.
	 * @param assignedToIds A delimited text field storing IDs of staff members assigned to the initiative.
	 * @return boolean true if the operation was successful, false otherwise.
	 */
	public boolean createImprovementInitiative(String title, TargetedComplaintCategory category, Date startDate, Date endDate, String outcome, String assignedToIds) {
	    Connection conn = DatabaseUtility.connect();
	    String sql = "INSERT INTO improvement_initiatives (initiative_title, targeted_complaint_category, start_date, end_date, expected_outcomes, assigned_to_ids) VALUES (?, ?, ?, ?, ?, ?)";
	
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, title);
	        pstmt.setString(2, category.name());
	        pstmt.setDate(3, new java.sql.Date(startDate.getTime()));
	        pstmt.setDate(4, new java.sql.Date(endDate.getTime()));
	        pstmt.setString(5, outcome);
	        pstmt.setString(6, assignedToIds);
	
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
	 * Retrieves all improvement initiatives for display.
	 * @return A list of all improvement initiatives.
	 */
	public List<ImprovementInitiatives> fetchAllImprovementInitiatives() {
	    List<ImprovementInitiatives> initiatives = new ArrayList<>();
	    String query = "SELECT * FROM improvement_initiatives";
	    try (Connection conn = DatabaseUtility.connect();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {
	        while (rs.next()) {
	            ImprovementInitiatives initiative = new ImprovementInitiatives();
	            initiative.setId(rs.getLong("id"));
	            initiative.setInitiativeTitle(rs.getString("initiative_title"));
	            initiative.setTargetedComplaintCategory(ImprovementInitiatives.TargetedComplaintCategory.valueOf(rs.getString("targeted_complaint_category")));
	            initiative.setStartDate(rs.getDate("start_date"));
	            initiative.setEndDate(rs.getDate("end_date"));
	            initiative.setExpectedOutcomes(rs.getString("expected_outcomes"));
	            initiative.setAssignedToIds(rs.getString("assigned_to_ids"));
	            initiatives.add(initiative);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ImprovementInitiativesDAO.class.getName()).log(Level.SEVERE, "Error fetching improvement initiatives", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return initiatives;
	}
	
	
	/**
	 * Updates the details of an existing improvement initiative.
	 * @param initiativeId The unique ID of the improvement initiative to be updated.
	 * @param updatedDetails The object containing the updated details of the initiative.
	 * @return boolean True if the update was successful, otherwise false.
	 */
	public boolean updateImprovementInitiative(long initiativeId, ImprovementInitiatives updatedDetails) {
	  Connection conn = null;
	  PreparedStatement pstmt = null;
	  boolean updateStatus = false;
	
	  try {
	    conn = DatabaseUtility.connect();
	    String sql = "UPDATE improvement_initiatives SET initiative_title = ?, targeted_complaint_category = ?, start_date = ?, end_date = ?, expected_outcomes = ?, assigned_to_ids = ? WHERE id = ?";
	
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, updatedDetails.getInitiativeTitle());
	    pstmt.setString(2, updatedDetails.getTargetedComplaintCategory().name());
	    pstmt.setDate(3, new java.sql.Date(updatedDetails.getStartDate().getTime()));
	    pstmt.setDate(4, new java.sql.Date(updatedDetails.getEndDate().getTime()));
	    pstmt.setString(5, updatedDetails.getExpectedOutcomes());
	    pstmt.setString(6, updatedDetails.getAssignedToIds());
	    pstmt.setLong(7, initiativeId);
	
	    int rowAffected = pstmt.executeUpdate();
	    if(rowAffected == 1) {
	      updateStatus = true;
	    }
	  } catch (SQLException e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error updating improvement initiative", e);
	  } finally {
	    DatabaseUtility.disconnect(conn);
	    try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex); }
	  }
	  return updateStatus;
	}
	
	/**
	 * Deletes an improvement initiative from the system.
	 * @param initiativeId Unique ID of the improvement initiative to be deleted.
	 */
	public boolean deleteImprovementInitiative(long initiativeId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean isSuccess = false;
	    String sql = "DELETE FROM improvement_initiatives WHERE id = ?;";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, initiativeId);
	
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            isSuccess = true;
	            Logger.getLogger(this.getClass().getName()).info("Improvement initiative deleted successfully.");
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error deleting improvement initiative", e);
	    } finally {
	        DatabaseUtility.closePreparedStatement(pstmt);
	        DatabaseUtility.disconnect(conn);
	    }
	    return isSuccess;
	}
}
