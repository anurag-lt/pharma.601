package model;

import java.sql.Timestamp;

/**
 * Represents the actions taken on a complaint within the system.
 */
public class ComplaintActions {
    /**
     * Unique identifier for each complaint action.
     */
    private int id;

    /**
     * Describes the type of action taken on a complaint.
     */
    private String actionType;

    /**
     * Records the exact time and date when the action was performed.
     */
    private Timestamp actionDate;

    /**
     * Provides a detailed description of the action taken.
     */
    private String actionDescription;

    /**
     * Represents the status of the complaint after the action has been taken.
     */
    private ComplaintStatus complaintStatus;

    /**
     * Links each action to the specific complaint record it pertains to.
     */
    private Complaints fkComplaint;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Timestamp getActionDate() {
        return actionDate;
    }

    public void setActionDate(Timestamp actionDate) {
        this.actionDate = actionDate;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public ComplaintStatus getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(ComplaintStatus complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public Complaints getFkComplaint() {
        return fkComplaint;
    }

    public void setFkComplaint(Complaints fkComplaint) {
        this.fkComplaint = fkComplaint;
    }

    @Override
    public String toString() {
        return "ComplaintActions{" +
                "id=" + id +
                ", actionType='" + actionType + '\'' +
                ", actionDate=" + actionDate +
                ", actionDescription='" + actionDescription + '\'' +
                ", complaintStatus=" + complaintStatus +
                ", fkComplaint=" + fkComplaint +
                '}';
    }
}

enum ComplaintStatus {
    NEW, IN_PROGRESS, RESOLVED, CLOSED
}