package model;

import java.util.Date;

/**
 * Represents the review status of a complaint within the system.
 */
public class ComplaintReviewStatuses {

    private int id;
    private boolean reviewStatus;
    private String reviewNotes;
    private Date reviewDate;
    private Complaints fkComplaintId;

    /**
     * Gets the unique identifier for the complaint review status.
     *
     * @return the unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the complaint review status.
     *
     * @param id the unique identifier to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Determines if the complaint review passed.
     *
     * @return the review status
     */
    public boolean isReviewStatus() {
        return reviewStatus;
    }

    /**
     * Sets the complaint review status.
     *
     * @param reviewStatus the review status to set
     */
    public void setReviewStatus(boolean reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    /**
     * Gets additional comments or observations made by the reviewer.
     *
     * @return the review notes
     */
    public String getReviewNotes() {
        return reviewNotes;
    }

    /**
     * Sets additional comments or observations made by the reviewer.
     *
     * @param reviewNotes the review notes to set
     */
    public void setReviewNotes(String reviewNotes) {
        this.reviewNotes = reviewNotes;
    }

    /**
     * Gets the date when the review was conducted.
     *
     * @return the review date
     */
    public Date getReviewDate() {
        return reviewDate;
    }

    /**
     * Sets the date when the review was conducted.
     *
     * @param reviewDate the review date to set
     */
    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    /**
     * Gets the complaint associated with this review status.
     *
     * @return the associated complaint
     */
    public Complaints getFkComplaintId() {
        return fkComplaintId;
    }

    /**
     * Sets the complaint associated with this review status.
     *
     * @param fkComplaintId the associated complaint to set
     */
    public void setFkComplaintId(Complaints fkComplaintId) {
        this.fkComplaintId = fkComplaintId;
    }

    @Override
    public String toString() {
        return "ComplaintReviewStatuses{" +
                "id=" + id +
                ", reviewStatus=" + reviewStatus +
                ", reviewNotes='" + reviewNotes + '\'' +
                ", reviewDate=" + reviewDate +
                ", fkComplaintId=" + (fkComplaintId != null ? fkComplaintId.toString() : "null") +
                '}';
    }
}