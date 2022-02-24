public class Task {
    private String taskName;
    private String description;
    private int uniqueNumber;
    private Progress progressStatus;


    public Task(String taskName, String description, int uniqueNumber, Progress progressStatus) {
        this.taskName = taskName;
        this.description = description;
        this.uniqueNumber = uniqueNumber;
        this.progressStatus = progressStatus;
    }
}
