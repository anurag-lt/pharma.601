package dao;


import model.ComplaintAssignments;
import utils.DatabaseUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class ComplaintAssignmentsDAO {

	
	/**
	 * Fetches a paginated list of complaint assignments based on status filter.
	 * Used to display assignments in the New Complaints Table and Complaint Selection for further action.
	 * @param statusFilter Filter assignments based on their current status.
	 * @param limit Maximum number of complaint assignments to return.
	 * @param offset Number of complaint assignments to skip before starting to return results.
	 * @return List<ComplaintAssignments> A list of filtered complaint assignments.
	 */
	public List<ComplaintAssignments> fetchAllComplaintAssignments(String statusFilter, int limit, int offset) {
	    List<ComplaintAssignments> assignments = new ArrayList<>();
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM complaint_assignments WHERE assignment_status = ? LIMIT ? OFFSET ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, statusFilter);
	        pstmt.setInt(2, limit);
	        pstmt.setInt(3, offset);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            ComplaintAssignments assignment = new ComplaintAssignments();
	            assignment.setId(rs.getInt("id"));
	            assignment.setAssignmentDate(rs.getDate("assignment_date"));
	            // Map ENUM values appropriately
	            // assignment.setComplaintPriority(ComplaintPriorities.valueOf(rs.getString("complaint_priority")));
	            assignment.setAssignmentStatus(rs.getString("assignment_status"));
	            assignment.setCompletionDate(rs.getDate("completion_date"));
	            // Set foreign keys or related entities if needed
	            assignments.add(assignment);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try { if (rs != null) rs.close(); if (pstmt != null) pstmt.close(); } catch (SQLException e) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e); }
	    }
	    return assignments;
	}
	
	/**
	 * Updates the status of a specific complaint assignment.
	 * It is used within the dashboard to manage the workflow of complaint resolution.
	 * @param assignmentId The unique identifier of the complaint assignment to be updated.
	 * @param newStatus The new status to update the complaint assignment to.
	 */
	public boolean updateComplaintAssignmentStatus(Integer assignmentId, String newStatus) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String updateSql = "UPDATE complaint_assignments SET assignment_status = ? WHERE id = ?;";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(updateSql);
	        pstmt.setString(1, newStatus);
	        pstmt.setInt(2, assignmentId);
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignored */ }
	        DatabaseUtility.disconnect(conn);
	    }
	}
	
	public boolean assignComplaintToStaffMember(Integer complaintId, Integer staffMemberId, ComplaintPriorities priority, String status) {
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    String sql = "INSERT INTO complaint_assignments (fk_complaint_id, fk_staff_member_id, complaint_priority, assignment_status, assignment_date) VALUES (?, ?, CAST(? AS complaint_priorities), ?, CURRENT_DATE)";
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, complaintId);
	        pstmt.setInt(2, staffMemberId);
	        pstmt.setString(3, priority.name());
	        pstmt.setString(4, status);
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException ex) {
	        Logger.getLogger(ComplaintAssignments.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	        } catch (SQLException ex) {
	            Logger.getLogger(ComplaintAssignments.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    return false;
	}
	
	/**
	 * Retrieves all complaint assignments associated with a specified staff member.
	 * Useful for displaying relevant assignments to staff during action plan creation or resolution efforts.
	 * @param staffMemberId The unique identifier of the staff member whose assignments are being fetched.
	 * @return A list of ComplaintAssignments objects that are assigned to the specified staff member.
	 */
	public List<ComplaintAssignments> fetchComplaintAssignmentsForStaff(Integer staffMemberId) {
	    List<ComplaintAssignments> assignments = new ArrayList<>();
	    Connection conn = DatabaseUtility.connect();
	    String sql = "SELECT * FROM complaint_assignments WHERE fk_staff_member_id = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, staffMemberId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                ComplaintAssignments assignment = new ComplaintAssignments();
	                assignment.setId(rs.getInt("id"));
	                assignment.setAssignmentDate(rs.getDate("assignment_date"));
	                assignment.setAssignmentStatus(rs.getString("assignment_status"));
	                assignment.setCompletionDate(rs.getDate("completion_date"));
	                // Assuming Complaint and StaffMember objects are set elsewhere or further 
	                // implementation details required for setting up the whole object
	                assignments.add(assignment);
	            }
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return assignments;
	}
	
	/**
	 * Updates the completion date of a specific complaint assignment.
	 * @param assignmentId The unique identifier of the complaint assignment to be updated.
	 * @param completionDate The date when the complaint handling or investigation is completed.
	 */
	public boolean updateComplaintAssignmentCompletionDate(Integer assignmentId, Date completionDate) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = "UPDATE complaint_assignments SET completion_date = ? WHERE id = ?;";
	
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	
	        // Convert java.util.Date to java.sql.Date
	        java.sql.Date sqlCompletionDate = new java.sql.Date(completionDate.getTime());
	
	        pstmt.setDate(1, sqlCompletionDate);
	        pstmt.setInt(2, assignmentId);
	
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            return true;
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException ex) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	    return false;
	}
}
