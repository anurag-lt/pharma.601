package dao;


import model.AuditLogs;
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
import model.Documents;
import java.util.Map;
import java.util.Map.Entry;
import model.Documents;



public class AuditLogsDAO {

	
	/**
	 * Fetches a list of documents based on the provided document type, complaint ID, with sorting and pagination capabilities.
	 * @param documentTypeFilter A string specifying type of documents to filter by.
	 * @param complaintIdFilter An integer for the complaint ID to filter by.
	 * @param sortBy A string specifying column name to sort by.
	 * @param limit An integer defining the maximum number of documents to return.
	 * @param offset An integer specifying the offset from where to start fetching documents.
	 * @return List<Documents> A list of documents meeting the specified criteria.
	 */
	public List<Documents> fetchDocuments(String documentTypeFilter, int complaintIdFilter, String sortBy, int limit, int offset) {
	    List<Documents> documents = new ArrayList<>();
	    String sql = "SELECT * FROM audit_logs WHERE document_type LIKE ? AND fk_complaint_id = ? ORDER BY " + sortBy + " LIMIT ? OFFSET ?;";
	    Connection conn = DatabaseUtility.connect();
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, documentTypeFilter);
	        pstmt.setInt(2, complaintIdFilter);
	        pstmt.setInt(3, limit);
	        pstmt.setInt(4, offset);
	
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Documents document = new Documents();
	                // Set properties from ResultSet to Documents object
	                documents.add(document);
	            }
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	
	    return documents;
	}
	
	/**
	 * Updates the access permissions for a single document.
	 * @param documentId An integer identifying the document to update permissions for.
	 * @param permissions A map where each key is a user role or ID string and each value is a boolean indicating if that role or user has access to the document.
	 * @return boolean indicating success or failure of the update operation.
	 */
	public boolean updateDocumentPermissions(int documentId, Map<String, Boolean> permissions) {
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    boolean updateSuccess = true;
	    try {
	        String sql = "UPDATE document_permissions SET access = ? WHERE document_id = ? AND user_role = ?";
	    
	        for (Entry<String, Boolean> entry : permissions.entrySet()) {
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setBoolean(1, entry.getValue());
	            pstmt.setInt(2, documentId);
	            pstmt.setString(3, entry.getKey());
	            int rowsAffected = pstmt.executeUpdate();
	            if (rowsAffected == 0) {
	                updateSuccess = false;
	                break;
	            }
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        updateSuccess = false;
	    } finally {
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	            }
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	    return updateSuccess;
	}
	
	/**
	 * Retrieves detailed information about a specific document based on its ID. This method supports viewing or editing document permissions.
	 *
	 * @param documentId An integer identifying the document to be retrieved.
	 * @return Documents object containing detailed information about the document.
	 */
	public Documents fetchDocumentById(int documentId) {
	    Documents document = null;
	    String sql = "SELECT id, document_name, document_type, document_name, user_role, fk_document_id FROM audit_logs WHERE id = ?;";
	    try (Connection conn = DatabaseUtility.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, documentId);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            document = new Documents();
	            document.setId(rs.getInt("id"));
	            document.setDocumentName(rs.getString("document_name"));
	            document.setDocumentType(Documents.DocumentTypes.valueOf(rs.getString("document_type")));
	            document.setUserRole(rs.getString("user_role"));
	            // Assuming 'fk_document_id' is a placeholder for linking to the documents table
	            document.setFkDocumentId(new Documents(rs.getInt("fk_document_id")));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(AuditLogs.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return document;
	}
	
	
	/**
	 * Deletes a specific document from the repository.
	 * @param documentId An integer identifying the document to be deleted.
	 * @return boolean indicating success or failure of the deletion operation.
	 */
	public boolean deleteDocument(int documentId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean isDeleted = false;
	    String sql = "DELETE FROM documents WHERE id = ?;";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, documentId);
	
	        int affectedRows = pstmt.executeUpdate();
	        isDeleted = affectedRows > 0;
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) { /* Ignored */ }
	        DatabaseUtility.disconnect(conn);
	    }
	    return isDeleted;
	}
	
	/**
	 * Logs an access event to a document in the system.
	 * @param userId A string representing the unique identifier of the user who accessed the document.
	 * @param documentId A string that uniquely identifies the document that was accessed.
	 * @param accessTime A timestamp marking the date and time when the access occurred.
	 */
	public void logDocumentAccess(String userId, String documentId, Timestamp accessTime) {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO audit_logs (user_id, document_id, action_date, action_type, action_time) VALUES (?, ?, ?, ?, ?)";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, userId);
	        ps.setString(2, documentId);
	        ps.setTimestamp(3, accessTime);
	        ps.setString(4, 'DOCUMENT_VIEWED');
	        ps.setTimestamp(5, accessTime);
	        int affectedRows = ps.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Creating log failed, no rows affected.");
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        if (ps != null) {
	            try { ps.close(); } catch (SQLException e) { /* ignored */ }
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	}
}
