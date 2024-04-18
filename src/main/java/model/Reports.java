package model;

import java.sql.Date;

/**
 * Represents a report entity mapping to the reports table in the database.
 */
public class Reports {
  
    private int id;
    private DocumentTypes reportType; // Assuming enum DocumentTypes is predefined as per your enums.
    private Date startDate;
    private Date endDate;
    private String summaryOfFindings;
    private Complaints fkComplaintId; // Using Complaints class assuming it's pre-defined and matches the foreign key relationship.
  
    public Reports() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DocumentTypes getReportType() {
        return reportType;
    }

    public void setReportType(DocumentTypes reportType) {
        this.reportType = reportType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSummaryOfFindings() {
        return summaryOfFindings;
    }

    public void setSummaryOfFindings(String summaryOfFindings) {
        this.summaryOfFindings = summaryOfFindings;
    }

    public Complaints getFkComplaintId() {
        return fkComplaintId;
    }

    public void setFkComplaintId(Complaints fkComplaintId) {
        this.fkComplaintId = fkComplaintId;
    }
  
    /**
     * Enum for document types, scoped to Reports class for simplification.
     * Replace or expand as needed based on actual enum definitions.
     */
    public enum DocumentTypes {
        INVESTIGATION_REPORT,
        RESOLUTION_DOCUMENTATION,
        COMPLAINT_FORM,
        ACTION_PLAN,
        CORRECTIVE_ACTION_DOCUMENTATION,
        COMMUNICATION_LOG
    }

    @Override
    public String toString() {
        return "Reports{" +
               "id=" + id +
               ", reportType=" + reportType +
               ", startDate=" + startDate +
               ", endDate=" + endDate +
               ", summaryOfFindings='" + summaryOfFindings + '\'' +
               ", fkComplaintId=" + fkComplaintId +
               '}';
    }
}