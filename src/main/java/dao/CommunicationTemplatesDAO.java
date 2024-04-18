package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CommunicationTemplatesDAO {

	
	/**
	 * This method retrieves all available communication templates for the dropdown selection, supporting the selection of appropriate message formats for various stages of complaint resolution.
	 * 
	 * @return A list of CommunicationTemplates objects representing all communication templates available in the database.
	 */
	public List<CommunicationTemplates> getAllCommunicationTemplates() {
	    List<CommunicationTemplates> templates = new ArrayList<>();
	    Connection conn = DatabaseUtility.connect();
	    String sql = "SELECT * FROM communication_templates";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {
	        while (rs.next()) {
	            CommunicationTemplates template = new CommunicationTemplates();
	            template.setId(rs.getInt("id"));
	            template.setTemplateName(rs.getString("template_name"));
	            template.setTemplateContent(rs.getString("template_content"));
	            template.setTemplateType(rs.getString("template_type"));
	            template.setCreationDate(rs.getDate("creation_date"));
	            template.setLastModifiedDate(rs.getTimestamp("last_modified_date"));
	            templates.add(template);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error retrieving communication templates", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return templates;
	}
	
	
	/**
	 * Fetches a specific communication template based on its ID. This supports dynamically loading template content into the form for previewing, editing, and sending to complainants.
	 *
	 * @param templateId An integer representing the unique id of the communication template.
	 * @return A CommunicationTemplates object containing the template's details or null if not found.
	 */
	public CommunicationTemplates getTemplateById(int templateId) {
	    Connection conn = DatabaseUtility.connect();
	    String sql = "SELECT * FROM communication_templates WHERE id = ?;";
	    CommunicationTemplates template = null;
	
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, templateId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                template = new CommunicationTemplates();
	                template.setId(rs.getInt("id"));
	                template.setTemplateName(rs.getString("template_name"));
	                template.setTemplateContent(rs.getString("template_content"));
	                template.setTemplateType(rs.getString("template_type"));
	                template.setCreationDate(rs.getDate("creation_date"));
	                template.setLastModifiedDate(rs.getTimestamp("last_modified_date"));
	            }
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error fetching template by ID", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	
	    return template;
	}
	
	/**
	 * Saves a draft of the communication message.
	 * @param subjectLine The subject line of the communication.
	 * @param messageBody The body of the communication message.
	 * @param templateId The unique id of the template used for the draft.
	 * @param recipientEmail The recipient's email address.
	 * @return true if draft saved successfully, false otherwise.
	 */
	public boolean saveCommunicationDraft(String subjectLine, String messageBody, int templateId, String recipientEmail) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = "INSERT INTO communication_drafts (subject_line, message_body, template_id, recipient_email) VALUES (?, ?, ?, ?)";
	
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, subjectLine);
	        pstmt.setString(2, messageBody);
	        pstmt.setInt(3, templateId);
	        pstmt.setString(4, recipientEmail);
	
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
	    }
	}
	
	/**
	 * Facilitates the sending of communication to complainants based on the selected template and entered message,
	 * ensuring proper logging and version control.
	 *
	 * @param subjectLine A string containing the subject line of the communication message.
	 * @param messageBody A string containing the full content of the communication message.
	 * @param templateId An integer representing the unique id of the template used for the message.
	 * @param recipientEmail A string containing the email address of the recipient.
	 * @return boolean indicating whether the communication was sent successfully.
	 */
	public boolean sendCommunication(String subjectLine, String messageBody, int templateId, String recipientEmail) {
	    Connection conn = null;
	    try {
	        conn = DatabaseUtility.connect();
	        // Assuming existence of a table `communications` for logging purposes
	        String sql = "INSERT INTO communications (subject_line, message_body, template_id, recipient_email) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, subjectLine);
	            pstmt.setString(2, messageBody);
	            pstmt.setInt(3, templateId);
	            pstmt.setString(4, recipientEmail);
	            int affectedRows = pstmt.executeUpdate();
	            return affectedRows > 0;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error when sending communication: ", e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	}
}
