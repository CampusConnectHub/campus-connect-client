package model;

public class Attendance {
    private int id;
    private String studentName;
    private String date;
    private boolean present;
    private String academicYear;
    private String branch;
    private String year;
    private String section;
    private String semester; // ✅ Added semester field
    private String timestamp;
    private String markedBy;

    public Attendance(int id, String studentName, String date, boolean present,
                      String academicYear, String branch, String year, String section) {
        this.id = id;
        this.studentName = studentName;
        this.date = date;
        this.present = present;
        this.academicYear = academicYear;
        this.branch = branch;
        this.year = year;
        this.section = section;
    }

    public int getId() { return id; }
    public String getStudentName() { return studentName; }
    public String getDate() { return date; }
    public boolean isPresent() { return present; }
    public String getAcademicYear() { return academicYear; }
    public String getBranch() { return branch; }
    public String getYear() { return year; }
    public String getSection() { return section; }
    public String getSemester() { return semester; } // ✅ Getter
    public String getTimestamp() { return timestamp; }
    public String getMarkedBy() { return markedBy; }

    public void setId(int id) { this.id = id; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setDate(String date) { this.date = date; }
    public void setPresent(boolean present) { this.present = present; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public void setBranch(String branch) { this.branch = branch; }
    public void setYear(String year) { this.year = year; }
    public void setSection(String section) { this.section = section; }
    public void setSemester(String semester) { this.semester = semester; } // ✅ Setter
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public void setMarkedBy(String markedBy) { this.markedBy = markedBy; }
}
