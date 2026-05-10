/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controllers;

import dao.EnrollmentDAO;
import dao.EnrollmentDAOImpl;
import models.Enrollment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author TOP
 */

public class EnrollmentController implements Initializable {
    
    @FXML private TextField studentIdField;
    @FXML private TextField courseIdField;
    @FXML private DatePicker enrollmentDatePicker;
    @FXML private TableView<Enrollment> enrollmentTable;
    @FXML private TableColumn<Enrollment, Integer> colEnrollmentId;
    @FXML private TableColumn<Enrollment, Integer> colStudentId;
    @FXML private TableColumn<Enrollment, String> colStudentName;
    @FXML private TableColumn<Enrollment, Integer> colCourseId;
    @FXML private TableColumn<Enrollment, String> colCourseTitle;
    @FXML private TableColumn<Enrollment, LocalDate> colDate;
    @FXML private Label statusLabel;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    
    private EnrollmentDAO enrollmentDAO;
    private ObservableList<Enrollment> enrollmentList;
    private Enrollment selectedEnrollment;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        enrollmentDAO = new EnrollmentDAOImpl();
        enrollmentList = FXCollections.observableArrayList();
        
        // ربط الجدول مع القائمة
        enrollmentTable.setItems(enrollmentList);
        
        setupTableColumns();
        loadAllEnrollments();
        setupTableSelection();
        enrollmentDatePicker.setValue(LocalDate.now());
    }
    
    private void setupTableColumns() {
        colEnrollmentId.setCellValueFactory(new PropertyValueFactory<>("enrollmentId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseTitle.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("enrollmentDate"));
    }
    
    private void setupTableSelection() {
        enrollmentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedEnrollment = newSelection;
                studentIdField.setText(String.valueOf(selectedEnrollment.getStudentId()));
                courseIdField.setText(String.valueOf(selectedEnrollment.getCourseId()));
                enrollmentDatePicker.setValue(selectedEnrollment.getEnrollmentDate());
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
            } else {
                updateButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });
    }
    
    private void loadAllEnrollments() {
        try {
            List<Enrollment> enrollments = enrollmentDAO.getAllEnrollments();
            enrollmentList.clear();
            enrollmentList.addAll(enrollments);
            
            if (enrollmentList.isEmpty()) {
                statusLabel.setText("📋 No enrollments found");
                statusLabel.setStyle("-fx-text-fill: orange;");
            } else {
                statusLabel.setText("✅ Total enrollments: " + enrollmentList.size());
                statusLabel.setStyle("-fx-text-fill: green;");
            }
        } catch (Exception e) {
            showError("Error loading enrollments: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleAddEnrollment() {
        if (!validateInputs()) return;
        
        try {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudentId(Integer.parseInt(studentIdField.getText()));
            enrollment.setCourseId(Integer.parseInt(courseIdField.getText()));
            enrollment.setEnrollmentDate(enrollmentDatePicker.getValue());
            
            enrollmentDAO.addEnrollment(enrollment);
            clearFields();
            loadAllEnrollments();  // تحديث الجدول
            showSuccess("Enrollment added successfully!");
            
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }
    
    @FXML
    private void handleUpdateEnrollment() {
        if (selectedEnrollment == null) {
            showError("Please select an enrollment to update");
            return;
        }
        
        if (!validateInputs()) return;
        
        try {
            selectedEnrollment.setStudentId(Integer.parseInt(studentIdField.getText()));
            selectedEnrollment.setCourseId(Integer.parseInt(courseIdField.getText()));
            selectedEnrollment.setEnrollmentDate(enrollmentDatePicker.getValue());
            
            enrollmentDAO.updateEnrollment(selectedEnrollment);
            clearFields();
            loadAllEnrollments();  // تحديث الجدول
            showSuccess("Enrollment updated successfully!");
            
        } catch (Exception e) {
            showError("Error updating enrollment: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleDeleteEnrollment() {
        if (selectedEnrollment == null) {
            showError("Please select an enrollment to delete");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Enrollment");
        alert.setContentText("Are you sure you want to delete this enrollment?");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                enrollmentDAO.deleteEnrollment(selectedEnrollment.getEnrollmentId());
                clearFields();
                loadAllEnrollments();  // تحديث الجدول
                showSuccess("Enrollment deleted successfully!");
                
            } catch (Exception e) {
                showError("Error deleting enrollment: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleRefresh() {
        loadAllEnrollments();
        clearFields();
        showSuccess("Data refreshed!");
    }
    
    private boolean validateInputs() {
        if (studentIdField.getText().isEmpty()) {
            showError("Student ID is required");
            return false;
        }
        if (courseIdField.getText().isEmpty()) {
            showError("Course ID is required");
            return false;
        }
        if (enrollmentDatePicker.getValue() == null) {
            showError("Enrollment date is required");
            return false;
        }
        
        try {
            Integer.parseInt(studentIdField.getText());
            Integer.parseInt(courseIdField.getText());
        } catch (NumberFormatException e) {
            showError("Student ID and Course ID must be numbers");
            return false;
        }
        return true;
    }
    
    private void clearFields() {
        studentIdField.clear();
        courseIdField.clear();
        enrollmentDatePicker.setValue(LocalDate.now());
        selectedEnrollment = null;
        enrollmentTable.getSelectionModel().clearSelection();
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    
    private void showError(String message) {
        statusLabel.setText("❌ " + message);
        statusLabel.setStyle("-fx-text-fill: red;");
    }
    
    private void showSuccess(String message) {
        statusLabel.setText("✅ " + message);
        statusLabel.setStyle("-fx-text-fill: green;");
    }
}