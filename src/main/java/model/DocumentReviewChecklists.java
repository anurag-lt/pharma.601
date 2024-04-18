package model;

public class DocumentReviewChecklists {

    /**
     * The primary key for the document_review_checklists table. Essential for ensuring uniqueness and in database operations such as joins, updates, and deletes.
     */
    private Long id;
    /**
     * Indicates whether the investigation report document has been verified as part of the complaint resolution process. It serves as a task-level parameter for audit and compliance, ensuring that no part of the investigation is overlooked before closing the complaint.
     */
    private Boolean investigationReportVerified;
    /**
     * Marks the verification of the action plan developed to address the complaint. It is critical at the process level to ensure that planned corrective actions are in place and acknowledged, facilitating accountability and effective resolution.
     */
    private Boolean actionPlanVerified;
    /**
     * Confirms that all communications with the complainant, including notifications and updates, have been properly logged and reviewed. This parameter is essential for quality control and customer satisfaction, ensuring that the complainant has been kept informed throughout the complaint resolution process.
     */
    private Boolean communicationLogVerified;
    /**
     * Confirms the verification of the corrective actions taken in response to the complaint. This is a critical task-level parameter reflecting rigorous follow-up on actions taken, ensuring that they are effectively addressing the complaint's cause and preventing recurrence.
     */
    private Boolean correctiveActionVerified;
    /**
     * Links each checklist to a specific complaint, ensuring the review process is aligned with the correct case. This relation helps in reporting by enabling a comprehensive view of all review actions taken for a complaint, aiding in audit trails and quality assurance.
     */
    private Complaints fkComplaintId;

    // Constructors, Getters, Setters, and toString() Methods

    public DocumentReviewChecklists() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getInvestigationReportVerified() {
        return investigationReportVerified;
    }

    public void setInvestigationReportVerified(Boolean investigationReportVerified) {
        this.investigationReportVerified = investigationReportVerified;
    }

    public Boolean getActionPlanVerified() {
        return actionPlanVerified;
    }

    public void setActionPlanVerified(Boolean actionPlanVerified) {
        this.actionPlanVerified = actionPlanVerified;
    }

    public Boolean getCommunicationLogVerified() {
        return communicationLogVerified;
    }

    public void setCommunicationLogVerified(Boolean communicationLogVerified) {
        this.communicationLogVerified = communicationLogVerified;
    }

    public Boolean getCorrectiveActionVerified() {
        return correctiveActionVerified;
    }

    public void setCorrectiveActionVerified(Boolean correctiveActionVerified) {
        this.correctiveActionVerified = correctiveActionVerified;
    }

    public Complaints getFkComplaintId() {
        return fkComplaintId;
    }

    public void setFkComplaintId(Complaints fkComplaintId) {
        this.fkComplaintId = fkComplaintId;
    }

    @Override
    public String toString() {
        return "DocumentReviewChecklists{" +
                "id=" + id +
                ", investigationReportVerified=" + investigationReportVerified +
                ", actionPlanVerified=" + actionPlanVerified +
                ", communicationLogVerified=" + communicationLogVerified +
                ", correctiveActionVerified=" + correctiveActionVerified +
                ", fkComplaintId=" + fkComplaintId +
                '}';
    }
}