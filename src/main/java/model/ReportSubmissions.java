package model;

import java.sql.Timestamp;

/**
 * Represents a submission of a report to a regulatory body.
 */
public class ReportSubmissions {

    private int id;
    private Timestamp submissionDate;
    private SubmissionMethods submissionMethod;
    private CommunicationStatus submissionStatus;
    private Reports fkReport;
    private RegulatoryBodies fkRegulatoryBody;

    // Enum for submission methods
    public enum SubmissionMethods {
        EMAIL, PORTAL, MAIL, DIRECT_UPLOAD
    }

    // Enum for communication status
    public enum CommunicationStatus {
        SENT, FAILED, DRAFT, RESENT
    }

    public ReportSubmissions() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Timestamp submissionDate) {
        this.submissionDate = submissionDate;
    }

    public SubmissionMethods getSubmissionMethod() {
        return submissionMethod;
    }

    public void setSubmissionMethod(SubmissionMethods submissionMethod) {
        this.submissionMethod = submissionMethod;
    }

    public CommunicationStatus getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(CommunicationStatus submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public Reports getFkReport() {
        return fkReport;
    }

    public void setFkReport(Reports fkReport) {
        this.fkReport = fkReport;
    }

    public RegulatoryBodies getFkRegulatoryBody() {
        return fkRegulatoryBody;
    }

    public void setFkRegulatoryBody(RegulatoryBodies fkRegulatoryBody) {
        this.fkRegulatoryBody = fkRegulatoryBody;
    }

    @Override
    public String toString() {
        return "ReportSubmissions{" +
                "id=" + id +
                ", submissionDate=" + submissionDate +
                ", submissionMethod='" + submissionMethod + '\'' +
                ", submissionStatus='" + submissionStatus + '\'' +
                ", fkReport=" + fkReport +
                ", fkRegulatoryBody=" + fkRegulatoryBody +
                '}';
    }
}