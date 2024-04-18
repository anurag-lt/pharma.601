package model;

import java.util.Date;

/**
 * This class represents the complaint subcategories within the complaint management system.
 * It holds information about each subcategory, including its name, description, and its relationship to complaint categories.
 */
public class ComplaintSubcategories {
    /**
     * The unique identifier for the complaint subcategory.
     */
    private int id;
    
    /**
     * The name of the complaint subcategory, providing a more granular classification within a broader complaint category.
     */
    private String subCategoryName;
    
    /**
     * A detailed description of what the subcategory encompasses, clarifying the scope and specific issues addressed.
     */
    private String description;
    
    /**
     * The date when the complaint subcategory was created, aiding in tracking the introduction of new categories.
     */
    private Date creationDate;
    
    /**
     * The timestamp for when the subcategory was last updated, ensuring the subcategory definitions stay current.
     */
    private Date lastUpdated;
    
    /**
     * The complaint category this subcategory is associated with, enabling hierarchical categorization of complaints.
     */
    private ComplaintCategories fkCategory;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ComplaintCategories getFkCategory() {
        return fkCategory;
    }

    public void setFkCategory(ComplaintCategories fkCategory) {
        this.fkCategory = fkCategory;
    }

    // toString Method
    @Override
    public String toString() {
        return "ComplaintSubcategories{" +
                "id=" + id +
                ", subCategoryName='" + subCategoryName + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", lastUpdated=" + lastUpdated +
                ", fkCategory=" + (fkCategory != null ? fkCategory.toString() : "null") +
                '}';
    }
}