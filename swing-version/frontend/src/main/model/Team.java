package model;

public class Team {
    private int id;
    private String teamName;
    private String members;

    private String branch;
    private String year;
    private String section;
    private String projectName;
    private String description;
    private String dueDate;
    private String dateSubmitted;
    private String rollNumbers;
    private String memberNames;
    private String subjectName;
    private String teamLeaderUsername;
    private String academicYear;
    private String semester;

    // Full constructor
    public Team(int id, String teamName, String members, String subjectName, String teamLeaderUsername,
                String branch, String year, String section, String academicYear, String semester) {
        this.id = id;
        this.teamName = teamName;
        this.members = members;
        this.subjectName = subjectName;
        this.teamLeaderUsername = teamLeaderUsername;
        this.branch = branch;
        this.year = year;
        this.section = section;
        this.academicYear = academicYear;
        this.semester = semester;
    }

    // Basic constructor (legacy use)
    public Team(int id, String teamName, String members) {
        this.id = id;
        this.teamName = teamName;
        this.members = members;
    }

    // Getters
    public int getId() { return id; }
    public String getTeamName() { return teamName; }
    public String getMembers() { return members; }
    public String getBranch() { return branch; }
    public String getYear() { return year; }
    public String getSection() { return section; }
    public String getProjectName() { return projectName; }
    public String getDescription() { return description; }
    public String getDueDate() { return dueDate; }
    public String getDateSubmitted() { return dateSubmitted; }
    public String getRollNumbers() { return rollNumbers; }
    public String getMemberNames() { return memberNames; }
    public String getSubjectName() { return subjectName; }
    public String getTeamLeaderUsername() { return teamLeaderUsername; }
    public String getAcademicYear() { return academicYear; }
    public String getSemester() { return semester; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public void setMembers(String members) { this.members = members; }
    public void setBranch(String branch) { this.branch = branch; }
    public void setYear(String year) { this.year = year; }
    public void setSection(String section) { this.section = section; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public void setDescription(String description) { this.description = description; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public void setDateSubmitted(String dateSubmitted) { this.dateSubmitted = dateSubmitted; }
    public void setRollNumbers(String rollNumbers) { this.rollNumbers = rollNumbers; }
    public void setMemberNames(String memberNames) { this.memberNames = memberNames; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setTeamLeaderUsername(String teamLeaderUsername) { this.teamLeaderUsername = teamLeaderUsername; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public void setSemester(String semester) { this.semester = semester; }
}
