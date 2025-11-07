# CampusConnect Client Setup Guide

[![Java 24](https://img.shields.io/badge/Java-24-blue.svg)](https://www.oracle.com/java/)
[![Swing UI](https://img.shields.io/badge/UI-Swing-green.svg)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![MySQL](https://img.shields.io/badge/Database-MySQL-blue.svg)](https://www.mysql.com/)
[![JDBC](https://img.shields.io/badge/Backend-JDBC-yellow.svg)](https://docs.oracle.com/javase/tutorial/jdbc/)
[![License MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Build: Stable](https://img.shields.io/badge/build-Stable-brightgreen.svg)]()

Welcome to the CampusConnect Client! This guide will help you get started with setting up the project locally for development or contribution.

---

## Prerequisites

Make sure you have the following installed:

- **Java Development Kit (JDK)**: Version 24+
- **MySQL Server**: Running locally or remotely
- **IDE**: IntelliJ IDEA, Eclipse, or any Java-compatible IDE
- **Git**: For cloning the repository

---

## Project Structure

```
campus-connect-client/
├── src/
│   ├── ui/                  # Swing-based UI components
│   ├── dao/                 # JDBC-based data access layer
│   ├── model/               # Business logic and services
│   └── util/                # DB connection and helpers
├── .github/
│   └── SETUP.md             # This setup guide
├── README.md
└── LICENSE
```

---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/CampusConnectHub/campus-connect-client.git
cd campus-connect-client
```

### 2. Create the Database

```sql
CREATE DATABASE campusconnect;
USE campusconnect;
```

---

## SQL Schema Setup

Run the following queries to create the required tables:

```sql
CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100),
    role ENUM('Admin', 'Faculty', 'Student'),
    name VARCHAR(100),
    email VARCHAR(100),
    branch VARCHAR(50),
    year INT,
    section VARCHAR(10)
);

CREATE TABLE notifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    body TEXT,
    priority ENUM('Normal', 'Urgent'),
    sender_username VARCHAR(50),
    sender_role ENUM('Admin', 'Faculty'),
    audience_type VARCHAR(50),
    audience_payload TEXT,
    status ENUM('Sent', 'Scheduled'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    send_at TIMESTAMP,
    expires_at TIMESTAMP,
    FOREIGN KEY (sender_username) REFERENCES users(username)
);

CREATE TABLE notification_recipients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    notification_id INT,
    username VARCHAR(50),
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (notification_id) REFERENCES notifications(id),
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE notification_attachments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    notification_id INT,
    filename VARCHAR(255),
    mime_type VARCHAR(50),
    size_bytes BIGINT,
    storage_path TEXT,
    FOREIGN KEY (notification_id) REFERENCES notifications(id)
);

CREATE TABLE class_structure (
    username VARCHAR(50) PRIMARY KEY,
    branch VARCHAR(50),
    year INT,
    section VARCHAR(10),
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    message TEXT,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    event_date DATE,
    created_by VARCHAR(50),
    FOREIGN KEY (created_by) REFERENCES users(username)
);

CREATE TABLE assignments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    due_date DATE,
    created_by VARCHAR(50),
    FOREIGN KEY (created_by) REFERENCES users(username)
);

CREATE TABLE submissions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    assignment_id INT,
    username VARCHAR(50),
    file_path TEXT,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (assignment_id) REFERENCES assignments(id),
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    date DATE,
    status ENUM('Present', 'Absent'),
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE teams (
    id INT AUTO_INCREMENT PRIMARY KEY,
    team_name VARCHAR(100),
    project_title VARCHAR(255),
    members TEXT,
    created_by VARCHAR(50),
    FOREIGN KEY (created_by) REFERENCES users(username)
);

CREATE TABLE subject_project_config (
    id INT AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(100),
    branch VARCHAR(50),
    year INT,
    max_team_size INT,
    created_by VARCHAR(50),
    FOREIGN KEY (created_by) REFERENCES users(username)
);

CREATE TABLE global_config (
    config_key VARCHAR(100) PRIMARY KEY,
    config_value TEXT
);

CREATE TABLE notices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    posted_by VARCHAR(50),
    posted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (posted_by) REFERENCES users(username)
);
```

---

## Testing Features

- **Login as Admin/Faculty/Student** to access role-specific dashboards
- **Compose and View Notifications** using JDBC-backed UI
- **Submit Feedback, View Events, Assignments, Attendance** modules

---

## Need Help?

Feel free to open an issue or reach out via [GitHub Discussions](https://github.com/CampusConnectHub/campus-connect-client/discussions)

---

## License

This project is licensed under the [MIT License](../LICENSE)
