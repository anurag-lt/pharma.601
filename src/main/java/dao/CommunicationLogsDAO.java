package dao;


import model.CommunicationLogs;
import utils.DatabaseUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.Statement;


public class CommunicationLogsDAO {

	
	/**
	 * Saves a log of communications associated with complaints.
	 * @param sentTime The exact timestamp when the communication was sent to the complainant.
	 * @param communicationStatus The status of the communication (sent, failed, draft, resent).
	 * @param templateUsed The name or identifier of the template used for the communication.
	 * @param linkToMessage A hyperlink or reference to the full content of the communication sent.
	 * @param fkComplaintId The ID of the complaint associated with this communication log entry.
	 * @param fkTemplateId The ID of the communication template used for this communication.
	 * @return boolean Returns true if the log entry was successfully saved.
	 */
	public boolean saveCommunicationLog(Timestamp sentTime, CommunicationLogs.CommunicationStatus communicationStatus, String templateUsed, String linkToMessage, Long fkComplaintId, Long fkTemplateId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean result = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO communication_logs (sent_time, communication_status, template_used, link_to_message, fk_complaint_id, fk_template_id) VALUES (?, ?, ?, ?, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setTimestamp(1, sentTime);
	        pstmt.setString(2, communicationStatus.name());
	        pstmt.setString(3, templateUsed);
	        pstmt.setString(4, linkToMessage);
	        pstmt.setLong(5, fkComplaintId);
	        pstmt.setLong(6, fkTemplateId);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            result = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error inserting communication log", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return result;
	}
	
	
	/**
	 * Retrieves all communication logs associated with a specific complaint ID.
	 * @param fkComplaintId The ID of the complaint for which to retrieve communication logs.
	 * @return A list of communication log entries.
	 */
	public List<CommunicationLogs> fetchCommunicationLogsByComplaintId(Long fkComplaintId) {
	    List<CommunicationLogs> communicationLogsList = new ArrayList<>();
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = "SELECT * FROM communication_logs WHERE fk_complaint_id = ?;";
	
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, fkComplaintId);
	        rs = pstmt.executeQuery();
	
	        while (rs.next()) {
	            CommunicationLogs log = new CommunicationLogs();
	            log.setId(rs.getLong("id"));
	            log.setSentTime(rs.getTimestamp("sent_time"));
	            log.setCommunicationStatus(CommunicationLogs.CommunicationStatus.valueOf(rs.getString("communication_status")));
	            log.setTemplateUsed(rs.getString("template_used"));
	            log.setLinkToMessage(rs.getString("link_to_message"));
	            // Assuming the fk_complaint_id and fk_template_id are being properly set elsewhere or are not needed for this retrieval
	            communicationLogsList.add(log);
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* Ignored */ }
	        if (rs != null) try { rs.close(); } catch (SQLException e) { /* Ignored */ }
	    }
	    return communicationLogsList;
	}
}
