package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class ComplaintAttachmentsDAO {

	
	/**
	 * Stores the uploaded document in the system with metadata for future retrieval.
	 * This method is essential for ensuring that all relevant data and evidence related to complaints
	 * are maintained within the complaint record.
	 *
	 * @param documentName the name of the document being uploaded
	 * @param complaintId the unique identifier of the complaint to which the document is attached
	 * @param documentTypeId identifier for the type of document based on predefined list
	 * @param fileContent the content of the file being uploaded
	 * @return boolean indicating the success or failure of the document upload
	 */
	public boolean uploadDocument(String documentName, String complaintId, String documentTypeId, byte[] fileContent) {
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    String sql = "INSERT INTO complaint_attachments(attachment_name, file_type, upload_date, file_size, fk_complaint_id)"
	                + " VALUES(?, ?, CURRENT_DATE, ?, ?);";
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, documentName);
	        pstmt.setString(2, documentTypeId);
	        pstmt.setDouble(3, fileContent.length);
	        pstmt.setInt(4, Integer.parseInt(complaintId));
	        int rowsAffected = pstmt.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	}
	
	/**
	 * Fetches a paginated list of documents, supporting filtering by document type and association with a specific complaint,
	 * and sorting. This method enables efficient document retrieval and management.
	 *
	 * @param documentTypeFilter a String specifying the type of documents to filter by.
	 * @param complaintIdFilter an integer for the complaint ID to filter by.
	 * @param sortBy a String specifying column name to sort by.
	 * @param limit an integer defining the maximum number of documents to return.
	 * @param offset an integer specifying the offset from where to start fetching documents.
	 * @return a list of documents matching the criteria.
	 */
	public List<ComplaintAttachments> fetchDocuments(String documentTypeFilter, int complaintIdFilter, String sortBy, int limit, int offset) {
	    List<ComplaintAttachments> documents = new ArrayList<>();
	    Connection conn = DatabaseUtility.connect();
	    try {
	        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM complaint_attachments WHERE document_type LIKE ? AND fk_complaint_id = ? ORDER BY " + sortBy + " LIMIT ? OFFSET ?");
	        stmt.setString(1, '%' + documentTypeFilter + '%');
	        stmt.setInt(2, complaintIdFilter);
	        stmt.setInt(3, limit);
	        stmt.setInt(4, offset);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            ComplaintAttachments document = new ComplaintAttachments();
	            document.setId(rs.getInt("id"));
	            document.setAttachmentName(rs.getString("attachment_name"));
	            document.setFileType(ComplaintAttachments.DocumentTypes.valueOf(rs.getString("file_type")));
	            document.setUploadDate(rs.getDate("upload_date"));
	            document.setFileSize(rs.getDouble("file_size"));
	            // Assuming Complaints is a prepared model with a constructor that accepts id
	            document.setFkComplaintId(new Complaints(rs.getInt("fk_complaint_id")));
	            documents.add(document);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ComplaintAttachmentsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return documents;
	}
	
	/*
	 Prepares and allows the download of the specified document by its unique identifier, supporting the 'Download' action in the document management table.
	@param documentId the unique identifier of the document to be downloaded
	@return File object representing the document to be downloaded
	*/
	public File downloadDocument(String documentId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    File file = null;
	
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "SELECT * FROM complaint_attachments WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, Integer.parseInt(documentId));
	
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            // Assuming file path is stored in the database
	            // This part needs to be adapted based on how the files are stored (e.g., in filesystem, BLOB in DB)
	            String filePath = rs.getString("file_path");
	            file = new File(filePath);
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
	
	    return file;
	}
	
	public boolean deleteDocument(int documentId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean isDeleted = false;
	    String sql = "DELETE FROM complaint_attachments WHERE id = ?";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, documentId);
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            isDeleted = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error deleting document with ID: " + documentId, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error while closing the prepared statement", e);
	            }
	        }
	    }
	    return isDeleted;
	}
}
