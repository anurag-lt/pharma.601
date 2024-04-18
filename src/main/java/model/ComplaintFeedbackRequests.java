package model;

import java.sql.Timestamp;

/**
 * Represents the feedback requests issued for each complaint, containing details about the request and its status.
 */
public class ComplaintFeedbackRequests {

    private int id;
    private Complaints complaint; // Links to the Complaints class based on the complaint_id
    private String complainantContactInfo;
    private CommunicationTemplates messageTemplate; // Links to the CommunicationTemplates class based on the message_template_id
    private String customMessage;
    private CommunicationStatus communicationStatus; // Enum to represent the communication status
    private Timestamp sentTime;

    // Enum for communication status
    public enum CommunicationStatus {
        SENT, FAILED, DRAFT, RESENT
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Complaints getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaints complaint) {
        this.complaint = complaint;
    }

    public String getComplainantContactInfo() {
        return complainantContactInfo;
    }

    public void setComplainantContactInfo(String complainantContactInfo) {
        this.complainantContactInfo = complainantContactInfo;
    }

    public CommunicationTemplates getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(CommunicationTemplates messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public CommunicationStatus getCommunicationStatus() {
        return communicationStatus;
    }

    public void setCommunicationStatus(CommunicationStatus communicationStatus) {
        this.communicationStatus = communicationStatus;
    }

    public Timestamp getSentTime() {
        return sentTime;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }

    // toString method
    @Override
    public String toString() {
        return "ComplaintFeedbackRequests{" +
                "id=" + id +
                ", complaint=" + (complaint != null ? complaint.getId() : "null") +
                ", complainantContactInfo='" + complainantContactInfo + '\'' +
                ", messageTemplate=" + (messageTemplate != null ? messageTemplate.getId() : "null") +
                ", customMessage='" + customMessage + '\'' +
                ", communicationStatus=" + communicationStatus +
                ", sentTime=" + sentTime +
                '}';
    }
}