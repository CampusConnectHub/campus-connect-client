package model;

public class Submission {
    private int id;
    private String studentName;
    private String assignmentTitle;
    private String submissionDate;
    private String status;

    public Submission(int id, String studentName, String assignmentTitle, String submissionDate, String status) {
        this.id = id;
        this.studentName = studentName;
        this.assignmentTitle = assignmentTitle;
        this.submissionDate = submissionDate;
        this.status = status;
    }

    public int getId() { return id; }
    public String getStudentName() { return studentName; }
    public String getAssignmentTitle() { return assignmentTitle; }
    public String getSubmissionDate() { return submissionDate; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setAssignmentTitle(String assignmentTitle) { this.assignmentTitle = assignmentTitle; }
    public void setSubmissionDate(String submissionDate) { this.submissionDate = submissionDate; }
    public void setStatus(String status) { this.status = status; }
}
