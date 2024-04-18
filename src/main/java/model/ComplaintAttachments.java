package model;

import java.util.Date;

/**
 * ComplaintAttachments model for managing complaint attachment details in the system.
 */
public class ComplaintAttachments {

    private int id;
    private String attachmentName;
    private DocumentTypes fileType;
    private Date uploadDate;
    private double fileSize;
    private Complaints fkComplaintId;

    /**
     * Gets the unique identifier for the attachment.
     *
     * @return the unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the attachment.
     *
     * @param id the unique identifier to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the attachment.
     *
     * @return the attachment name
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * Sets the name of the attachment.
     *
     * @param attachmentName the name to set
     */
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    /**
     * Gets the file type of the attachment.
     *
     * @return the file type
     */
    public DocumentTypes getFileType() {
        return fileType;
    }

    /**
     * Sets the file type of the attachment.
     *
     * @param fileType the file type to set
     */
    public void setFileType(DocumentTypes fileType) {
        this.fileType = fileType;
    }

    /**
     * Gets the upload date of the attachment.
     *
     * @return the upload date
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * Sets the upload date of the attachment.
     *
     * @param uploadDate the upload date to set
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * Gets the file size of the attachment.
     *
     * @return the file size
     */
    public double getFileSize() {
        return fileSize;
    }

    /**
     * Sets the file size of the attachment.
     *
     * @param fileSize the file size to set
     */
    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Gets the Complaint object linked to the attachment.
     *
     * @return the linked Complaint object
     */
    public Complaints getFkComplaintId() {
        return fkComplaintId;
    }

    /**
     * Sets the Complaint object linked to the attachment.
     *
     * @param fkComplaintId the Complaint object to link
     */
    public void setFkComplaintId(Complaints fkComplaintId) {
        this.fkComplaintId = fkComplaintId;
    }

    @Override
    public String toString() {
        return "ComplaintAttachments{" +
                "id=" + id +
                ", attachmentName='" + attachmentName + '\'' +
                ", fileType=" + fileType +
                ", uploadDate=" + uploadDate +
                ", fileSize=" + fileSize +
                ", fkComplaintId=" + (fkComplaintId != null ? fkComplaintId.getId() : "null") +
                '}';
    }

    /**
     * Enumeration for DocumentTypes.
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