package model;

import java.sql.Timestamp;

/**
 * Represents a log of communications associated with complaints.
 */
public class CommunicationLogs {

    private Long id;
    private Timestamp sentTime;
    private CommunicationStatus communicationStatus;
    private String templateUsed;
    private String linkToMessage;
    private Complaints fkComplaintId;
    private CommunicationTemplates fkTemplateId;

    public CommunicationLogs() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getSentTime() {
        return sentTime;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }

    public CommunicationStatus getCommunicationStatus() {
        return communicationStatus;
    }

    public void setCommunicationStatus(CommunicationStatus communicationStatus) {
        this.communicationStatus = communicationStatus;
    }

    public String getTemplateUsed() {
        return templateUsed;
    }

    public void setTemplateUsed(String templateUsed) {
        this.templateUsed = templateUsed;
    }

    public String getLinkToMessage() {
        return linkToMessage;
    }

    public void setLinkToMessage(String linkToMessage) {
        this.linkToMessage = linkToMessage;
    }

    public Complaints getFkComplaintId() {
        return fkComplaintId;
    }

    public void setFkComplaintId(Complaints fkComplaintId) {
        this.fkComplaintId = fkComplaintId;
    }

    public CommunicationTemplates getFkTemplateId() {
        return fkTemplateId;
    }

    public void setFkTemplateId(CommunicationTemplates fkTemplateId) {
        this.fkTemplateId = fkTemplateId;
    }

    @Override
    public String toString() {
        return "CommunicationLogs{" +
                "id=" + id +
                ", sentTime=" + sentTime +
                ", communicationStatus=" + communicationStatus +
                ", templateUsed='" + templateUsed + '\'' +
                ", linkToMessage='" + linkToMessage + '\'' +
                ", fkComplaintId=" + fkComplaintId +
                ", fkTemplateId=" + fkTemplateId +
                '}';
    }

    /**
     * Enumeration for communication status.
     */
    public enum CommunicationStatus {
        SENT,
        FAILED,
        DRAFT,
        RESENT
    }
}