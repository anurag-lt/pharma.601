package model;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Represents a category of complaints for managing and categorizing complaints within the system.
 */
public class ComplaintCategories {

    private int id;
    private String categoryName;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public ComplaintCategories() {
    }

    public ComplaintCategories(int id, String categoryName, String description, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.categoryName = categoryName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the complaint category.
     *
     * @return category name.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the name of the complaint category.
     *
     * @param categoryName the name of the category.
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Gets the description of the complaint category.
     *
     * @return category description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the complaint category.
     *
     * @param description the description of the category.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the creation timestamp of the complaint category.
     *
     * @return creation timestamp.
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the complaint category.
     *
     * @param createdAt the creation timestamp.
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the update timestamp of the complaint category.
     *
     * @return update timestamp.
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the update timestamp of the complaint category.
     *
     * @param updatedAt the update timestamp.
     */
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplaintCategories)) return false;
        ComplaintCategories that = (ComplaintCategories) o;
        return getId() == that.getId() && Objects.equals(getCategoryName(), that.getCategoryName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCategoryName(), getDescription(), getCreatedAt(), getUpdatedAt());
    }

    @Override
    public String toString() {
        return "ComplaintCategories{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}