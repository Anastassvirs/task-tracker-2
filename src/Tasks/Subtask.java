package Tasks;

// Класс, описывающий подзадачи
public class Subtask extends Task {
    Integer numberOfEpicTask; // Уникальный номер соответствующего подзадаче эпика

    public Subtask(String taskName, String description, Integer id, Progress progressStatus, Integer numberOfEpicTask) {
        super(taskName, description, id, progressStatus);
        this.numberOfEpicTask = numberOfEpicTask;
    }

    public Integer getNumberOfEpicTask() {
        return numberOfEpicTask;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "numberOfEpicTask=" + numberOfEpicTask +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", progressStatus=" + progressStatus +
                '}';
    }
}
