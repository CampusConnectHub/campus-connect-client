# Campus Connect Client 🎓

Campus Connect is a role-based desktop GUI application designed to streamline campus interactions among Admins, Faculty, and Students. Built using Java AWT/Swing, it offers modular dashboards, event management, notice boards, and feedback systems — ideal for second-year DBMS/PBL projects.

---

## 🚀 Features

- 🔐 **Login System** with role-based redirection (Admin, Faculty, Student)
- 🗂️ **Modular Dashboards** for each role
  - Admin: Manage events, Post notices, View feedback
  - Faculty: Post notices, Post Events, View/submit feedback
  - Student: View notices/events, submit feedback
- 📢 **Notice Board** with role-based posting
- 📅 **Event Manager & Viewer**
- 💬 **Feedback Panel** with role-aware access
- ✅ Designed for clarity, modularity, and 3rd Normal Form (3NF) DBMS compliance

---

## 🛠️ Technologies Used

- **Java AWT/Swing** for GUI
- **MySQL** for backend database
- **JDBC** for database connectivity
- **GitHub** for version control and collaboration

---

## 📦 Folder Structure

```
frontend/
└── src/
    └── main/
        ├── CampusConnect.java
        └── ui/
            ├── LoginScreen.java
            ├── AdminDashboard.java
            ├── FacultyDashboard.java
            ├── StudentDashboard.java
            ├── NoticeBoard.java
            ├── FeedbackPanel.java
            ├── EventManager.java
            └── EventViewer.java
```

---

## 🧩 Database Schema (3 -Normal Forms) [under Development..]

- `users(user_id, username, password, role)`
- `feedback(feedback_id, user_id, feedback_text, submitted_at)`
- `notices(notice_id, posted_by, notice_text, posted_at)`
- `events(event_id, title, description, created_by, created_at)`

All tables are normalized to **Third Normal Form (3NF)** for DBMS compliance.

---

## 🧪 Setup Instructions

1. Clone the repo:
   ```bash
   git clone https://github.com/CampusConnectHub/campus-connect-client.git
   ```

2. Add MySQL Connector/J `.jar` to your classpath

3. Create the MySQL database:
   ```sql
   CREATE DATABASE campusconnect;
   ```

4. Run `CampusConnect.java` to launch the GUI

---

## 📚 Academic Scope

This project is designed for:
- DBMS Lab (3NF schema design)
- PBL (Project-Based Learning)
- Java GUI development
- Full-stack integration (Java + MySQL)

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 🤝 Contributors

- [Rishit Ghosh](https://github.com/rajghosh06-dev), [Shaik Zaheer Abbas](https://github.com/Starcloud-retro) (Lead Developer)
- [Md. Abdul Rayain](https://github.com/rayainwarrior-dev)
- Sitha Sai Kumar
- [CampusConnectHub Team](https://github.com/CampusConnectHub)

---

## 🌐 Repository

[Campus Connect Client on GitHub](https://github.com/CampusConnectHub/campus-connect-client)

