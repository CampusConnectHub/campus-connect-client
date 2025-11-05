package model;

public class User {
    private int id;
    private String username;
    private String role;
    private String name;
    private String rollNumber;
    private String phone;
    private String email;
    private String branch;
    private String year;
    private String section;
    private String academicYear;

    public User(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public User(int id, String username, String role, String name) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.name = name;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getName() { return name; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setRole(String role) { this.role = role; }
    public void setName(String name) { this.name = name; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
}
