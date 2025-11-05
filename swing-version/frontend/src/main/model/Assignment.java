package model;

public class Assignment {
    private int id;
    private String title;
    private String description;
    private String dueDate;
    private String submissionStatus;
    private String submittedFileName;
    private String submittedTimestamp;
    private String studentUsername;

    // Full constructor including submission details
    public Assignment(int id, String title, String description, String dueDate, String submissionStatus,
                      String submittedFileName, String submittedTimestamp, String studentUsername) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.submissionStatus = submissionStatus;
        this.submittedFileName = submittedFileName;
        this.submittedTimestamp = submittedTimestamp;
        this.studentUsername = studentUsername;
    }

    // Basic constructor (used for creation without submission)
    public Assignment(int id, String title, String description, String dueDate, String submissionStatus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.submissionStatus = submissionStatus;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getSubmissionStatus() {
        return submissionStatus;
    }

    public String getSubmittedFileName() {
        return submittedFileName;
    }

    public String getSubmittedTimestamp() {
        return submittedTimestamp;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setSubmissionStatus(String submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public void setSubmittedFileName(String submittedFileName) {
        this.submittedFileName = submittedFileName;
    }

    public void setSubmittedTimestamp(String submittedTimestamp) {
        this.submittedTimestamp = submittedTimestamp;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }
}
