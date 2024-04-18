package model;

/**
 * Represents a trend analysis for complaints over a specific period of time,
 * incorporating complaint count, type, status, and other aggregate metrics.
 */
public class ComplaintTrends {
    private int id;
    private String timeFrame;
    private int complaintCount;
    private String complaintType;
    private double averageResolutionTime;
    private double customerSatisfactionScore;
    private ComplaintStatus complaintStatus;
    private ComplaintPriorities complaintPriority;

    public ComplaintTrends() {
        // Default constructor
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public int getComplaintCount() {
        return complaintCount;
    }

    public void setComplaintCount(int complaintCount) {
        this.complaintCount = complaintCount;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public double getAverageResolutionTime() {
        return averageResolutionTime;
    }

    public void setAverageResolutionTime(double averageResolutionTime) {
        this.averageResolutionTime = averageResolutionTime;
    }

    public double getCustomerSatisfactionScore() {
        return customerSatisfactionScore;
    }

    public void setCustomerSatisfactionScore(double customerSatisfactionScore) {
        this.customerSatisfactionScore = customerSatisfactionScore;
    }

    public ComplaintStatus getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(ComplaintStatus complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public ComplaintPriorities getComplaintPriority() {
        return complaintPriority;
    }

    public void setComplaintPriority(ComplaintPriorities complaintPriority) {
        this.complaintPriority = complaintPriority;
    }

    @Override
    public String toString() {
        return "ComplaintTrends{" +
                "id=" + id +
                ", timeFrame='" + timeFrame + '\'' +
                ", complaintCount=" + complaintCount +
                ", complaintType='" + complaintType + '\'' +
                ", averageResolutionTime=" + averageResolutionTime +
                ", customerSatisfactionScore=" + customerSatisfactionScore +
                ", complaintStatus=" + complaintStatus +
                ", complaintPriority=" + complaintPriority +
                '}';
    }

    // Enums for status and priorities 
    public enum ComplaintStatus {
        NEW, IN_PROGRESS, RESOLVED, CLOSED
    }

    public enum ComplaintPriorities {
        LOW, MEDIUM, HIGH, URGENT
    }
}