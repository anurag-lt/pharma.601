package model;

import java.util.Date;

/**
 * Represents a customer complaint within the system. It encapsulates all relevant details about a complaint,
 * including its description, status, and associated data like customer feedback and resolution date.
 */
public class Complaints {

    private int id;
    private String complaintDescription;
    private Date complaintDate;
    private ComplaintStatus complaintStatus;
    private ComplaintPriority complaintPriority;
    private String customerFeedback;
    private Date resolutionDate;
    private String productName;
    private String model;
    private String serialNumber;

    // Getters
    public int getId() {
        return id;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public Date getComplaintDate() {
        return complaintDate;
    }

    public ComplaintStatus getComplaintStatus() {
        return complaintStatus;
    }

    public ComplaintPriority getComplaintPriority() {
        return complaintPriority;
    }

    public String getCustomerFeedback() {
        return customerFeedback;
    }

    public Date getResolutionDate() {
        return resolutionDate;
    }

    public String getProductName() {
        return productName;
    }

    public String getModel() {
        return model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public void setComplaintDate(Date complaintDate) {
        this.complaintDate = complaintDate;
    }

    public void setComplaintStatus(ComplaintStatus complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public void setComplaintPriority(ComplaintPriority complaintPriority) {
        this.complaintPriority = complaintPriority;
    }

    public void setCustomerFeedback(String customerFeedback) {
        this.customerFeedback = customerFeedback;
    }

    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "Complaints{" +
                "id=" + id +
                ", complaintDescription='" + complaintDescription + '\'' +
                ", complaintDate=" + complaintDate +
                ", complaintStatus=" + complaintStatus +
                ", complaintPriority=" + complaintPriority +
                ", customerFeedback='" + customerFeedback + '\'' +
                ", resolutionDate=" + resolutionDate +
                ", productName='" + productName + '\'' +
                ", model='" + model + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }

    /**
     * Enum for Complaint Status.
     */
    public enum ComplaintStatus {
        NEW, IN_PROGRESS, RESOLVED, CLOSED
    }

    /**
     * Enum for Complaint Priority.
     */
    public enum ComplaintPriority {
        LOW, MEDIUM, HIGH, URGENT
    }
}