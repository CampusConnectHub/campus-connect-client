package model;

public class Student {
    private String name;
    private String rollNumber;
    private String username;
    private String branch;
    private String year;
    private String section;
    private String academicYear;
    private String semester;

    public Student(String name, String rollNumber, String username, String branch, String year,
                   String section, String academicYear, String semester) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.username = username;
        this.branch = branch;
        this.year = year;
        this.section = section;
        this.academicYear = academicYear;
        this.semester = semester;
    }

    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getUsername() { return username; }
    public String getBranch() { return branch; }
    public String getYear() { return year; }
    public String getSection() { return section; }
    public String getAcademicYear() { return academicYear; }
    public String getSemester() { return semester; }

    @Override
    public String toString() {
        return name + " (" + rollNumber + ")";
    }
}
