package model;

import java.sql.Timestamp;

/**
 * Represents the collaborative efforts taken during the investigation of a complaint.
 */
public class InvestigationCollaborations {

    private Long id;
    private Timestamp requestDate;
    private Timestamp responseDate;
    private ActionType actionType;
    private CommunicationStatus collaborationStatus;
    private String outcome;
    private String notes;
    private InvestigationRecords fkInvestigationId;
    private StaffMembers fkStaffMemberId;

    public InvestigationCollaborations() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    public Timestamp getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Timestamp responseDate) {
        this.responseDate = responseDate;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public CommunicationStatus getCollaborationStatus() {
        return collaborationStatus;
    }

    public void setCollaborationStatus(CommunicationStatus collaborationStatus) {
        this.collaborationStatus = collaborationStatus;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public InvestigationRecords getFkInvestigationId() {
        return fkInvestigationId;
    }

    public void setFkInvestigationId(InvestigationRecords fkInvestigationId) {
        this.fkInvestigationId = fkInvestigationId;
    }

    public StaffMembers getFkStaffMemberId() {
        return fkStaffMemberId;
    }

    public void setFkStaffMemberId(StaffMembers fkStaffMemberId) {
        this.fkStaffMemberId = fkStaffMemberId;
    }

    @Override
    public String toString() {
        return "InvestigationCollaborations{" +
                "id=" + id +
                ", requestDate=" + requestDate +
                ", responseDate=" + responseDate +
                ", actionType=" + actionType +
                ", collaborationStatus=" + collaborationStatus +
                ", outcome='" + outcome + '\'' +
                ", notes='" + notes + '\'' +
                ", fkInvestigationId=" + fkInvestigationId +
                ", fkStaffMemberId=" + fkStaffMemberId +
                '}';
    }

    public enum ActionType {
        UPLOADED, PERMISSIONS_EDITED, DOCUMENT_DELETED, DOCUMENT_VIEWED, DOCUMENT_DOWNLOADED, DOCUMENT_SHARED
    }
    
    public enum CommunicationStatus {
        SENT, FAILED, DRAFT, RESENT
    }
}