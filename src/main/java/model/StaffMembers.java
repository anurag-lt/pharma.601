package model;

import java.util.Date;

/**
 * Represents a staff member within the system with attributes that detail their personal information,
 * role, contact details, and operational status within the organization.
 */
public class StaffMembers {

    private int id;
    private String name;
    private String role;
    private String email;
    private String contactNumber;
    private Date joinDate;
    private boolean activeStatus;
    private ComplaintAssignments fkStaffMemberId;

    public StaffMembers() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
    
    public ComplaintAssignments getFkStaffMemberId() {
        return fkStaffMemberId;
    }

    public void setFkStaffMemberId(ComplaintAssignments fkStaffMemberId) {
        this.fkStaffMemberId = fkStaffMemberId;
    }

    @Override
    public String toString() {
        return "StaffMembers{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", joinDate=" + joinDate +
                ", activeStatus=" + activeStatus +
                ", fkStaffMemberId=" + fkStaffMemberId +
                '}';
    }
}