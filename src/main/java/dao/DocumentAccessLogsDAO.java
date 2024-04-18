package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;

public class DocumentAccessLogsDAO {

	
	
	/**
	 * Fetches a list of document access logs based on provided filters.
	 *
	 * @param startDateTime The start date and time to filter logs.
	 * @param endDateTime The end date and time to filter logs.
	 * @param userId The unique identifier of the staff member who accessed the document.
	 * @param documentId The unique identifier of the document that was accessed.
	 * @param limit The maximum number of log entries to return.
	 * @param offset The offset from where to start fetching the log entries.
	 * @return List<DocumentAccessLogs> The list of document access logs matching the filters.
	 */
	public List<DocumentAccessLogs> fetchDocumentAccessLogs(Timestamp startDateTime, Timestamp endDateTime, long userId, long documentId, int limit, int offset) {
	    List<DocumentAccessLogs> logs = new ArrayList<>();
	    String sql = "SELECT * FROM document_access_logs WHERE access_time >= ? AND access_time <= ? AND (fk_user_id = ? OR ? = 0) AND (fk_document_id = ? OR ? = 0) LIMIT ? OFFSET ?;";
	    try (Connection conn = DatabaseUtility.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setTimestamp(1, startDateTime);
	        pstmt.setTimestamp(2, endDateTime);
	        pstmt.setLong(3, userId);
	        pstmt.setLong(4, userId);
	        pstmt.setLong(5, documentId);
	        pstmt.setLong(6, documentId);
	        pstmt.setInt(7, limit);
	        pstmt.setInt(8, offset);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            DocumentAccessLogs log = new DocumentAccessLogs();
	            log.setId(rs.getLong("id"));
	            log.setAccessTime(rs.getTimestamp("access_time"));
	            log.setActionType(DocumentAccessLogs.ActionType.valueOf(rs.getString("action_type")));
	            log.setStaffMember(new StaffMembers());  // Placeholder for actual staff member retrieval
	            log.setDocument(new Documents());  // Placeholder for actual document retrieval
	            logs.add(log);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(DocumentAccessLogsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(null);
	    }
	    return logs;
	}
	
	public boolean logDocumentAccess(Timestamp accessTime, DocumentAccessLogs.ActionType actionType, long staffMemberId, long documentId) {
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    String insertStatement = "INSERT INTO document_access_logs (access_time, action_type, fk_user_id, fk_document_id) VALUES (?, ?::action_type, ?, ?)";
	
	    try {
	        pstmt = conn.prepareStatement(insertStatement);
	        pstmt.setTimestamp(1, accessTime);
	        pstmt.setString(2, actionType.name());
	        pstmt.setLong(3, staffMemberId);
	        pstmt.setLong(4, documentId);
	
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(DocumentAccessLogsDAO.class.getName()).log(Level.SEVERE, "Error logging document access", e);
	        return false;
	    } finally {
	        DatabaseUtility.closeConnection(conn, pstmt, null);
	    }
	}
}
