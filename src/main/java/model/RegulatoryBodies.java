package model;

/**
 * Represents a regulatory body entity with details about the regulatory body, including its name, contact information, and submission guidelines.
 */
public class RegulatoryBodies {
    
    private int id;
    private String name;
    private String contactInformation;
    private String submissionGuidelines;

    /**
     * Gets the unique identifier for the regulatory body.
     * @return the unique identifier of the regulatory body.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the regulatory body.
     * @param id the unique identifier to set for the regulatory body.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the regulatory body.
     * @return the name of the regulatory body.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the regulatory body.
     * @param name the name to set for the regulatory body.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the contact information for the regulatory body, including email addresses, phone numbers, and physical addresses.
     * @return the contact information of the regulatory body.
     */
    public String getContactInformation() {
        return contactInformation;
    }

    /**
     * Sets the contact information for the regulatory body, including email addresses, phone numbers, and physical addresses.
     * @param contactInformation the contact information to set for the regulatory body.
     */
    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    /**
     * Gets the submission guidelines provided by the regulatory body for submissions.
     * @return the submission guidelines of the regulatory body.
     */
    public String getSubmissionGuidelines() {
        return submissionGuidelines;
    }

    /**
     * Sets the submission guidelines provided by the regulatory body for submissions.
     * @param submissionGuidelines the submission guidelines to set for the regulatory body.
     */
    public void setSubmissionGuidelines(String submissionGuidelines) {
        this.submissionGuidelines = submissionGuidelines;
    }

    /**
     * Provides a string representation of the RegulatoryBodies object, including id, name, contact information, and submission guidelines.
     * @return a string representation of the RegulatoryBodies object.
     */
    @Override
    public String toString() {
        return "RegulatoryBodies{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactInformation='" + contactInformation + '\'' +
                ", submissionGuidelines='" + submissionGuidelines + '\'' +
                '}';
    }
}