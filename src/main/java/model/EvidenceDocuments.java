package model;

import java.sql.Date;

/**
 * Represents an evidence document associated with a complaint's investigation.
 */
public class EvidenceDocuments {
    private int id;
    private String documentName;
    private DocumentTypes documentType;
    private Date uploadDate;
    private double fileSize;
    private String fileFormat;
    private Complaints fkComplaintId;
    private InvestigationRecords fkInvestigationRecordId;

    public EvidenceDocuments() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the document.
     * @return the name of the document.
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Sets the name of the document.
     * @param documentName the name of the document.
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    /**
     * Gets the type of the document based on the predefined document types.
     * @return the document type.
     */
    public DocumentTypes getDocumentType() {
        return documentType;
    }

    /**
     * Sets the type of the document based on the predefined document types.
     * @param documentType the document type to set.
     */
    public void setDocumentType(DocumentTypes documentType) {
        this.documentType = documentType;
    }

    /**
     * Gets the upload date of the document.
     * @return the upload date.
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * Sets the upload date of the document.
     * @param uploadDate the upload date to set.
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * Gets the size of the file in bytes.
     * @return the size of the file.
     */
    public double getFileSize() {
        return fileSize;
    }

    /**
     * Sets the size of the file in bytes.
     * @param fileSize the file size to set.
     */
    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Gets the format of the file.
     * @return the file format.
     */
    public String getFileFormat() {
        return fileFormat;
    }

    /**
     * Sets the format of the file.
     * @param fileFormat the file format to set.
     */
    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    /**
     * Gets the complaint associated with this document.
     * @return the associated complaint.
     */
    public Complaints getFkComplaintId() {
        return fkComplaintId;
    }

    /**
     * Sets the complaint associated with this document.
     * @param fkComplaintId the complaint to associate.
     */
    public void setFkComplaintId(Complaints fkComplaintId) {
        this.fkComplaintId = fkComplaintId;
    }

    /**
     * Gets the investigation record associated with this document.
     * @return the associated investigation record.
     */
    public InvestigationRecords getFkInvestigationRecordId() {
        return fkInvestigationRecordId;
    }

    /**
     * Sets the investigation record associated with this document.
     * @param fkInvestigationRecordId the investigation record to associate.
     */
    public void setFkInvestigationRecordId(InvestigationRecords fkInvestigationRecordId) {
        this.fkInvestigationRecordId = fkInvestigationRecordId;
    }

    @Override
    public String toString() {
        return "EvidenceDocuments{" +
                "id=" + id +
                ", documentName='" + documentName + '\'' +
                ", documentType=" + documentType +
                ", uploadDate=" + uploadDate +
                ", fileSize=" + fileSize +
                ", fileFormat='" + fileFormat + '\'' +
                ", fkComplaintId=" + fkComplaintId +
                ", fkInvestigationRecordId=" + fkInvestigationRecordId +
                '}';
    }
}

/**
 * Enum representing possible types of documents.
 */
enum DocumentTypes {
    INVESTIGATION_REPORT, RESOLUTION_DOCUMENTATION, COMPLAINT_FORM, ACTION_PLAN, CORRECTIVE_ACTION_DOCUMENTATION, COMMUNICATION_LOG
}