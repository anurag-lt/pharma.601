package dao;


import model.Complaints;
import utils.DatabaseUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.sql.Timestamp;


public class ComplaintsDAO {

	
	/**
	 * Creates a new complaint record in the database.
	 * @param complaintDescription The detailed description of the customer's complaint.
	 * @param complaintDate The date when the complaint was filed.
	 * @param complaintStatus The current status of the complaint.
	 * @param complaintPriority The urgency and importance of resolving the complaint.
	 * @param customerFeedback Optional feedback provided by the customer about the resolution process.
	 * @param resolutionDate The date on which the complaint was resolved or closed.
	 * @param productName The name of the product involved in the complaint.
	 * @param model Model of the product involved in the complaint.
	 * @param serialNumber Serial number of the product unit involved in the complaint.
	 * @return boolean True if the complaint was successfully created, false otherwise.
	 */
	public boolean createComplaint(String complaintDescription, Date complaintDate, Complaints.ComplaintStatus complaintStatus, Complaints.ComplaintPriority complaintPriority,  String customerFeedback, Date resolutionDate, String productName, String model, String serialNumber) {
	  Connection conn = DatabaseUtility.connect();
	  try {
	      PreparedStatement stmt = conn.prepareStatement("INSERT INTO complaints (complaint_description, complaint_date, complaint_status, complaint_priority, customer_feedback, resolution_date, product_name, model, serial_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
	      stmt.setString(1, complaintDescription);
	      stmt.setTimestamp(2, new Timestamp(complaintDate.getTime()));
	      stmt.setString(3, complaintStatus.name());
	      stmt.setString(4, complaintPriority.name());
	      stmt.setString(5, customerFeedback);
	      stmt.setTimestamp(6, resolutionDate != null ? new Timestamp(resolutionDate.getTime()) : null);
	      stmt.setString(7, productName);
	      stmt.setString(8, model);
	      stmt.setString(9, serialNumber);
	      int affectedRows = stmt.executeUpdate();
	      stmt.close();
	      return affectedRows > 0;
	  } catch (SQLException e) {
	      Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error creating complaint: " + e.getMessage(), e);
	      return false;
	  } finally {
	      DatabaseUtility.disconnect(conn);
	  }
	}
	
	
	/**
	 * Updates the status of a specified complaint. This method is used across multiple sections including
	 * 'Complaint Status Updates', 'Initial Assessment Workspace', 'Review and Closure Module', and
	 * 'Trend Analysis and Continuous Improvement Dashboard'.
	 *
	 * @param complaintId The unique identifier of the complaint.
	 * @param newStatus The new status to update the complaint to.
	 * @return boolean indicating if the update was successful.
	 */
	public boolean updateComplaintStatus(int complaintId, Complaints.ComplaintStatus newStatus) {
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement ps = null;
	    String query = "UPDATE complaints SET complaint_status = ? WHERE id = ?";
	
	    try {
	        ps = conn.prepareStatement(query);
	        ps.setString(1, newStatus.toString());
	        ps.setInt(2, complaintId);
	
	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        if (ps != null) {
	            try {
	                ps.close();
	            } catch (SQLException ex) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	    return false;
	}
	
	/**
	 * Retrieves detailed information about a specific complaint for initial assessment.
	 * @param complaintId The unique identifier of the complaint for which details are required.
	 * @return Complaints The complaint object filled with complaint details.
	 */
	public Complaints fetchComplaintDetailsForAssessment(int complaintId) {
	    Complaints complaint = null;
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String query = "SELECT * FROM complaints WHERE id = ?";
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, complaintId);
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            complaint = new Complaints();
	            complaint.setId(rs.getInt("id"));
	            complaint.setComplaintDescription(rs.getString("complaint_description"));
	            complaint.setComplaintDate(rs.getDate("complaint_date"));
	            complaint.setComplaintStatus(Complaints.ComplaintStatus.valueOf(rs.getString("complaint_status").toUpperCase()));
	            complaint.setComplaintPriority(Complaints.ComplaintPriority.valueOf(rs.getString("complaint_priority").toUpperCase()));
	            complaint.setCustomerFeedback(rs.getString("customer_feedback"));
	            complaint.setResolutionDate(rs.getDate("resolution_date"));
	            complaint.setProductName(rs.getString("product_name"));
	            complaint.setModel(rs.getString("model"));
	            complaint.setSerialNumber(rs.getString("serial_number"));
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (stmt != null) { try { stmt.close(); } catch (SQLException ex) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex); } }
	        if (rs != null) { try { rs.close(); } catch (SQLException ex) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex); } }
	    }
	    return complaint;
	}
	
	/**
	 * Fetches a list of complaints that are awaiting initial assessment.
	 * @param offset The start position in the dataset from which to begin retrieval.
	 * @param limit The maximum number of complaint records to return.
	 * @param sortBy The column by which to sort the results.
	 * @param sortDirection The order, either 'asc' for ascending or 'desc' for descending.
	 * @param filterByStatus Allows filtering complaints based on their current status.
	 * @return A List of Complaints objects that match the criteria.
	 */
	public List<Complaints> fetchComplaintsForAssessment(int offset, int limit, String sortBy, String sortDirection, String filterByStatus) {
	    Connection conn = DatabaseUtility.connect();
	    List<Complaints> complaintsList = new ArrayList<>();
	    String query = "SELECT * FROM complaints WHERE complaint_status = ? ORDER BY " + sortBy + " " + sortDirection + " LIMIT ? OFFSET ?";
	
	    try (PreparedStatement statement = conn.prepareStatement(query)) {
	        statement.setString(1, filterByStatus);
	        statement.setInt(2, limit);
	        statement.setInt(3, offset);
	
	        ResultSet rs = statement.executeQuery();
	
	        while (rs.next()) {
	            Complaints complaint = new Complaints();
	            complaint.setId(rs.getInt("id"));
	            complaint.setComplaintDescription(rs.getString("complaint_description"));
	            complaint.setComplaintDate(rs.getDate("complaint_date"));
	            complaint.setComplaintStatus(Complaints.ComplaintStatus.valueOf(rs.getString("complaint_status")));
	            complaint.setComplaintPriority(Complaints.ComplaintPriority.valueOf(rs.getString("complaint_priority")));
	            complaint.setCustomerFeedback(rs.getString("customer_feedback"));
	            complaint.setResolutionDate(rs.getDate("resolution_date"));
	            complaint.setProductName(rs.getString("product_name"));
	            complaint.setModel(rs.getString("model"));
	            complaint.setSerialNumber(rs.getString("serial_number"));
	            complaintsList.add(complaint);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	
	    return complaintsList;
	}
}
