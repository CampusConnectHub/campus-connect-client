package model;

public class SubjectProjectConfig {
    private String subjectName;
    private String facultyUsername;
    private String academicYear;
    private String semester;
    private String branch;
    private String section;
    private String year;
    private int maxTeamMembers;
    private int maxFileUploads;
    private boolean isLive;

    public SubjectProjectConfig(String subjectName, String facultyUsername, String academicYear, String semester,
                                String branch, String section, String year, int maxTeamMembers, int maxFileUploads, boolean isLive) {
        this.subjectName = subjectName;
        this.facultyUsername = facultyUsername;
        this.academicYear = academicYear;
        this.semester = semester;
        this.branch = branch;
        this.section = section;
        this.year = year;
        this.maxTeamMembers = maxTeamMembers;
        this.maxFileUploads = maxFileUploads;
        this.isLive = isLive;
    }

    // Getters
    public String getSubjectName() { return subjectName; }
    public String getFacultyUsername() { return facultyUsername; }
    public String getAcademicYear() { return academicYear; }
    public String getSemester() { return semester; }
    public String getBranch() { return branch; }
    public String getSection() { return section; }
    public String getYear() { return year; }
    public int getMaxTeamMembers() { return maxTeamMembers; }
    public int getMaxFileUploads() { return maxFileUploads; }
    public boolean isLive() { return isLive; }

    // Setters
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setFacultyUsername(String facultyUsername) { this.facultyUsername = facultyUsername; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public void setSemester(String semester) { this.semester = semester; }
    public void setBranch(String branch) { this.branch = branch; }
    public void setSection(String section) { this.section = section; }
    public void setYear(String year) { this.year = year; }
    public void setMaxTeamMembers(int maxTeamMembers) { this.maxTeamMembers = maxTeamMembers; }
    public void setMaxFileUploads(int maxFileUploads) { this.maxFileUploads = maxFileUploads; }
    public void setLive(boolean live) { isLive = live; }
}
