package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class ComplaintSubcategoriesDAO {

	
	/**
	 * Retrieves and lists subcategories corresponding to a selected complaint category, aiding in granular classification.
	 * @param categoryId The unique identifier of the complaint category for which related subcategories are fetched.
	 * @return List of ComplaintSubcategories for the given category.
	 */
	public List<ComplaintSubcategories> fetchSubCategoriesByCategoryId(int categoryId) {
	    List<ComplaintSubcategories> subcategories = new ArrayList<>();
	    String sql = "SELECT * FROM complaint_subcategories WHERE fk_category_id = ?;";
	    try (Connection conn = DatabaseUtility.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, categoryId);
	
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                ComplaintSubcategories subcategory = new ComplaintSubcategories();
	                subcategory.setId(rs.getInt("id"));
	                subcategory.setSubCategoryName(rs.getString("sub_category_name"));
	                subcategory.setDescription(rs.getString("description"));
	                subcategory.setCreationDate(rs.getDate("creation_date"));
	                subcategory.setLastUpdated(rs.getTimestamp("last_updated"));
	                subcategories.add(subcategory);
	            }
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ComplaintSubcategoriesDAO.class.getName()).log(Level.SEVERE, "Error fetching subcategories by category ID", e);
	    } finally {
	        DatabaseUtility.disconnect();
	    }
	    return subcategories;
	}
	
	/**
	 * Updates the subcategory of multiple complaints at once, enhancing the efficiency of categorization workflow.
	 * @param complaintIds List of unique identifiers for complaints that need to be updated with a new subcategory.
	 * @param subCategoryId The unique identifier of the new subcategory to be assigned to the listed complaints.
	 * @return boolean indicating whether the update operation was successful.
	 */
	public boolean updateComplaintsSubCategory(List<Long> complaintIds, int subCategoryId) {
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    String updateQuery = "UPDATE complaints SET sub_category_id = ? WHERE id = ?";
	
	    try {
	        conn.setAutoCommit(false);
	        pstmt = conn.prepareStatement(updateQuery);
	        for (Long complaintId : complaintIds) {
	            pstmt.setInt(1, subCategoryId);
	            pstmt.setLong(2, complaintId);
	            pstmt.executeUpdate();
	        }
	        conn.commit();
	        return true;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        try {
	            if (conn != null) {
	                conn.rollback();
	            }
	        } catch (SQLException ex) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	        }
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	}
	
	/**
	 * Creates a new Complaint Subcategory in the database.
	 * @param subCategoryName The name of the complaint subcategory to be created.
	 * @param description A detailed description of the new subcategory's scope and specifics.
	 * @param categoryId The unique identifier of the complaint category under which the new subcategory is nested.
	 * @return Boolean indicating the success of the operation.
	 */
	public boolean createComplaintSubCategory(String subCategoryName, String description, int categoryId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean isSuccess = false;
	    String sql = "INSERT INTO complaint_subcategories (sub_category_name, description, fk_category_id) VALUES (?, ?, ?)";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, subCategoryName);
	        pstmt.setString(2, description);
	        pstmt.setInt(3, categoryId);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            isSuccess = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            DatabaseUtility.disconnect(conn);
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        }
	    }
	    return isSuccess;
	}
	
	/**
	 * Deletes a specific complaint subcategory from the database.
	 * @param subCategoryId The unique identifier of the complaint subcategory to be deleted.
	 */
	public boolean deleteComplaintSubCategory(int subCategoryId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = "DELETE FROM complaint_subcategories WHERE id = ?;";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, subCategoryId);
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(ComplaintSubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	        } catch (SQLException e) {
	            Logger.getLogger(ComplaintSubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
	        }
	    }
	}
}
