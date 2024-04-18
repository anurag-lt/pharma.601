package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class CapasDAO {

	
	/*
	 * Creates a new corrective action plan record in the database.
	 * @param actionDescription A detailed description of the corrective action needed.
	 * @param dueDate The target completion date for the CAPA.
	 * @param capaStatus The current stage of the CAPA, using values from the CapaStatuses enum.
	 * @param staffMemberId The ID of the staff member responsible for implementing the CAPA.
	 * @param complaintId The ID of the complaint to which this CAPA is linked.
	 * @return true if the CAPA was successfully created.
	 */
	public boolean createCorrectiveActionPlan(String actionDescription, Date dueDate, Capas.CapaStatuses capaStatus, int staffMemberId, int complaintId) {
	    Connection conn = DatabaseUtility.connect();
	    try {
	        String sql = "INSERT INTO capas (action_description, due_date, capa_status, fk_staff_member_id, fk_complaint_id) VALUES (?, ?, ?::capa_statuses, ?, ?)";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, actionDescription);
	            pstmt.setDate(2, dueDate);
	            pstmt.setString(3, capaStatus.name());
	            pstmt.setInt(4, staffMemberId);
	            pstmt.setInt(5, complaintId);
	            int affectedRows = pstmt.executeUpdate();
	            return affectedRows > 0;
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return false;
	}
	
	/*
	 Used in the 'Edit CAPA Details' modal of the 'Action Plan and Resolution' page to fetch details for a specific CAPA by its ID.
	 */
	public Capas fetchCapaDetailsById(int capaId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Capas capa = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "SELECT * FROM capas WHERE id = ?;";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, capaId);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            capa = new Capas();
	            capa.setId(rs.getInt("id"));
	            capa.setActionDescription(rs.getString("action_description"));
	            capa.setDueDate(rs.getDate("due_date"));
	            capa.setCapaStatus(Capas.CapaStatuses.valueOf(rs.getString("capa_status")));
	            capa.setStaffMember(new StaffMembers()); // Assuming you have a method to set StaffMember
	            capa.setComplaint(new Complaints()); // Assuming you have a method to set Complaint
	            // Set staff member and complaint details if necessary
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(CapasDAO.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) { try { pstmt.close(); } catch (SQLException ex) { /* Logger here */ } }
	        if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* Logger here */ } }
	    }
	    return capa;
	}
	
	
	/**
	 * Updates details for a specific CAPA by its ID.
	 *
	 * @param actionDescription A detailed description of the corrective action needed.
	 * @param dueDate The target completion date for the CAPA.
	 * @param capaStatus The updated stage/status of the CAPA, using values from the CapaStatuses enum.
	 * @param staffMemberId The updated ID of the staff member responsible for the CAPA.
	 * @param capaId The ID of the CAPA to update.
	 * @return Returns true if update is successful; otherwise, false.
	 */
	public boolean updateCapaDetails(String actionDescription, Date dueDate, Capas.CapaStatuses capaStatus, int staffMemberId, int capaId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean updateStatus = false;
	    String sql = "UPDATE capas SET action_description = ?, due_date = ?, capa_status = ::capa_statuses, fk_staff_member_id = ? WHERE id = ?;";
	
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, actionDescription);
	        pstmt.setDate(2, dueDate);
	        pstmt.setString(3, capaStatus.name());
	        pstmt.setInt(4, staffMemberId);
	        pstmt.setInt(5, capaId);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            updateStatus = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating CAPA details", e);
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* Ignored */ }
	        DatabaseUtility.disconnect(conn);
	    }
	    return updateStatus;
	}
	
	
	/**
	 * Fetches a list of CAPA records based on status filters for display in a monitoring table.
	 * @param statusFilter Filter to select CAPAs based on their current status.
	 * @param limit The maximum number of CAPA records to return in one call.
	 * @param offset The offset index from where to start fetching the CAPA records.
	 * @return List of Capas objects.
	 */
	public List<Capas> fetchAllCapas(String statusFilter, int limit, int offset) {
	    List<Capas> capasList = new ArrayList<>();
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String sql = "SELECT * FROM capas WHERE capa_status = ? ORDER BY id LIMIT ? OFFSET ?";
	    try {
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, statusFilter);
	        ps.setInt(2, limit);
	        ps.setInt(3, offset);
	        rs = ps.executeQuery();
	        while (rs.next()) {
	            Capas capas = new Capas();
	            capas.setId(rs.getInt("id"));
	            capas.setActionDescription(rs.getString("action_description"));
	            capas.setDueDate(rs.getDate("due_date"));
	            capas.setCapaStatus(Capas.CapaStatuses.valueOf(rs.getString("capa_status")));
	            // Assuming staff_member_id and complaint_id are to be manually set after retrieval
	            capasList.add(capas);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(CapasDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (ps != null) {
	            try { ps.close(); } catch (SQLException e) { /* Ignored */ }
	        }
	        if (rs != null) {
	            try { rs.close(); } catch (SQLException e) { /* Ignored */ }
	        }
	    }
	    return capasList;
	}
}
