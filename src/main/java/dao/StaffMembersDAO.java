package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class StaffMembersDAO {

	
	/**
	 * Creates a new staff member record or updates an existing one based on the provided email address.
	 * @param name The full name of the staff member.
	 * @param role The role of the staff member within the organization.
	 * @param email The email address of the staff member.
	 * @param contactNumber The phone number of the staff member.
	 * @param joinDate The date when the staff member joined the organization.
	 * @param activeStatus Indicates the current active status of the staff member.
	 * @return boolean indicating the success of the operation.
	 */
	public boolean createOrUpdateStaffMember(String name, String role, String email, String contactNumber, Date joinDate, boolean activeStatus) {
	    Connection conn = DatabaseUtility.connect();
	    try {
	        String queryCheck = "SELECT count(*) FROM staff_members WHERE email = ?";
	        PreparedStatement psCheck = conn.prepareStatement(queryCheck);
	        psCheck.setString(1, email);
	        ResultSet rs = psCheck.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            if (count > 0) {
	                // Update existing staff member
	                String queryUpdate = "UPDATE staff_members SET name=?, role=?, contact_number=?, join_date=?, active_status=? WHERE email=?";
	                PreparedStatement psUpdate = conn.prepareStatement(queryUpdate);
	                psUpdate.setString(1, name);
	                psUpdate.setString(2, role);
	                psUpdate.setString(3, contactNumber);
	                psUpdate.setDate(4, new java.sql.Date(joinDate.getTime()));
	                psUpdate.setBoolean(5, activeStatus);
	                psUpdate.setString(6, email);
	                psUpdate.executeUpdate();
	            } else {
	                // Insert new staff member
	                String queryInsert = "INSERT INTO staff_members (name, role, email, contact_number, join_date, active_status) VALUES (?, ?, ?, ?, ?, ?)";
	                PreparedStatement psInsert = conn.prepareStatement(queryInsert);
	                psInsert.setString(1, name);
	                psInsert.setString(2, role);
	                psInsert.setString(3, email);
	                psInsert.setString(4, contactNumber);
	                psInsert.setDate(5, new java.sql.Date(joinDate.getTime()));
	                psInsert.setBoolean(6, activeStatus);
	                psInsert.executeUpdate();
	            }
	        }
	        return true;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error executing createOrUpdateStaffMember", e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	}
	
	/**
	 * Fetches staff member details from the database by their email address.
	 * Used in 'Complaint Registration Dashboard' and 'Complaint Communication Interface' sections
	 * to retrieve staff member details by their email for assigning complaints and sending communications.
	 *
	 * @param email The email address of the staff member.
	 * @return A StaffMembers object containing the staff member's details or null if not found.
	 */
	public StaffMembers fetchStaffMemberByEmail(String email) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    StaffMembers staffMember = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String query = "SELECT * FROM staff_members WHERE email = ?";
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, email);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            staffMember = new StaffMembers();
	            staffMember.setId(rs.getInt("id"));
	            staffMember.setName(rs.getString("name"));
	            staffMember.setRole(rs.getString("role"));
	            staffMember.setEmail(rs.getString("email"));
	            staffMember.setContactNumber(rs.getString("contact_number"));
	            staffMember.setJoinDate(rs.getDate("join_date"));
	            staffMember.setActiveStatus(rs.getBoolean("active_status"));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try {
	            if (pstmt != null) pstmt.close();
	            if (rs != null) rs.close();
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        }
	    }
	    return staffMember;
	}
	
	/**
	 * Updates the active status of a staff member in the database.
	 * @param id The unique identifier of the staff member to update.
	 * @param activeStatus The new active status for the staff member.
	 * @return true if the update was successful, false otherwise.
	 */
	public boolean updateStaffMemberStatus(int id, boolean activeStatus) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean updateStatus = false;
	
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE staff_members SET active_status = ? WHERE id = ?";
	
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setBoolean(1, activeStatus);
	        pstmt.setInt(2, id);
	
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            updateStatus = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	    return updateStatus;
	}
	
	/**
	 * Fetches all active staff members from the database.
	 * Used in the 'Complaint Assignments' section for assigning complaints.
	 * @return A list of StaffMembers objects representing all active staff members.
	 */
	public List<StaffMembers> fetchAllActiveStaffMembers() {
	    List<StaffMembers> staffMembers = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "SELECT * FROM staff_members WHERE active_status = true;";
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            StaffMembers member = new StaffMembers();
	            member.setId(rs.getInt("id"));
	            member.setName(rs.getString("name"));
	            member.setRole(rs.getString("role"));
	            member.setEmail(rs.getString("email"));
	            member.setContactNumber(rs.getString("contact_number"));
	            member.setJoinDate(rs.getDate("join_date"));
	            member.setActiveStatus(rs.getBoolean("active_status"));
	            staffMembers.add(member);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(StaffMembersDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { /* Ignored */ } }
	        if (rs != null) { try { rs.close(); } catch (SQLException e) { /* Ignored */ } }
	    }
	    return staffMembers;
	}
}
