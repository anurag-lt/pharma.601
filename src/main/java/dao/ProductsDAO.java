package dao;


import model.Products;
import utils.DatabaseUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.Statement;


public class ProductsDAO {

	
	/**
	 * Fetches a product from the database by its serial number.
	 * @param serialNumber The unique serial number of the product to fetch.
	 * @return A Products object if found, null otherwise.
	 */
	public Products fetchProductBySerialNumber(String serialNumber) {
	    Connection conn = null;
	    Products product = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String query = "SELECT * FROM products WHERE serial_number = ?";
	        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	            pstmt.setString(1, serialNumber);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    product = new Products();
	                    product.setId(rs.getInt("id"));
	                    product.setProductName(rs.getString("product_name"));
	                    product.setModel(rs.getString("model"));
	                    product.setSerialNumber(rs.getString("serial_number"));
	                }
	            }
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "SQL exception in fetchProductBySerialNumber", ex);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return product;
	}
	
	
	/**
	 * Creates a new product entry or updates an existing one based on serial number.
	 *
	 * @param productName The name of the product.
	 * @param model The model of the product.
	 * @param serialNumber The unique serial number of the product.
	 * @return boolean indicating the success of the operation.
	 */
	public boolean createOrUpdateProduct(String productName, String model, String serialNumber) {
	    Connection conn = DatabaseUtility.connect();
	    try {
	        String queryCheck = "SELECT id FROM products WHERE serial_number = ?";
	        PreparedStatement psCheck = conn.prepareStatement(queryCheck);
	        psCheck.setString(1, serialNumber);
	        ResultSet rs = psCheck.executeQuery();
	        if (rs.next()) {
	            String updateQuery = "UPDATE products SET product_name = ?, model = ? WHERE serial_number = ?";
	            PreparedStatement psUpdate = conn.prepareStatement(updateQuery);
	            psUpdate.setString(1, productName);
	            psUpdate.setString(2, model);
	            psUpdate.setString(3, serialNumber);
	            psUpdate.executeUpdate();
	        } else {
	            String insertQuery = "INSERT INTO products (product_name, model, serial_number) VALUES (?, ?, ?)";
	            PreparedStatement psInsert = conn.prepareStatement(insertQuery);
	            psInsert.setString(1, productName);
	            psInsert.setString(2, model);
	            psInsert.setString(3, serialNumber);
	            psInsert.executeUpdate();
	        }
	        return true;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	}
	
	/*
	 Deletes a product from the database based on its unique identifier.
	 @param id The unique identifier of the product record to be deleted.
	 @return boolean indicating if the deletion was successful.
	*/
	public boolean deleteProduct(int id) {
	  Connection conn = null;
	  PreparedStatement pstmt = null;
	  boolean isDeleted = false;
	  String SQL = "DELETE FROM products WHERE id = ?;";
	
	  try {
	    conn = DatabaseUtility.connect();
	    pstmt = conn.prepareStatement(SQL);
	    pstmt.setInt(1, id);
	    int rowsAffected = pstmt.executeUpdate();
	    if (rowsAffected > 0) {
	      isDeleted = true;
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
	  return isDeleted;
	}
}
