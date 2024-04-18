package model;

import java.util.Date;

/**
 * Represents an improvement initiative to address specific complaints or enhance processes within the organization.
 */
public class ImprovementInitiatives {

    private long id;
    private String initiativeTitle;
    private TargetedComplaintCategory targetedComplaintCategory;
    private Date startDate;
    private Date endDate;
    private String expectedOutcomes;
    private String assignedToIds;

    public ImprovementInitiatives() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInitiativeTitle() {
        return initiativeTitle;
    }

    public void setInitiativeTitle(String initiativeTitle) {
        this.initiativeTitle = initiativeTitle;
    }

    public TargetedComplaintCategory getTargetedComplaintCategory() {
        return targetedComplaintCategory;
    }

    public void setTargetedComplaintCategory(TargetedComplaintCategory targetedComplaintCategory) {
        this.targetedComplaintCategory = targetedComplaintCategory;
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

    public String getExpectedOutcomes() {
        return expectedOutcomes;
    }

    public void setExpectedOutcomes(String expectedOutcomes) {
        this.expectedOutcomes = expectedOutcomes;
    }

    public String getAssignedToIds() {
        return assignedToIds;
    }

    public void setAssignedToIds(String assignedToIds) {
        this.assignedToIds = assignedToIds;
    }

    @Override
    public String toString() {
        return "ImprovementInitiatives{" +
               "id=" + id +
               ", initiativeTitle='" + initiativeTitle + '\'' +
               ", targetedComplaintCategory=" + targetedComplaintCategory +
               ", startDate=" + startDate +
               ", endDate=" + endDate +
               ", expectedOutcomes='" + expectedOutcomes + '\'' +
               ", assignedToIds='" + assignedToIds + '\'' +
               '}';
    }

    /**
     * Defines categories of targeted complaints for improvement initiatives.
     */
    public enum TargetedComplaintCategory {
        DEFECTIVE_PRODUCT,
        LATE_DELIVERY,
        POOR_SERVICE,
        CUSTOMER_SUPPORT,
        PRODUCT_INFORMATION,
        BILLING_ISSUES
    }
}