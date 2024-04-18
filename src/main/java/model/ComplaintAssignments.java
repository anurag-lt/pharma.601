package model;

import java.util.Date;

/**
 * Represents an assignment of a complaint to a staff member, detailing the assignment status,
 * priority level, and completion information.
 */
public class ComplaintAssignments {

    private int id;
    private Date assignmentDate;
    private ComplaintPriorities complaintPriority;
    private String assignmentStatus;
    private Date completionDate;
    private Complaints fkComplaint;
    private StaffMembers fkStaffMember;

    public ComplaintAssignments() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(Date assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public ComplaintPriorities getComplaintPriority() {
        return complaintPriority;
    }

    public void setComplaintPriority(ComplaintPriorities complaintPriority) {
        this.complaintPriority = complaintPriority;
    }

    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Complaints getFkComplaint() {
        return fkComplaint;
    }

    public void setFkComplaint(Complaints fkComplaint) {
        this.fkComplaint = fkComplaint;
    }

    public StaffMembers getFkStaffMember() {
        return fkStaffMember;
    }

    public void setFkStaffMember(StaffMembers fkStaffMember) {
        this.fkStaffMember = fkStaffMember;
    }

    @Override
    public String toString() {
        return "ComplaintAssignments{" +
                "id=" + id +
                ", assignmentDate=" + assignmentDate +
                ", complaintPriority=" + complaintPriority +
                ", assignmentStatus='" + assignmentStatus + '\'' +
                ", completionDate=" + completionDate +
                ", fkComplaint=" + (fkComplaint != null ? fkComplaint.getId() : "null") +
                ", fkStaffMember=" + (fkStaffMember != null ? fkStaffMember.getId() : "null") +
                '}';
    }
}