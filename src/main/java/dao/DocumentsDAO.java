package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class DocumentsDAO {

	
	/**
	 * Fetches a list of documents based on the provided filters and pagination options.
	 * @param documentTypeFilter Specifies the type of documents to filter by.
	 * @param complaintIdFilter The complaint ID to filter documents by.
	 * @param sortBy Specifies the column name to sort by.
	 * @param limit Defines the maximum number of documents to return.
	 * @param offset Specifies the offset from where to start fetching documents.
	 * @return A list of Documents matching the criteria.
	 */
	public List<Documents> fetchDocuments(String documentTypeFilter, int complaintIdFilter, String sortBy, int limit, int offset) {
	    List<Documents> documents = new ArrayList<>();
	    Connection conn = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String query = "SELECT * FROM documents WHERE 1=1 ";
	        if (!documentTypeFilter.isEmpty()) {
	            query += "AND document_type = '" + documentTypeFilter + "' ";
	        }
	        if (complaintIdFilter != 0) {
	            query += "AND fk_complaint_id = " + complaintIdFilter + " ";
	        }
	        if (!sortBy.isEmpty()) {
	            query += "ORDER BY " + sortBy + " ";
	        }
	        query += "LIMIT " + limit + " OFFSET " + offset + ";";
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	            Documents document = new Documents();
	            document.setId(rs.getLong("id"));
	            document.setDocumentName(rs.getString("document_name"));
	            document.setUploadTime(rs.getTimestamp("upload_time"));
	            document.setFileSize(rs.getBigDecimal("file_size"));
	            document.setFileFormat(rs.getString("file_format"));
	            document.setContentHash(rs.getString("content_hash"));
	            documents.add(document);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(DocumentsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return documents;
	}
	
	/**
	 * Updates the access permissions for a single document.
	 * @param documentId The unique identifier of the document to update permissions for.
	 * @param permissions A map where each key is a user role or ID string and each value is a boolean indicating if that role or user has access to the document.
	 * @return boolean indicating success or failure of the update operation.
	 */
	public boolean updateDocumentPermissions(int documentId, Map<String, Boolean> permissions) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = DatabaseUtility.connect();
	        // Dummy SQL for demonstration. This should be replaced with actual SQL query to update document permissions.
	        String sql = "UPDATE document_permissions SET access = ? WHERE document_id = ? AND user_role = ?;";
	        pstmt = conn.prepareStatement(sql);
	        for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
	            pstmt.setBoolean(1, entry.getValue());
	            pstmt.setInt(2, documentId);
	            pstmt.setString(3, entry.getKey());
	            pstmt.executeUpdate();
	        }
	        return true;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error updating document permissions", e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing PreparedStatement", e);
	            }
	        }
	    }
	}
	
	
	/**
	 * Retrieves detailed information about a single document based on its ID.
	 * Utilized in the 'Document Management Table' and 'Document Review Link' sections.
	 * @param documentId The unique identifier for the document.
	 * @return A Documents object containing detailed information about the document, or null if not found.
	 */
	public Documents fetchDocumentById(int documentId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Documents document = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String query = "SELECT * FROM documents WHERE id = ?;";
	        pstmt = conn.prepareStatement(query);
	        pstmt.setInt(1, documentId);
	
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            document = new Documents();
	            document.setId(rs.getLong("id"));
	            document.setDocumentName(rs.getString("document_name"));
	            document.setUploadTime(rs.getTimestamp("upload_time"));
	            document.setDocumentType(Documents.DocumentTypes.valueOf(rs.getString("document_type")));
	            document.setFileSize(rs.getBigDecimal("file_size"));
	            document.setFileFormat(rs.getString("file_format"));
	            document.setContentHash(rs.getString("content_hash"));
	            // Assuming Complaints ID is being fetched properly from another part of the system
	            // document.setFkComplaintId(...);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(DocumentsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { /* Ignored */ } }
	        if (rs != null) { try { rs.close(); } catch (SQLException e) { /* Ignored */ } }
	    }
	    return document;
	}
	
	/**
	 * Deletes a specific document from the repository.
	 * @param documentId The unique identifier for the document to be deleted.
	 * @return true if the document is successfully deleted, false otherwise.
	 */
	public boolean deleteDocument(int documentId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean result = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "DELETE FROM documents WHERE id = ?;";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, documentId);
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            result = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
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
	    return result;
	}
}
