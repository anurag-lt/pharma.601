package model;

import java.sql.Timestamp;

public class AuditLogs {
    
    /**
     * Primary key for each entry in the audit log, ensuring a unique identifier for each log entry which is crucial for indexing and retrieval.
     **/
    private int id;
    
    /**
     * Records the exact time when the action was performed on a document. Critical for chronological tracking and understanding the sequence of actions for audit trails.
     **/
    private Timestamp actionDate;
    
    /**
     * Specifies the type of action performed (e.g., Uploaded, Permissions Edited), crucial for categorizing document interactions and supporting detailed audit trails.
     **/
    private ActionType actionType;
    
    /**
     * Provides a comprehensive description of the action performed, enhancing the audit log's usefulness by offering detailed context for each action, contributing to better oversight and control.
     **/
    private String detailedActionDescription;
    
    /**
     * Timestamp marking when the action was logged, contributing to the accuracy and timeliness of the audit trail.
     **/
    private Timestamp actionTime;
    
    /**
     * Categorizes the document involved in the action, aiding in filtering and identifying document-related actions in the audit process.
     **/
    private DocumentTypes documentType;
    
    /**
     * Name of the document involved in the action, aiding in easier identification and retrieval within the audit logs.
     **/
    private String documentName;
    
    /**
     * Role of the user who performed the action, providing insight into responsibilities and access levels in document handling.
     **/
    private String userRole;
    
    /**
     * Links each audit log entry to the specific document it pertains to, for tracking actions taken on that document.
     **/
    private Documents fkDocumentId;
    
    /**
     * Identifies the staff member who performed the action recorded in the audit log, establishing accountability.
     **/
    private StaffMembers fkUserId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getActionDate() {
        return actionDate;
    }

    public void setActionDate(Timestamp actionDate) {
        this.actionDate = actionDate;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getDetailedActionDescription() {
        return detailedActionDescription;
    }

    public void setDetailedActionDescription(String detailedActionDescription) {
        this.detailedActionDescription = detailedActionDescription;
    }

    public Timestamp getActionTime() {
        return actionTime;
    }

    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }

    public DocumentTypes getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypes documentType) {
        this.documentType = documentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Documents getFkDocumentId() {
        return fkDocumentId;
    }

    public void setFkDocumentId(Documents fkDocumentId) {
        this.fkDocumentId = fkDocumentId;
    }

    public StaffMembers getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(StaffMembers fkUserId) {
        this.fkUserId = fkUserId;
    }

    @Override
    public String toString() {
        return "AuditLogs{" +
                "id=" + id +
                ", actionDate=" + actionDate +
                ", actionType=" + actionType +
                ", detailedActionDescription='" + detailedActionDescription + '\'' +
                ", actionTime=" + actionTime +
                ", documentType=" + documentType +
                ", documentName='" + documentName + '\'' +
                ", userRole='" + userRole + '\'' +
                ", fkDocumentId=" + fkDocumentId +
                ", fkUserId=" + fkUserId +
                '}';
    }

    public enum ActionType {
        UPLOADED, PERMISSIONS_EDITED, DOCUMENT_DELETED, DOCUMENT_VIEWED, DOCUMENT_DOWNLOADED, DOCUMENT_SHARED
    }

    public enum DocumentTypes {
        INVESTIGATION_REPORT, RESOLUTION_DOCUMENTATION, COMPLAINT_FORM, ACTION_PLAN, CORRECTIVE_ACTION_DOCUMENTATION, COMMUNICATION_LOG
    }
}