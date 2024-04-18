package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;


public class EvidenceDocumentsDAO {

	
	/**
	 * Saves an evidence document to the database.
	 * @param documentName Name of the document or file uploaded as evidence in the investigation process.
	 * @param documentType Specifies the type of the uploaded document based on predefined categories.
	 * @param uploadDate The date when the evidence document was uploaded to the system.
	 * @param fileSize The size of the uploaded file, measured in kilobytes (KB) or megabytes (MB).
	 * @param fileFormat The format of the uploaded document (e.g., PDF, JPG, PNG).
	 * @param fkComplaintId The complaint associated with this document.
	 * @param fkInvestigationRecordId The investigation record associated with this document.
	 */
	public void saveEvidenceDocument(String documentName, DocumentTypes documentType, Date uploadDate, double fileSize, String fileFormat, Complaints fkComplaintId, InvestigationRecords fkInvestigationRecordId) {
	    Connection conn = DatabaseUtility.connect();
	    try {
	        String sql = "INSERT INTO evidence_documents (document_name, document_type, upload_date, file_size, file_format, fk_complaint_id, fk_investigation_record_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, documentName);
	        pstmt.setString(2, documentType.name());
	        pstmt.setDate(3, uploadDate);
	        pstmt.setDouble(4, fileSize);
	        pstmt.setString(5, fileFormat);
	        pstmt.setInt(6, fkComplaintId.getId());
	        pstmt.setInt(7, fkInvestigationRecordId.getId());
	
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        Logger.getLogger(EvidenceDocumentsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	}
	
	/**
	 * Retrieves all evidence documents associated with a specific complaint, used in sections that need to display or manage documents for a complaint.
	 * @param complaintId The unique identifier of the complaint associated with the evidence documents.
	 * @return A list of EvidenceDocuments objects associated with the specified complaint ID.
	 */
	public ArrayList<EvidenceDocuments> fetchEvidenceDocumentsByComplaintId(int complaintId) {
	    ArrayList<EvidenceDocuments> documents = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = "SELECT * FROM evidence_documents WHERE fk_complaint_id = ?";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, complaintId);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            EvidenceDocuments document = new EvidenceDocuments();
	            document.setId(rs.getInt("id"));
	            document.setDocumentName(rs.getString("document_name"));
	            // Assuming ENUM type 'document_type' can be directly mapped
	            document.setDocumentType(DocumentTypes.valueOf(rs.getString("document_type")));
	            document.setUploadDate(rs.getDate("upload_date"));
	            document.setFileSize(rs.getDouble("file_size"));
	            document.setFileFormat(rs.getString("file_format"));
	            documents.add(document);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error fetching evidence documents by complaint ID", e);
	    } finally {
	        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
	        DatabaseUtility.disconnect(conn);
	    }
	    return documents;
	}
	
	/**
	 * Retrieves all evidence documents linked to a particular investigation record, aiding in the
	 * aggregation and display of investigational findings.
	 * @param investigationRecordId The unique identifier of the investigation record associated with the evidence documents.
	 * @return A list of EvidenceDocuments objects linked to the specified investigation record.
	 */
	public List<EvidenceDocuments> fetchEvidenceDocumentsByInvestigationRecordId(int investigationRecordId) {
	    List<EvidenceDocuments> documents = new ArrayList<>();
	    Connection conn = DatabaseUtility.connect();
	    String sql = "SELECT * FROM evidence_documents WHERE fk_investigation_record_id = ?;";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, investigationRecordId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                EvidenceDocuments doc = new EvidenceDocuments();
	                doc.setId(rs.getInt("id"));
	                doc.setDocumentName(rs.getString("document_name"));
	                // Assuming ENUM types are managed appropriately
	                doc.setDocumentType(DocumentTypes.valueOf(rs.getString("document_type")));
	                doc.setUploadDate(rs.getDate("upload_date"));
	                doc.setFileSize(rs.getDouble("file_size"));
	                doc.setFileFormat(rs.getString("file_format"));
	                // Note: Linking objects by ID, actual object setting may vary based on implementation
	                documents.add(doc);
	            }
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(EvidenceDocumentsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return documents;
	}
	
	/**
	 * Updates an existing evidence document record in the database.
	 * @param id The unique identifier for the evidence document record to be updated.
	 * @param document The evidence document object including updates to be persisted.
	 */
	public boolean updateEvidenceDocument(int id, EvidenceDocuments document) {
	    Connection conn = DatabaseUtility.connect();
	    boolean updateResult = false;
	    String query = "UPDATE evidence_documents SET document_name=?, document_type=?, upload_date=?, file_size=?, file_format=?, fk_complaint_id=?, fk_investigation_record_id=? WHERE id=?";
	
	    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, document.getDocumentName());
	        pstmt.setString(2, document.getDocumentType().toString());
	        pstmt.setDate(3, document.getUploadDate());
	        pstmt.setDouble(4, document.getFileSize());
	        pstmt.setString(5, document.getFileFormat());
	        pstmt.setInt(6, document.getFkComplaintId().getId());
	        pstmt.setInt(7, document.getFkInvestigationRecordId().getId());
	        pstmt.setInt(8, id);
	
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            updateResult = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating evidence document", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return updateResult;
	}
	
	
	/**
	 * Deletes a specific evidence document from the database, used for document management and cleanup operations.
	 * @param id The unique identifier of the evidence document to be deleted.
	 */
	public boolean deleteEvidenceDocumentById(int id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean isDeleted = false;
	    String sql = "DELETE FROM evidence_documents WHERE id = ?;";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, id);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            isDeleted = true;
	            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Evidence document with ID: " + id + " has been successfully deleted.");
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error deleting evidence document with ID: " + id, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try {
	           if (pstmt != null) {
	               pstmt.close();
	           }
	        } catch (SQLException e) {
	           Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to close resources", e);
	        }
	    }
	    return isDeleted;
	}
	
	
	/**
	 * Logs the event of a document being accessed, useful for audit trails and security monitoring.
	 * @param userId Identifier of the user who accessed the document.
	 * @param documentId The unique identifier of the document that was accessed.
	 * @param accessTime The date and time when the document was accessed.
	 */
	public void logDocumentAccess(String userId, int documentId, Date accessTime) {
	    Connection conn = DatabaseUtility.connect();
	    String query = "INSERT INTO document_access_logs (user_id, document_id, access_time) VALUES (?, ?, ?)";
	    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, userId);
	        pstmt.setInt(2, documentId);
	        pstmt.setTimestamp(3, new Timestamp(accessTime.getTime()));
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows == 0) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to log document access.");
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "SQL Exception: ", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	}
}
