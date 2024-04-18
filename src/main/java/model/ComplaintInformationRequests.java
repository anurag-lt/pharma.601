package model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Represents an information request associated with a complaint, detailing the specifics of the request, its status, and related complainant data.
 */
public class ComplaintInformationRequests {
    private int id;
    private Timestamp requestDate;
    private CommunicationStatus requestStatus;
    private String requestType;
    private Date responseDueDate;
    private int followUpCount;
    private String customMessage;
    private Complaints fkComplaintId;

    // Enum for request status
    public enum CommunicationStatus {
        SENT, FAILED, DRAFT, RESENT
    }

    public ComplaintInformationRequests() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public CommunicationStatus getRequestStatus() {
        return requestStatus;
    }

    public String getRequestType() {
        return requestType;
    }

    public Date getResponseDueDate() {
        return responseDueDate;
    }

    public int getFollowUpCount() {
        return followUpCount;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public Complaints getFkComplaintId() {
        return fkComplaintId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    public void setRequestStatus(CommunicationStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public void setResponseDueDate(Date responseDueDate) {
        this.responseDueDate = responseDueDate;
    }

    public void setFollowUpCount(int followUpCount) {
        this.followUpCount = followUpCount;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public void setFkComplaintId(Complaints fkComplaintId) {
        this.fkComplaintId = fkComplaintId;
    }

    // ToString method
    @Override
    public String toString() {
        return "ComplaintInformationRequests{" +
                "id=" + id +
                ", requestDate=" + requestDate +
                ", requestStatus=" + requestStatus +
                ", requestType='" + requestType + '\'' +
                ", responseDueDate=" + responseDueDate +
                ", followUpCount=" + followUpCount +
                ", customMessage='" + customMessage + '\'' +
                ", fkComplaintId=" + fkComplaintId +
                '}';
    }
}