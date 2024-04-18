package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.Optional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class InvestigationRecordsDAO {

	
	/**
	 * Creates a new investigation record in the database.
	 * @param investigationSummary A summary of the findings from the investigation process.
	 * @param investigatorNotes Detailed notes and observations made by the investigator during the investigation process.
	 * @param conclusion The final status of the investigation.
	 * @param evidenceDocumentation Optional file(s) containing evidence collected during the investigation.
	 * @param fkComplaint The linked complaint object that this investigation record pertains to.
	 * @return boolean true if the record was successfully created, false otherwise.
	 */
	public boolean createInvestigationRecord(String investigationSummary, String investigatorNotes, ComplaintStatus conclusion, Optional<File> evidenceDocumentation, Complaints fkComplaint) {
	  Connection conn = DatabaseUtility.connect();
	  PreparedStatement pstmt = null;
	  try {
	    String sql = "INSERT INTO investigation_records (investigation_summary, investigator_notes, investigation_conclusion, fk_complaint_id) VALUES (?, ?, ?, ?)";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, investigationSummary);
	    pstmt.setString(2, investigatorNotes);
	    pstmt.setString(3, conclusion.toString());
	    pstmt.setLong(4, fkComplaint.getId());
	
	    int affectedRows = pstmt.executeUpdate();
	    return affectedRows > 0;
	  } catch (SQLException e) {
	    Logger.getLogger(InvestigationRecordsDAO.class.getName()).log(Level.SEVERE, null, e);
	    return false;
	  } finally {
	    DatabaseUtility.disconnect(conn);
	    if (pstmt != null) {
	      try {
	        pstmt.close();
	      } catch (SQLException e) {
	        Logger.getLogger(InvestigationRecordsDAO.class.getName()).log(Level.SEVERE, null, e);
	      }
	    }
	  }
	}
	
	/**
	 * Fetches a list of complaints based on their investigation status for display in the tracking table.
	 * @param status The status of the investigation records to filter by (e.g., Pending, In-Progress, Completed).
	 * @param limit The maximum number of investigation records to return.
	 * @param offset The offset from where to start fetching the investigation records.
	 * @return List<InvestigationRecords> A list of filtered investigation records.
	 */
	public List<InvestigationRecords> fetchInvestigationsByStatus(String status, int limit, int offset) {
	    List<InvestigationRecords> records = new ArrayList<>();
	    Connection conn = DatabaseUtility.connect();
	    try {
	        String sql = "SELECT * FROM investigation_records WHERE investigation_conclusion = ? ORDER BY investigation_date DESC LIMIT ? OFFSET ?;";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, status);
	            stmt.setInt(2, limit);
	            stmt.setInt(3, offset);
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    InvestigationRecords record = new InvestigationRecords();
	                    record.setId(rs.getLong("id"));
	                    record.setInvestigationSummary(rs.getString("investigation_summary"));
	                    record.setInvestigatorNotes(rs.getString("investigator_notes"));
	                    record.setInvestigationConclusion(ComplaintStatus.valueOf(rs.getString("investigation_conclusion")));
	                    record.setInvestigationDate(rs.getDate("investigation_date"));
	                    record.setConclusionRemarks(rs.getString("conclusion_remarks"));
	                    record.setInvestigationDuration(rs.getInt("investigation_duration"));
	                    record.setResolvedBy(rs.getString("resolved_by"));
	                    // Assuming the foreign key relationship is handled within the setter.
	                    records.add(record);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(InvestigationRecordsDAO.class.getName()).log(Level.SEVERE, "Error fetching investigation records by status", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return records;
	}
	
	/**
	 * Retrieves detailed information about a specific investigation for collaboration purposes.
	 * @param id The unique identifier of the investigation record to retrieve.
	 * @return an InvestigationRecords object containing the investigation details.
	 */
	public InvestigationRecords findInvestigationDetailsById(Long id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    InvestigationRecords investigationRecord = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "SELECT * FROM investigation_records WHERE id = ?;";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, id);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            investigationRecord = new InvestigationRecords();
	            investigationRecord.setId(rs.getLong("id"));
	            investigationRecord.setInvestigationSummary(rs.getString("investigation_summary"));
	            investigationRecord.setInvestigatorNotes(rs.getString("investigator_notes"));
	            investigationRecord.setInvestigationConclusion(ComplaintStatus.valueOf(rs.getString("investigation_conclusion")));
	            investigationRecord.setInvestigationDate(rs.getDate("investigation_date"));
	            investigationRecord.setConclusionRemarks(rs.getString("conclusion_remarks"));
	            investigationRecord.setInvestigationDuration(rs.getInt("investigation_duration"));
	            investigationRecord.setResolvedBy(rs.getString("resolved_by"));
	            // Assuming fk_complaint_id column exists with complaint details to be set similarly
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error finding investigation details by ID", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
	        try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
	    }
	    return investigationRecord;
	}
	
	/*
	 Updates an existing investigation record in the database with new findings or evidence.
	 @param id The unique identifier of the investigation record to be updated.
	 @param investigationSummary Updated summary of the findings from the investigation process.
	 @param investigatorNotes Updated detailed notes and observations made by the investigator.
	 @param conclusion Updated final status of the investigation.
	 @param evidenceDocumentation Updated file(s) containing evidence collected during the investigation.
	*/
	public boolean updateInvestigationRecord(Long id, String investigationSummary, String investigatorNotes, ComplaintStatus conclusion, Optional<File> evidenceDocumentation) {
	    Connection conn = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE investigation_records SET investigation_summary=?, investigator_notes=?, investigation_conclusion=?, WHERE id=?";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, investigationSummary);
	            pstmt.setString(2, investigatorNotes);
	            pstmt.setString(3, conclusion.name());
	            // No file upload implementation in SQL, evidenceDocumentation might be handled separately
	            pstmt.setLong(4, id);
	            int affectedRows = pstmt.executeUpdate();
	            return affectedRows > 0;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating investigation record", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return false;
	}
}
