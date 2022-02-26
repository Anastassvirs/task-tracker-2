package Tasks;

public class Subtask extends Task {
    Task epicTask;

    public Subtask(String taskName, String description, Integer id, Progress progressStatus, Task epicTask) {
        super(taskName, description, id, progressStatus);
        this.epicTask = epicTask;
    }

    public Task getEpicTask() {
        return epicTask;
    }
}
