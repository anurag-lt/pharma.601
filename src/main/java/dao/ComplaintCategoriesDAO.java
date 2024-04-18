package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class ComplaintCategoriesDAO {

	
	/**
	 * Creates a new complaint category in the system.
	 * @param categoryName The name of the complaint category to be created.
	 * @param description A textual description of the complaint category.
	 */
	public boolean createComplaintCategory(String categoryName, String description) {
	    Connection conn = DatabaseUtility.connect();
	    String query = "INSERT INTO complaint_categories (category_name, description, created_at, updated_at) VALUES (?, ?, NOW(), NOW())";
	    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, categoryName);
	        pstmt.setString(2, description);
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error creating complaint category", e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	}
	
	/**
	 * Retrieves a list of all complaint categories from the database.
	 * This method supports functionality across various parts of the system where
	 * complaint categorization or filtering by category might be required,
	 * facilitating comprehensive analytics and reporting.
	 *
	 * @return A list of ComplaintCategories objects.
	 */
	public List<ComplaintCategories> fetchAllComplaintCategories() {
	    List<ComplaintCategories> categories = new ArrayList<>();
	    String sql = "SELECT * FROM complaint_categories";
	    try (Connection conn = DatabaseUtility.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            ComplaintCategories category = new ComplaintCategories();
	            category.setId(rs.getInt("id"));
	            category.setCategoryName(rs.getString("category_name"));
	            category.setDescription(rs.getString("description"));
	            category.setCreatedAt(rs.getTimestamp("created_at"));
	            category.setUpdatedAt(rs.getTimestamp("updated_at"));
	            categories.add(category);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ComplaintCategoriesDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect();
	    }
	    return categories;
	}
	
	/**
	 * Allows for the modification of existing complaint categories in the system.
	 *
	 * @param id The unique identifier of the complaint category to be updated.
	 * @param categoryName The new name for the complaint category.
	 * @param description The updated textual description of the complaint category.
	 * @return boolean indicating the success of the update operation.
	 */
	public boolean updateComplaintCategory(int id, String categoryName, String description) {
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    boolean updateSuccess = false;
	    String sql = "UPDATE complaint_categories SET category_name = ?, description = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?;";
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, categoryName);
	        pstmt.setString(2, description);
	        pstmt.setInt(3, id);
	
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            updateSuccess = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ComplaintCategoriesDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	        } catch (SQLException e) {
	            Logger.getLogger(ComplaintCategoriesDAO.class.getName()).log(Level.SEVERE, null, e);
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	    return updateSuccess;
	}
	
	/**
	 * Enables the removal of complaint categories from the system.
	 * @param id The unique identifier of the complaint category to be deleted.
	 */
	public boolean deleteComplaintCategory(int id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean isDeleted = false;
	
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "DELETE FROM complaint_categories WHERE id = ?;";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, id);
	
	        // Execute the delete operation
	        int rowsAffected = pstmt.executeUpdate();
	        if(rowsAffected > 0) {
	            isDeleted = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error deleting complaint category", e);
	    } finally {
	        // Close resources
	        DatabaseUtility.closeQuietly(pstmt);
	        DatabaseUtility.disconnect(conn);
	    }
	    return isDeleted;
	}
	
	/**
	 * Retrieves a specific complaint category from the database based on its unique identifier.
	 * @param id The unique identifier of the complaint category to retrieve.
	 * @return A ComplaintCategories object containing details of the requested category.
	 */
	public ComplaintCategories fetchComplaintCategoryById(int id) {
	    ComplaintCategories category = null;
	    Connection conn = DatabaseUtility.connect();
	    String sql = "SELECT id, category_name, description, created_at, updated_at FROM complaint_categories WHERE id = ?;";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            category = new ComplaintCategories();
	            category.setId(rs.getInt("id"));
	            category.setCategoryName(rs.getString("category_name"));
	            category.setDescription(rs.getString("description"));
	            category.setCreatedAt(rs.getTimestamp("created_at"));
	            category.setUpdatedAt(rs.getTimestamp("updated_at"));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error fetching complaint category by ID", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return category;
	}
}
