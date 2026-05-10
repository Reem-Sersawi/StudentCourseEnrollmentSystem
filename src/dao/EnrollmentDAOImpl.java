package dao;

import models.Enrollment;
import config.DatabaseConfig;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAOImpl implements EnrollmentDAO {
    
    @Override
    public void addEnrollment(Enrollment enrollment) throws Exception {
        if (isDuplicateEnrollment(enrollment.getStudentId(), enrollment.getCourseId())) {
            throw new Exception("Duplicate enrollment: Student already enrolled in this course");
        }
        
        String sql = "INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, enrollment.getStudentId());
            pstmt.setInt(2, enrollment.getCourseId());
            pstmt.setString(3, enrollment.getEnrollmentDate().toString());
            pstmt.executeUpdate();
        }
    }
    
    @Override
    public void updateEnrollment(Enrollment enrollment) throws Exception {
        String sql = "UPDATE enrollments SET student_id = ?, course_id = ?, enrollment_date = ? WHERE enrollment_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, enrollment.getStudentId());
            pstmt.setInt(2, enrollment.getCourseId());
            pstmt.setString(3, enrollment.getEnrollmentDate().toString());
            pstmt.setInt(4, enrollment.getEnrollmentId());
            pstmt.executeUpdate();
        }
    }
    
    @Override
    public void deleteEnrollment(int enrollmentId) throws Exception {
        String sql = "DELETE FROM enrollments WHERE enrollment_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, enrollmentId);
            pstmt.executeUpdate();
        }
    }
    
    @Override
    public List<Enrollment> getAllEnrollments() throws Exception {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = """
            SELECT e.enrollment_id, e.student_id, e.course_id, e.enrollment_date,
                   COALESCE(s.name, 'Unknown') as student_name, 
                   COALESCE(c.title, 'Unknown') as course_title
            FROM enrollments e
            LEFT JOIN students s ON e.student_id = s.student_id
            LEFT JOIN courses c ON e.course_id = c.course_id
            ORDER BY e.enrollment_id DESC
        """;
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId(rs.getInt("enrollment_id"));
                enrollment.setStudentId(rs.getInt("student_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setStudentName(rs.getString("student_name"));
                enrollment.setCourseTitle(rs.getString("course_title"));
                
                // تجاوز مشكلة التاريخ الفاسد
                try {
                    String dateStr = rs.getString("enrollment_date");
                    if (dateStr != null && dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        enrollment.setEnrollmentDate(LocalDate.parse(dateStr));
                    } else {
                        enrollment.setEnrollmentDate(LocalDate.now());
                    }
                } catch (Exception e) {
                    enrollment.setEnrollmentDate(LocalDate.now());
                }
                
                enrollments.add(enrollment);
            }
        }
        return enrollments;
    }
    
    @Override
    public boolean isDuplicateEnrollment(int studentId, int courseId) throws Exception {
        String sql = "SELECT COUNT(*) FROM enrollments WHERE student_id = ? AND course_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }
}