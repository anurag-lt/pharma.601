package dao;


import model.Customers;
import utils.DatabaseUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.Statement;


public class CustomersDAO {

	
	/**
	 * Saves a new customer's details into the database.
	 * @param customer An instance of Customers class containing all necessary customer details.
	 * @return The ID of the saved customer or -1 if the operation failed.
	 */
	public int saveCustomer(Customers customer) {
	    String sql = "INSERT INTO customers (first_name, last_name, email_address, phone_number, register_date) VALUES (?, ?, ?, ?, ?)";
	    try (Connection conn = DatabaseUtility.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        pstmt.setString(1, customer.getFirstName());
	        pstmt.setString(2, customer.getLastName());
	        pstmt.setString(3, customer.getEmailAddress());
	        pstmt.setString(4, customer.getPhoneNumber());
	        pstmt.setDate(5, new java.sql.Date(customer.getRegisterDate().getTime()));
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Creating customer failed, no rows affected.");
	        }
	        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1);
	            } else {
	                throw new SQLException("Creating customer failed, no ID obtained.");
	            }
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect();
	    }
	    return -1;
	}
	
	/**
	 * Checks if a customer already exists in the database by their email address.
	 * Used in the Complaint Submission Form section of the Complaint Submission Form page.
	 * @param emailAddress The email address of the customer to be searched in the database.
	 * @return A Customer object if found, otherwise null.
	 */
	public Customers findCustomerByEmail(String emailAddress) {
	    Connection conn = DatabaseUtility.connect();
	    String sql = "SELECT * FROM customers WHERE email_address = ?;";
	    Customers customer = null;
	
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, emailAddress);
	        ResultSet rs = pstmt.executeQuery();
	
	        if (rs.next()) {
	            customer = new Customers();
	            customer.setId(rs.getInt("id"));
	            customer.setFirstName(rs.getString("first_name"));
	            customer.setLastName(rs.getString("last_name"));
	            customer.setEmailAddress(rs.getString("email_address"));
	            customer.setPhoneNumber(rs.getString("phone_number"));
	            customer.setRegisterDate(rs.getDate("register_date"));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return customer;
	}
	
	/**
	 * Updates an existing customer's details in the database.
	 * This method is utilized in the Complaint Submission Form section
	 * of the Complaint Submission Form page to handle cases where a customer
	 * submits another complaint or updates their contact information.
	 *
	 * @param customer An instance of Customers class containing updated information for an existing customer.
	 */
	public boolean updateCustomer(Customers customer) {
	    Connection conn = DatabaseUtility.connect();
	    boolean updateSuccess = false;
	    String sql = "UPDATE customers SET first_name = ?, last_name = ?, email_address = ?, phone_number = ?, register_date = ? WHERE id = ?;";
	
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, customer.getFirstName());
	        pstmt.setString(2, customer.getLastName());
	        pstmt.setString(3, customer.getEmailAddress());
	        pstmt.setString(4, customer.getPhoneNumber());
	        pstmt.setDate(5, new java.sql.Date(customer.getRegisterDate().getTime()));
	        pstmt.setInt(6, customer.getId());
	
	        int rowsAffected = pstmt.executeUpdate();
	        updateSuccess = rowsAffected > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error updating customer with ID: " + customer.getId(), e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	
	    return updateSuccess;
	}
	
	/**
	 * Retrieves a customer's details based on their unique identifier.
	 * @param id The unique identifier of a customer whose details are to be retrieved from the database.
	 * @return A Customers object containing the customer's details, or null if not found.
	 */
	public Customers getCustomerById(int id) {
	    Connection conn = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Customers customer = null;
	    try {
	        String sql = "SELECT * FROM customers WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            customer = new Customers();
	            customer.setId(rs.getInt("id"));
	            customer.setFirstName(rs.getString("first_name"));
	            customer.setLastName(rs.getString("last_name"));
	            customer.setEmailAddress(rs.getString("email_address"));
	            customer.setPhoneNumber(rs.getString("phone_number"));
	            customer.setRegisterDate(rs.getDate("register_date"));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        }
	    }
	    return customer;
	}
}
