package model;

import java.sql.Timestamp;

/**
 * Represents an access log entry for a document, detailing when and how a document was accessed.
 */
public class DocumentAccessLogs {

    /**
     * Unique identifier for each document access log record.
     */
    private long id;

    /**
     * The date and time when the document was accessed.
     */
    private Timestamp accessTime;

    /**
     * The type of action performed on the document.
     */
    private ActionType actionType;

    /**
     * The staff member who accessed the document.
     */
    private StaffMembers staffMember;

    /**
     * The document that was accessed.
     */
    private Documents document;

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Timestamp accessTime) {
        this.accessTime = accessTime;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public StaffMembers getStaffMember() {
        return staffMember;
    }

    public void setStaffMember(StaffMembers staffMember) {
        this.staffMember = staffMember;
    }

    public Documents getDocument() {
        return document;
    }

    public void setDocument(Documents document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return "DocumentAccessLogs{" +
                "id=" + id +
                ", accessTime=" + accessTime +
                ", actionType=" + actionType +
                ", staffMember=" + staffMember +
                ", document=" + document +
                '}';
    }

    /**
     * ActionType Enum representing types of actions that can be performed on a document.
     */
    public enum ActionType {
        UPLOADED,
        PERMISSIONS_EDITED,
        DOCUMENT_DELETED,
        DOCUMENT_VIEWED,
        DOCUMENT_DOWNLOADED,
        DOCUMENT_SHARED
    }
}