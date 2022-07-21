package tasks;

// Класс, описывающий подзадачи
public class Subtask extends Task {
    private Integer numberOfEpicTask; // Уникальный номер соответствующего подзадаче эпика

    public Subtask(String taskName, String description, Status status, Integer numberOfEpicTask) {
        super(taskName, description, status);
        this.numberOfEpicTask = numberOfEpicTask;
    }

    public Integer getNumberOfEpicTask() {
        return numberOfEpicTask;
    }

    @Override
    public String toString() {
        return id +
                "," + Types.SUBTASK +
                "," + taskName +
                "," + progressStatus +
                "," + description +
                "," + numberOfEpicTask
                ;
    }
}
