package model;

import java.sql.Date;

/**
 * Represents the corrective action plans and activities (CAPAs) undertaken in response to complaints.
 */
public class Capas {
    private int id;
    private String actionDescription;
    private Date dueDate;
    private CapaStatuses capaStatus;
    private StaffMembers staffMember;
    private Complaints complaint;

    public Capas() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the description of the corrective action to be undertaken.
     *
     * @return Action description.
     */
    public String getActionDescription() {
        return actionDescription;
    }

    /**
     * Sets the description of the corrective action to be undertaken.
     *
     * @param actionDescription Description of the action.
     */
    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    /**
     * Gets the target completion date for the CAPA.
     *
     * @return Due date.
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets the target completion date for the CAPA.
     *
     * @param dueDate Target completion date.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the current stage of the CAPA.
     *
     * @return CAPA status.
     */
    public CapaStatuses getCapaStatus() {
        return capaStatus;
    }

    /**
     * Sets the current stage of the CAPA.
     *
     * @param capaStatus CAPA status.
     */
    public void setCapaStatus(CapaStatuses capaStatus) {
        this.capaStatus = capaStatus;
    }

    /**
     * Gets the staff member responsible for this CAPA.
     *
     * @return Staff member.
     */
    public StaffMembers getStaffMember() {
        return staffMember;
    }

    /**
     * Sets the staff member responsible for this CAPA.
     *
     * @param staffMember Staff member responsible for the CAPA.
     */
    public void setStaffMember(StaffMembers staffMember) {
        this.staffMember = staffMember;
    }

    /**
     * Gets the complaint linked to this CAPA.
     *
     * @return Complaint.
     */
    public Complaints getComplaint() {
        return complaint;
    }

    /**
     * Sets the complaint linked to this CAPA.
     *
     * @param complaint Complaint associated with the CAPA.
     */
    public void setComplaint(Complaints complaint) {
        this.complaint = complaint;
    }

    @Override
    public String toString() {
        return "Capas{" +
                "id=" + id +
                ", actionDescription='" + actionDescription + '\'' +
                ", dueDate=" + dueDate +
                ", capaStatus=" + capaStatus +
                ", staffMember=" + staffMember +
                ", complaint=" + complaint +
                '}';
    }

    /**
     * Enum for CAPA statuses.
     */
    public enum CapaStatuses {
        PLANNED,
        IN_PROGRESS,
        COMPLETED,
        ON_HOLD
    }
}