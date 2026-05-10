/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import models.Enrollment;
import java.util.List;

/**
 *
 * @author TOP
 */

public interface EnrollmentDAO {
    void addEnrollment(Enrollment enrollment) throws Exception;
    void updateEnrollment(Enrollment enrollment) throws Exception;
    void deleteEnrollment(int enrollmentId) throws Exception;
    List<Enrollment> getAllEnrollments() throws Exception;
    boolean isDuplicateEnrollment(int studentId, int courseId) throws Exception;
}