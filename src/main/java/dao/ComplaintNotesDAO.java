package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class ComplaintNotesDAO {

	
	
	/**
	 * Saves assessment notes made by an officer on a complaint to the database.
	 * @param noteContent The actual text of the assessment officer's review notes.
	 * @param createdAt The timestamp marking when the note was initially created.
	 * @param updatedAt The timestamp marking when the note was last updated.
	 * @param fkComplaintId The foreign key linking the note to a specific complaint.
	 * @return boolean indicating success or failure of the operation.
	 */
	public boolean saveComplaintNote(String noteContent, Timestamp createdAt, Timestamp updatedAt, int fkComplaintId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean isSaved = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO complaint_notes (note_content, created_at, updated_at, fk_complaint_id) VALUES (?, ?, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, noteContent);
	        pstmt.setTimestamp(2, createdAt);
	        pstmt.setTimestamp(3, updatedAt);
	        pstmt.setInt(4, fkComplaintId);
	
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            isSaved = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.closeStatement(pstmt);
	        DatabaseUtility.disconnect(conn);
	    }
	    return isSaved;
	}
	
	/**
	 * Fetches all notes attached to a specific complaint for review and documentation purposes.
	 * @param complaintId The unique identifier for the complaint to retrieve all associated notes.
	 * @return A list of ComplaintNotes objects associated with the given complaint ID.
	 */
	public List<ComplaintNotes> fetchAllComplaintNotes(int complaintId) {
	  List<ComplaintNotes> notes = new ArrayList<>();
	  String sql = "SELECT * FROM complaint_notes WHERE fk_complaint_id = ?";
	  try (Connection conn = DatabaseUtility.connect();
	      PreparedStatement pstmt = conn.prepareStatement(sql)) {
	      pstmt.setInt(1, complaintId);
	      ResultSet rs = pstmt.executeQuery();
	      while (rs.next()) {
	          ComplaintNotes note = new ComplaintNotes();
	          note.setId(rs.getInt("id"));
	          note.setNoteContent(rs.getString("note_content"));
	          note.setCreatedAt(rs.getTimestamp("created_at"));
	          note.setUpdatedAt(rs.getTimestamp("updated_at"));
	          note.setFkComplaint(new Complaints()); // Placeholder for related complaint object
	          note.getFkComplaint().setId(rs.getInt("fk_complaint_id"));
	          notes.add(note);
	      }
	  } catch (SQLException e) {
	      Logger.getLogger(ComplaintNotesDAO.class.getName()).log(Level.SEVERE, null, e);
	  } finally {
	      DatabaseUtility.disconnect(conn);
	  }
	  return notes;
	}
	
	
	/**
	 * Updates an existing complaint note in the database.
	 * @param id The unique identifier of the complaint note to be updated.
	 * @param noteContent The updated text content of the complaint note.
	 * @param updatedAt The new timestamp marking the latest update to the note.
	 */
	public boolean updateComplaintNote(int id, String noteContent, Timestamp updatedAt) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean updateSuccess = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE complaint_notes SET note_content = ?, updated_at = ? WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, noteContent);
	        pstmt.setTimestamp(2, updatedAt);
	        pstmt.setInt(3, id);
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            updateSuccess = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        }
	    }
	    return updateSuccess;
	}
	
	/*
	 Deletes a complaint note identified by its unique ID. This method is used in the 'Review and Closure Module' to remove irrelevant or mistakenly added notes.
	 @param id The unique identifier of the complaint note to be deleted.
	*/
	public boolean deleteComplaintNote(int id) {
	  Connection conn = null;
	  PreparedStatement pstmt = null;
	  boolean isSuccess = false;
	  try {
	    conn = DatabaseUtility.connect();
	    String sql = "DELETE FROM complaint_notes WHERE id = ?;";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, id);
	
	    int rowsAffected = pstmt.executeUpdate();
	    if (rowsAffected > 0) {
	      isSuccess = true;
	    }
	  } catch (SQLException e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to delete complaint note", e);
	  } finally {
	    DatabaseUtility.disconnect(conn);
	    if (pstmt != null) {
	      try {
	        pstmt.close();
	      } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to close PreparedStatement", e);
	      }
	    }
	  }
	  return isSuccess;
	}
}
