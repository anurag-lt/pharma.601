package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class ReportsDAO {

	
	/**
	 * Retrieves a list of all reports from the database without applying any filters.
	 * This method is used in the Regulatory Reporting Management page, specifically within the Report Submissions Table section, to display an overview of all generated reports.
	 * @return List<Reports> A list of all reports in the database.
	 */
	public List<Reports> fetchAllReports() {
	    List<Reports> reportsList = new ArrayList<>();
	    String query = "SELECT * FROM reports";
	    try (Connection conn = DatabaseUtility.connect();
	         PreparedStatement stmt = conn.prepareStatement(query);) {
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Reports report = new Reports();
	            report.setId(rs.getInt("id"));
	            report.setReportType(Reports.DocumentTypes.valueOf(rs.getString("report_type"))); // Assuming you have a method to map string to Enum
	            report.setStartDate(rs.getDate("start_date"));
	            report.setEndDate(rs.getDate("end_date"));
	            report.setSummaryOfFindings(rs.getString("summary_of_findings"));
	            // Assuming Complaints class has a method to fetch by ID
	            report.setFkComplaintId(new Complaints(rs.getInt("fk_complaint_id")));
	            reportsList.add(report);
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect();
	    }
	    return reportsList;
	}
	
	/**
	 * Updates the status of a specific report identified by its reportId.
	 * This method is utilized in the Regulatory Reporting Management page,
	 * particularly for actions like submitting a report (Report Submission Modal section)
	 * and updating report statuses from the Report Submissions Table section.
	 *
	 * @param reportId The unique identifier of the report whose status is being updated.
	 * @param newStatus The new status to be assigned to the report.
	 * @return boolean indicating the success or failure of the operation.
	 */
	public boolean updateReportStatus(int reportId, String newStatus) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean updateSuccess = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE reports SET report_type = ? WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, newStatus);
	        pstmt.setInt(2, reportId);
	
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            updateSuccess = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	        } catch (SQLException e) {
	            Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, e);
	        }
	    }
	    return updateSuccess;
	}
	
	/*
	 * Generates a new report based on the provided details such as report type, start and end dates, linked complaint ID, and summary of findings.
	 * Used in the Report Generation Form section of the Regulatory Reporting Management page for document preparation and submission.
	 */
	public Reports generateReport(DocumentTypes reportType, Date startDate, Date endDate, Complaints fkComplaintId, String summaryOfFindings) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO reports (report_type, start_date, end_date, complaint_id, summary_of_findings) VALUES (?, ?, ?, ?, ?) RETURNING id;";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, reportType.name());
	        pstmt.setDate(2, startDate);
	        pstmt.setDate(3, endDate);
	        pstmt.setInt(4, fkComplaintId != null ? fkComplaintId.getId() : null);
	        pstmt.setString(5, summaryOfFindings);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            Reports report = new Reports();
	            report.setId(rs.getInt("id"));
	            report.setReportType(reportType);
	            report.setStartDate(startDate);
	            report.setEndDate(endDate);
	            report.setFkComplaintId(fkComplaintId);
	            report.setSummaryOfFindings(summaryOfFindings);
	            return report;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
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
	    return null;
	}
	
	/**
	 * Permanently removes a report from the database by its reportId.
	 * This function is primarily used in the Report Submissions Table section
	 * of the Regulatory Reporting Management page for managing the report records.
	 *
	 * @param reportId The unique identifier of the report to be deleted.
	 * @return boolean indicating success or failure of the operation.
	 */
	public boolean deleteReport(int reportId) {
	    String sql = "DELETE FROM reports WHERE id = ?;";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean result = false;
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, reportId);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            result = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e); }
	        DatabaseUtility.disconnect(conn);
	    }
	    return result;
	}
	
	/**
	 * Fetches detailed information about a specific report based on the reportId.
	 * This method supports activities in both the Report Submissions Table
	 * and Report Submission Modal sections of the Regulatory Reporting Management page,
	 * facilitating report review and submission processes.
	 *
	 * @param reportId The unique identifier of the report whose detailed information is requested.
	 * @return A Reports object containing detailed information of the report.
	 */
	public Reports fetchReportDetails(int reportId) {
	    Connection conn = DatabaseUtility.connect();
	    Reports report = new Reports();
	    String sql = "SELECT * FROM reports WHERE id = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, reportId);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            report.setId(rs.getInt("id"));
	            report.setReportType(Reports.DocumentTypes.valueOf(rs.getString("report_type")));
	            report.setStartDate(rs.getDate("start_date"));
	            report.setEndDate(rs.getDate("end_date"));
	            report.setSummaryOfFindings(rs.getString("summary_of_findings"));
	            // Assumes Complaints class has similar structure
	            Complaints complaint = new Complaints();
	            complaint.setId(rs.getInt("fk_complaint_id"));
	            report.setFkComplaintId(complaint);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ReportsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	    return report;
	}
}
