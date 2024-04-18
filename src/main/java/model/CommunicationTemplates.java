package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * This class represents the communication template entity, storing template metadata including 
 * names, types, and content specifics for communication processes.
 */
public class CommunicationTemplates {
    
    private int id;
    private String templateName;
    private String templateContent;
    private String templateType;
    private Date creationDate;
    private Timestamp lastModifiedDate;

    /**
     * Gets the unique identifier for the communication template.
     * 
     * @return An integer representing the unique id of the communication template.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the communication template.
     * 
     * @param id An integer containing the unique id to set for the communication template.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the communication template.
     * 
     * @return A string representing the name of the template.
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Sets the name of the communication template.
     * 
     * @param templateName A string containing the name to set for the communication template.
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * Gets the content of the communication template.
     * 
     * @return A string representing the template's content.
     */
    public String getTemplateContent() {
        return templateContent;
    }

    /**
     * Sets the content of the communication template.
     * 
     * @param templateContent A string containing the content to set for the communication template.
     */
    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    /**
     * Gets the type of the communication template.
     * 
     * @return A string representing the type of the template.
     */
    public String getTemplateType() {
        return templateType;
    }

    /**
     * Sets the type of the communication template.
     * 
     * @param templateType A string containing the type to set for the communication template.
     */
    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    /**
     * Gets the creation date of the communication template.
     * 
     * @return A Date representing the creation date of the template.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the communication template.
     * 
     * @param creationDate A Date containing the creation date to set for the communication template.
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the last modification date of the communication template.
     * 
     * @return A Timestamp representing the last modified date of the template.
     */
    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the last modification date of the communication template.
     * 
     * @param lastModifiedDate A Timestamp containing the last modified date to set for the communication template.
     */
    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * Provides a string representation of the communication template object, including all its fields.
     * 
     * @return A string representation of the communication template instance.
     */
    @Override
    public String toString() {
        return "CommunicationTemplates{" +
                "id=" + id +
                ", templateName='" + templateName + '\'' +
                ", templateContent='" + templateContent + '\'' +
                ", templateType='" + templateType + '\'' +
                ", creationDate=" + creationDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}