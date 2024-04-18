package model;

import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * This class represents a document entity in the system. 
 * It captures comprehensive details about documents related to complaint processes.
 */
public class Documents {

    private Long id;
    private String documentName;
    private Timestamp uploadTime;
    private DocumentTypes documentType;
    private BigDecimal fileSize;
    private String fileFormat;
    private String contentHash;
    private Complaints fkComplaintId;

    /**
     * Gets the unique identifier for the document.
     * 
     * @return the document's unique id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the document.
     * 
     * @param id the unique identifier to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the document.
     * 
     * @return the name of the document
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Sets the name of the document.
     * 
     * @param documentName the document name to set
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    /**
     * Gets the upload time of the document.
     * 
     * @return the document's upload time
     */
    public Timestamp getUploadTime() {
        return uploadTime;
    }

    /**
     * Sets the upload time of the document.
     * 
     * @param uploadTime the upload time to set
     */
    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * Gets the type of the document.
     * 
     * @return the document's type
     */
    public DocumentTypes getDocumentType() {
        return documentType;
    }

    /**
     * Sets the type of the document.
     * 
     * @param documentType the document type to set
     */
    public void setDocumentType(DocumentTypes documentType) {
        this.documentType = documentType;
    }

    /**
     * Gets the file size of the document.
     * 
     * @return the document's file size
     */
    public BigDecimal getFileSize() {
        return fileSize;
    }

    /**
     * Sets the file size of the document.
     * 
     * @param fileSize the file size to set
     */
    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Gets the file format of the document.
     * 
     * @return the document's file format
     */
    public String getFileFormat() {
        return fileFormat;
    }

    /**
     * Sets the file format of the document.
     * 
     * @param fileFormat the file format to set
     */
    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    /**
     * Gets the content hash of the document.
     * 
     * @return the document's content hash
     */
    public String getContentHash() {
        return contentHash;
    }

    /**
     * Sets the content hash of the document.
     * 
     * @param contentHash the content hash to set
     */
    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }

    /**
     * Gets the Complaints object associated with the document.
     * 
     * @return the associated Complaints object
     */
    public Complaints getFkComplaintId() {
        return fkComplaintId;
    }

    /**
     * Sets the Complaints object associated with the document.
     * 
     * @param fkComplaintId the Complaints object to associate
     */
    public void setFkComplaintId(Complaints fkComplaintId) {
        this.fkComplaintId = fkComplaintId;
    }

    @Override
    public String toString() {
        return "Documents{" +
                "id=" + id +
                ", documentName='" + documentName + '\'' +
                ", uploadTime=" + uploadTime +
                ", documentType=" + documentType +
                ", fileSize=" + fileSize +
                ", fileFormat='" + fileFormat + '\'' +
                ", contentHash='" + contentHash + '\'' +
                ", fkComplaintId=" + (fkComplaintId != null ? fkComplaintId.getId() : null) +
                '}';
    }

    /**
     * Enumeration of document types to ensure consistency across the application.
     */
    public enum DocumentTypes {
        INVESTIGATION_REPORT,
        RESOLUTION_DOCUMENTATION,
        COMPLAINT_FORM,
        ACTION_PLAN,
        CORRECTIVE_ACTION_DOCUMENTATION,
        COMMUNICATION_LOG
    }
}