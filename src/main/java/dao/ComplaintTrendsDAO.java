package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.stream.Collectors;


public class ComplaintTrendsDAO {

	
	/**
	 * Fetches complaint trends based on time frame, category, and supports pagination.
	 * @param timeFrame The time frame for which to fetch complaint trends.
	 * @param category The category of complaints to filter by.
	 * @param limit The maximum number of complaints to return.
	 * @param offset The starting point from which to return complaints.
	 * @return A list of {@link ComplaintTrends} objects representing the complaint trends.
	 */
	public List<ComplaintTrends> fetchComplaintTrends(String timeFrame, String category, int limit, int offset) {
	    List<ComplaintTrends> trends = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	
	    String query = "SELECT * FROM complaint_trends WHERE time_frame = ? AND complaint_type = ? LIMIT ? OFFSET ?";
	
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, timeFrame);
	        pstmt.setString(2, category);
	        pstmt.setInt(3, limit);
	        pstmt.setInt(4, offset);
	
	        rs = pstmt.executeQuery();
	
	        while (rs.next()) {
	            ComplaintTrends trend = new ComplaintTrends();
	            trend.setId(rs.getInt("id"));
	            trend.setTimeFrame(rs.getString("time_frame"));
	            trend.setComplaintCount(rs.getInt("complaint_count"));
	            trend.setComplaintType(rs.getString("complaint_type"));
	            trend.setAverageResolutionTime(rs.getDouble("average_resolution_time"));
	            trend.setCustomerSatisfactionScore(rs.getDouble("customer_satisfaction_score"));
	            trend.setComplaintStatus(ComplaintTrends.ComplaintStatus.valueOf(rs.getString("complaint_status"))); 
	            trend.setComplaintPriority(ComplaintTrends.ComplaintPriorities.valueOf(rs.getString("complaint_priority")));
	            trends.add(trend);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ComplaintTrendsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try {
	            if (pstmt != null) pstmt.close();
	            if (rs != null) rs.close();
	        } catch (SQLException e) {
	            Logger.getLogger(ComplaintTrendsDAO.class.getName()).log(Level.SEVERE, null, e);
	        }
	    }
	
	    return trends;
	}
	
	
	/**
	 * Fetches detailed information of a specific complaint when analyzing complaint trends.
	 * @param complaintId A String uniquely identifying the complaint.
	 * @return ComplaintDetails object containing detailed information of the complaint.
	 */
	public ComplaintDetails getComplaintDetail(String complaintId) {
	  ComplaintDetails complaintDetails = null;
	  String sql = "SELECT * FROM complaints WHERE id = ?;";
	  try (Connection conn = DatabaseUtility.connect();
	       PreparedStatement pstmt = conn.prepareStatement(sql)) {
	    pstmt.setString(1, complaintId);
	    ResultSet rs = pstmt.executeQuery();
	    if (rs.next()) {
	      complaintDetails = new ComplaintDetails();
	      complaintDetails.setId(rs.getInt("id"));
	      complaintDetails.setComplaintDescription(rs.getString("complaint_description"));
	      complaintDetails.setComplaintDate(rs.getDate("complaint_date"));
	      complaintDetails.setComplaintStatus(ComplaintTrends.ComplaintStatus.valueOf(rs.getString("complaint_status")));
	      complaintDetails.setComplaintPriority(ComplaintTrends.ComplaintPriorities.valueOf(rs.getString("complaint_priority")));
	      // Additional fields can be set here as needed
	    }
	    rs.close();
	  } catch (SQLException e) {
	    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error fetching complaint detail", e);
	  } finally {
	    DatabaseUtility.disconnect(conn);
	  }
	  return complaintDetails;
	}
	
	/**
	 * Updates the status of a specific complaint identified by its unique ID.
	 * Used in 'Complaint Trend Analytics' and 'Review and Closure Module' sections.
	 * @param complaintId Unique identifier of the complaint to be updated.
	 * @param newStatus New status to be assigned to the complaint.
	 * @return boolean indicating success or failure of the update operation.
	 */
	public boolean updateComplaintStatus(String complaintId, String newStatus) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean updateStatus = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE complaints SET status = ? WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, newStatus);
	        pstmt.setString(2, complaintId);
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            updateStatus = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ComplaintTrendsDAO.class.getName()).log(Level.SEVERE, "Error updating complaint status", e);
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	        } catch (SQLException e) {
	            Logger.getLogger(ComplaintTrendsDAO.class.getName()).log(Level.SEVERE, "Failed to close PreparedStatement", e);
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	    return updateStatus;
	}
	
	/**
	 * Retrieves a list of all complaint categories to populate dropdown filters.
	 * Used in the 'Complaint Trend Analytics' section for categorization-based analysis.
	 * @return List<String> containing all complaint categories from the database.
	 */
	public List<String> getComplaintCategories() {
	    List<String> categories = new ArrayList<>();
	    String query = "SELECT DISTINCT category_name FROM complaint_categories ORDER BY category_name ASC;";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            categories.add(rs.getString("category_name"));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        if (rs != null) try { rs.close(); } catch (SQLException e) { /* Ignored */ }
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* Ignored */ }
	        DatabaseUtility.disconnect(conn);
	    }
	    return categories;
	}
	
	/**
	 * Retrieves a list of complaints that match certain status criteria within a specific time frame,
	 * aiding in the analysis of complaint handling efficiency and effectiveness.
	 * @param statuses A list of status values (e.g., 'New', 'In Progress') to filter complaints by.
	 * @param timeFrame A string specifying the time frame within which the filtered complaints were received.
	 * @param limit An integer specifying the maximum number of records to return for pagination.
	 * @param offset An integer specifying the starting point from which to return records for pagination.
	 * @return List<ComplaintTrends> Matching complaint trends based on the provided parameters.
	 */
	public List<ComplaintTrends> filterComplaintsByStatus(List<String> statuses, String timeFrame, int limit, int offset) {
	    Connection conn = DatabaseUtility.connect();
	    List<ComplaintTrends> complaintTrends = new ArrayList<>();
	    String statusesInSql = statuses.stream().map(status -> "'" + status + "'").collect(Collectors.joining(","));
	    String sql = "SELECT * FROM complaint_trends WHERE complaint_status IN (" + statusesInSql + ") AND time_frame = ? LIMIT ? OFFSET ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, timeFrame);
	        stmt.setInt(2, limit);
	        stmt.setInt(3, offset);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            ComplaintTrends trend = new ComplaintTrends();
	            trend.setId(rs.getInt("id"));
	            trend.setTimeFrame(rs.getString("time_frame"));
	            trend.setComplaintCount(rs.getInt("complaint_count"));
	            trend.setComplaintType(rs.getString("complaint_type"));
	            trend.setAverageResolutionTime(rs.getDouble("average_resolution_time"));
	            trend.setCustomerSatisfactionScore(rs.getDouble("customer_satisfaction_score"));
	            trend.setComplaintStatus(ComplaintTrends.ComplaintStatus.valueOf(rs.getString("complaint_status")));
	            trend.setComplaintPriority(ComplaintTrends.ComplaintPriorities.valueOf(rs.getString("complaint_priority")));
	            complaintTrends.add(trend);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ComplaintTrendsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return complaintTrends;
	}
}
