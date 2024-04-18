package model;

import java.sql.Timestamp;

/**
 * Represents the updates made to the status of a complaint within the system.
 */
public class ComplaintStatusUpdates {

    private Long id;
    private Timestamp updateTimestamp;
    private ComplaintStatus complaintStatus;
    private String reasonForChange;
    private String changedByRole;
    private Complaints complaint;
    private InvestigationRecords investigationRecord;
    private StaffMembers processedBy;

    public ComplaintStatusUpdates() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Timestamp updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public ComplaintStatus getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(ComplaintStatus complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public String getReasonForChange() {
        return reasonForChange;
    }

    public void setReasonForChange(String reasonForChange) {
        this.reasonForChange = reasonForChange;
    }

    public String getChangedByRole() {
        return changedByRole;
    }

    public void setChangedByRole(String changedByRole) {
        this.changedByRole = changedByRole;
    }

    public Complaints getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaints complaint) {
        this.complaint = complaint;
    }

    public InvestigationRecords getInvestigationRecord() {
        return investigationRecord;
    }

    public void setInvestigationRecord(InvestigationRecords investigationRecord) {
        this.investigationRecord = investigationRecord;
    }

    public StaffMembers getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(StaffMembers processedBy) {
        this.processedBy = processedBy;
    }

    @Override
    public String toString() {
        return "ComplaintStatusUpdates{" +
               "id=" + id +
               ", updateTimestamp=" + updateTimestamp +
               ", complaintStatus=" + complaintStatus +
               ", reasonForChange='" + reasonForChange + '\'' +
               ", changedByRole='" + changedByRole + '\'' +
               ", complaint=" + complaint +
               ", investigationRecord=" + investigationRecord +
               ", processedBy=" + processedBy +
               '}';
    }

    /**
     * Enum for representing the possible statuses of a complaint.
     */
    public enum ComplaintStatus {
        NEW, IN_PROGRESS, RESOLVED, CLOSED
    }
}