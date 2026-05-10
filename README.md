<div align="center">

# 🎓 Student Course Enrollment System

### 📚 A Complete JavaFX & MySQL Desktop Application

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://java.com)
[![JavaFX](https://img.shields.io/badge/JavaFX-23-007396?style=for-the-badge&logo=java&logoColor=white)](https://openjfx.io/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://mysql.com)

</div>

---

## 📌 About The Project

This is a **Student Course Enrollment System** developed for university course assignment. The application allows students to manage their course enrollments through an intuitive graphical interface.

**Goal:** Provide a simple yet complete system for course enrollment management.

---

## ✨ Features

| Icon | Feature | Description |
|------|---------|-------------|
| ➕ | **Add Enrollment** | Student can enroll in a course with date |
| ✏️ | **Update Enrollment** | Modify existing enrollment details |
| 🗑️ | **Delete Enrollment** | Remove enrollment with confirmation |
| 📋 | **View All** | Display all enrollments in TableView |
| 🚫 | **Duplicate Prevention** | Prevent same student & course combination |
| 🎨 | **Modern GUI** | Clean design with CSS styling |
| 🗄️ | **MySQL Database** | Persistent data storage |

---

## 🛠️ Technologies Used

<div align="center">

| Technology | Version | Purpose |
|------------|---------|---------|
| ☕ **Java** | 21 | Core programming language |
| 🖥️ **JavaFX** | 23 | GUI framework |
| 🐬 **MySQL** | 8.0 | Database management |
| 💻 **NetBeans** | 23 | IDE |
| 🎨 **CSS** | - | Styling & design |

</div>

---

## 📁 Project Structure
StudentCourseEnrollmentSystem/
│
├── 📁 src/
│ ├── 📁 app/
│ │ └── 📄 MainApp.java ← Application entry point
│ │
│ ├── 📁 config/
│ │ └── 📄 DatabaseConfig.java ← MySQL connection setup
│ │
│ ├── 📁 dao/
│ │ ├── 📄 EnrollmentDAO.java ← DAO interface
│ │ └── 📄 EnrollmentDAOImpl.java ← CRUD implementation
│ │
│ ├── 📁 models/
│ │ └── 📄 Enrollment.java ← Entity model
│ │
│ ├── 📁 controllers/
│ │ └── 📄 EnrollmentController.java ← FXML controller
│ │
│ ├── 📁 views/
│ │ └── 📄 EnrollmentView.fxml ← GUI layout
│ │
│ └── 📁 styles/
│ └── 📄 style.css ← CSS styling
│
├── 📄 database.sql ← Database schema
└── 📄 README.md ← This file
 Structure
