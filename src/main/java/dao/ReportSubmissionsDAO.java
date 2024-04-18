package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;

public class ReportSubmissionsDAO {

	
	public boolean submitReport(int reportId, String recipientRegulatoryBody, ReportSubmissions.SubmissionMethods submissionMethod, String notes) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean success = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO report_submissions (submission_date, submission_method, submission_status, fk_report_id, fk_regulatory_body_id, notes) VALUES (?, ?, ?, ?, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
	        pstmt.setString(2, submissionMethod.name());
	        pstmt.setString(3, ReportSubmissions.CommunicationStatus.SENT.name()); // Assuming the default status is SENT upon submission call
	        pstmt.setInt(4, reportId);
	        pstmt.setString(5, recipientRegulatoryBody); // Assuming this is an ID mapped to a name in the application logic
	        pstmt.setString(6, notes);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            success = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ReportSubmissionsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(ReportSubmissionsDAO.class.getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	    return success;
	}
	
	/**
	 * Updates the status of a report's submission process.
	 * Used post-submission attempt to mark the outcome (success or failure) in both the 'Report Submissions Table' and 'Report Submission Modal'.
	 * 
	 * @param reportId The ID of the report for which the status is being updated.
	 * @param newStatus The new status of the report submission.
	 * @return boolean indicating whether the operation was successful.
	 */
	public boolean updateReportStatus(int reportId, ReportSubmissions.CommunicationStatus newStatus) {
	    Connection conn = DatabaseUtility.connect();
	    boolean updateSuccess = false;
	    String query = "UPDATE report_submissions SET submission_status = ? WHERE id = ?";
	
	    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, newStatus.name());
	        pstmt.setInt(2, reportId);
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            updateSuccess = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ReportSubmissionsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	    }
	
	    return updateSuccess;
	}
	
	
	/**
	 * Retrieves detailed information about a specific report submission attempt.
	 * @param submissionId The unique identifier of the report submission record.
	 * @return A ReportSubmissions object containing detailed submission info, or null if not found.
	 */
	public ReportSubmissions fetchReportSubmissionDetails(int submissionId) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    ReportSubmissions submission = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String query = "SELECT * FROM report_submissions WHERE id = ?";
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, submissionId);
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            submission = new ReportSubmissions();
	            submission.setId(rs.getInt("id"));
	            submission.setSubmissionDate(rs.getTimestamp("submission_date"));
	            submission.setSubmissionMethod(ReportSubmissions.SubmissionMethods.valueOf(rs.getString("submission_method")));
	            submission.setSubmissionStatus(ReportSubmissions.CommunicationStatus.valueOf(rs.getString("submission_status")));
	            // Assuming fetchReportById and fetchRegulatoryBodyById are existing methods for obtaining related objects
	            submission.setFkReport(fetchReportById(rs.getInt("fk_report_id")));
	            submission.setFkRegulatoryBody(fetchRegulatoryBodyById(rs.getInt("fk_regulatory_body_id")));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ReportSubmissionsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */ } }
	        if (rs != null) { try { rs.close(); } catch (SQLException e) { /* ignored */ } }
	    }
	    return submission;
	}
	
	
	/**
	 * Logs the outcome of a report submission attempt, capturing the action details, the associated report,
	 * submission status, and specific remarks, used primarily after submitting a report through the
	 * 'Report Submission Modal'.
	 *
	 * @param reportId The ID of the report being logged.
	 * @param status The status result of the submission attempt.
	 * @param submissionLog A detailed log or remarks about the submission attempt.
	 * @return boolean indicating if the logging was successful.
	 */
	public boolean logReportSubmission(int reportId, String status, String submissionLog) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean result = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO report_submission_logs (report_id, submission_status, submission_log) VALUES (?, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, reportId);
	        pstmt.setString(2, status);
	        pstmt.setString(3, submissionLog);
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            result = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(ReportSubmissionsDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(ReportSubmissionsDAO.class.getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	    return result;
	}
}
