package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class RegulatoryBodiesDAO {

	
	/**
	 * Retrieves a list of all regulatory bodies from the database to populate dropdown selections in the 'Report Generation Form'
	 * and 'Report Submission Modal' sections on the 'Regulatory Reporting Management' page.
	 * It is used to allow users to select the appropriate regulatory body to which a report will be submitted.
	 * @return A list of RegulatoryBodies objects representing all regulatory bodies in the database.
	 */
	public List<RegulatoryBodies> fetchAllRegulatoryBodies() {
	    List<RegulatoryBodies> regulatoryBodiesList = new ArrayList<>();
	    String query = "SELECT * FROM regulatory_bodies";
	    Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtility.connect();
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery(query);
	        while (rs.next()) {
	            RegulatoryBodies regulatoryBody = new RegulatoryBodies();
	            regulatoryBody.setId(rs.getInt("id"));
	            regulatoryBody.setName(rs.getString("name"));
	            regulatoryBody.setContactInformation(rs.getString("contact_information"));
	            regulatoryBody.setSubmissionGuidelines(rs.getString("submission_guidelines"));
	            regulatoryBodiesList.add(regulatoryBody);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(RegulatoryBodiesDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	        } catch (SQLException e) {
	            Logger.getLogger(RegulatoryBodiesDAO.class.getName()).log(Level.SEVERE, null, e);
	        }
	    }
	    return regulatoryBodiesList;
	}
	
	/**
	 * Retrieves details of a specific regulatory body based on its ID to provide detailed information on the 'Report Submission Modal' of the 'Regulatory Reporting Management' page.
	 * @param id The unique identifier of the regulatory body.
	 * @return a RegulatoryBodies object containing the details of the regulatory body.
	 */
	public RegulatoryBodies getRegulatoryBodyById(int id) {
	    Connection conn = DatabaseUtility.connect();
	    String query = "SELECT * FROM regulatory_bodies WHERE id = ?";
	    RegulatoryBodies regulatoryBody = null;
	    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            regulatoryBody = new RegulatoryBodies();
	            regulatoryBody.setId(rs.getInt("id"));
	            regulatoryBody.setName(rs.getString("name"));
	            regulatoryBody.setContactInformation(rs.getString("contact_information"));
	            regulatoryBody.setSubmissionGuidelines(rs.getString("submission_guidelines"));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error retrieving regulatory body by ID", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return regulatoryBody;
	}
	
	
	/**
	 * Adds a new regulatory body to the database.
	 * @param name Name of the regulatory body.
	 * @param contactInformation Contact information of the regulatory body.
	 * @param submissionGuidelines Submission guidelines provided by the regulatory body.
	 * @return boolean indicating whether the operation was successful.
	 */
	public boolean addRegulatoryBody(String name, String contactInformation, String submissionGuidelines) {
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    String insertQuery = "INSERT INTO regulatory_bodies (name, contact_information, submission_guidelines) VALUES (?, ?, ?)";
	    try {
	        pstmt = conn.prepareStatement(insertQuery);
	        pstmt.setString(1, name);
	        pstmt.setString(2, contactInformation);
	        pstmt.setString(3, submissionGuidelines);
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            return true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	            }
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	    return false;
	}
	
	
	/**
	* Updates information for an existing regulatory body in the database.
	*
	* @param id The unique identifier of the regulatory body to be updated.
	* @param name Updated name of the regulatory body.
	* @param contactInformation Updated contact information of the regulatory body.
	* @param submissionGuidelines Updated submission guidelines provided by the regulatory body.
	* @return Boolean indicating if the update was successful.
	*/
	public boolean updateRegulatoryBody(int id, String name, String contactInformation, String submissionGuidelines) {
	  Connection conn = null;
	  PreparedStatement pstmt = null;
	  boolean updateSuccess = false;
	
	  try {
	      conn = DatabaseUtility.connect();
	      String sql = "UPDATE regulatory_bodies SET name = ?, contact_information = ?, submission_guidelines = ? WHERE id = ?";
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, name);
	      pstmt.setString(2, contactInformation);
	      pstmt.setString(3, submissionGuidelines);
	      pstmt.setInt(4, id);
	
	      int rowsAffected = pstmt.executeUpdate();
	      updateSuccess = rowsAffected > 0;
	  } catch (SQLException e) {
	      Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating regulatory body", e);
	  } finally {
	      try {
	          if (pstmt != null) pstmt.close();
	          DatabaseUtility.disconnect(conn);
	      } catch (SQLException e) {
	          Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing resources", e);
	      }
	  }
	  return updateSuccess;
	}
}
