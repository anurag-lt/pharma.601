package model;

import java.util.Date;

/**
 * Enumeration for representing investigation conclusion statuses.
 */
enum ComplaintStatus { 
    NEW, IN_PROGRESS, RESOLVED, CLOSED
}

/**
 * The InvestigationRecords class models the investigation_records entity, encapsulating details of investigation records.
 */
public class InvestigationRecords {

    private Long id;
    private String investigationSummary;
    private String investigatorNotes;
    private ComplaintStatus investigationConclusion;
    private Date investigationDate;
    private String conclusionRemarks;
    private Integer investigationDuration;
    private String resolvedBy;
    private Complaints fkComplaint;

    /**
     * Gets the unique identifier for the Investigation Record.
     * @return the unique identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the Investigation Record.
     * @param id the unique identifier
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the investigation summary.
     * @return the investigation summary
     */
    public String getInvestigationSummary() {
        return investigationSummary;
    }

    /**
     * Sets the investigation summary.
     * @param investigationSummary the investigation summary
     */
    public void setInvestigationSummary(String investigationSummary) {
        this.investigationSummary = investigationSummary;
    }

    /**
     * Gets the investigator notes.
     * @return the investigator notes
     */
    public String getInvestigatorNotes() {
        return investigatorNotes;
    }

    /**
     * Sets the investigator notes.
     * @param investigatorNotes the investigator notes
     */
    public void setInvestigatorNotes(String investigatorNotes) {
        this.investigatorNotes = investigatorNotes;
    }

    /**
     * Gets the investigation conclusion.
     * @return the investigation conclusion
     */
    public ComplaintStatus getInvestigationConclusion() {
        return investigationConclusion;
    }

    /**
     * Sets the investigation conclusion.
     * @param investigationConclusion the investigation conclusion
     */
    public void setInvestigationConclusion(ComplaintStatus investigationConclusion) {
        this.investigationConclusion = investigationConclusion;
    }

    /**
     * Gets the investigation date.
     * @return the investigation date
     */
    public Date getInvestigationDate() {
        return investigationDate;
    }

    /**
     * Sets the investigation date.
     * @param investigationDate the investigation date
     */
    public void setInvestigationDate(Date investigationDate) {
        this.investigationDate = investigationDate;
    }

    /**
     * Gets the conclusion remarks.
     * @return the conclusion remarks
     */
    public String getConclusionRemarks() {
        return conclusionRemarks;
    }

    /**
     * Sets the conclusion remarks.
     * @param conclusionRemarks the conclusion remarks
     */
    public void setConclusionRemarks(String conclusionRemarks) {
        this.conclusionRemarks = conclusionRemarks;
    }

    /**
     * Gets the investigation duration in days.
     * @return the investigation duration
     */
    public Integer getInvestigationDuration() {
        return investigationDuration;
    }

    /**
     * Sets the investigation duration in days.
     * @param investigationDuration the investigation duration
     */
    public void setInvestigationDuration(Integer investigationDuration) {
        this.investigationDuration = investigationDuration;
    }

    /**
     * Gets the ID of the staff member who resolved the investigation.
     * @return the resolver ID
     */
    public String getResolvedBy() {
        return resolvedBy;
    }

    /**
     * Sets the ID of the staff member who resolved the investigation.
     * @param resolvedBy the resolver ID
     */
    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    /**
     * Gets the linked complaint object.
     * @return the linked complaint
     */
    public Complaints getFkComplaint() {
        return fkComplaint;
    }

    /**
     * Sets the linked complaint object.
     * @param fkComplaint the linked complaint
     */
    public void setFkComplaint(Complaints fkComplaint) {
        this.fkComplaint = fkComplaint;
    }

    /**
     * Provides a string representation of an Investigation Record, including all its details.
     * @return a string representation of the Investigation Record
     */
    @Override
    public String toString() {
        return "InvestigationRecords{" +
                "id=" + id +
                ", investigationSummary='" + investigationSummary + '\'' +
                ", investigatorNotes='" + investigatorNotes + '\'' +
                ", investigationConclusion=" + investigationConclusion +
                ", investigationDate=" + investigationDate +
                ", conclusionRemarks='" + conclusionRemarks + '\'' +
                ", investigationDuration=" + investigationDuration +
                ", resolvedBy='" + resolvedBy + '\'' +
                ", fkComplaint=" + fkComplaint +
                '}';
    }
}