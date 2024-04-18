package model;

import java.sql.Timestamp;

/**
 * Represents the complaint notes entity model with various attributes defining the notes related to complaints.
 */
public class ComplaintNotes {

    private int id;
    private String noteContent;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Complaints fkComplaint;

    /**
     * Gets the unique identifier for the complaint note.
     *
     * @return The unique identifier for the complaint note.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the complaint note.
     *
     * @param id The unique identifier for the complaint note.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the content of the note.
     *
     * @return The content of the note.
     */
    public String getNoteContent() {
        return noteContent;
    }

    /**
     * Sets the content of the note.
     *
     * @param noteContent The content of the note.
     */
    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    /**
     * Gets the time when the note was created.
     *
     * @return The creation time of the note.
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the time when the note was created.
     *
     * @param createdAt The creation time of the note.
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the time when the note was last updated.
     *
     * @return The update time of the note.
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the time when the note was last updated.
     *
     * @param updatedAt The update time of the note.
     */
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the associated complaint of the note.
     *
     * @return The associated complaint.
     */
    public Complaints getFkComplaint() {
        return fkComplaint;
    }

    /**
     * Sets the associated complaint of the note.
     *
     * @param fkComplaint The associated complaint.
     */
    public void setFkComplaint(Complaints fkComplaint) {
        this.fkComplaint = fkComplaint;
    }

    /**
     * Returns a string representation of the complaint notes object.
     *
     * @return String representation of the complaint notes object.
     */
    @Override
    public String toString() {
        return "ComplaintNotes{" +
                "id=" + id +
                ", noteContent='" + noteContent + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", fkComplaint=" + (fkComplaint != null ? fkComplaint.getId() : "null") +
                '}';
    }
}