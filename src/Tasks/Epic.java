package Tasks;

import java.util.HashMap;

// Класс, описывающий эпик
public class Epic extends Task {
    HashMap<Integer, Progress> subtasksStatuses; // Список принадлежащих подзадач с указанием статуса

    public Epic(String taskName, String description, Integer id, Progress progressStatus,
                HashMap<Integer, Progress> subtasksStatuses) {
        super(taskName, description, id, progressStatus);
        this.subtasksStatuses = subtasksStatuses;
    }

    // Проверка, закрыты ли все подзадачи
    public boolean isAllSubtasksDone() {
        boolean isDone = true;

        for (Progress subtask: subtasksStatuses.values()) {
            if (subtask != Progress.DONE) {
                isDone = false;
                break;
            }
        }
        return isDone;
    }

    // Проверка, являются ли все подзадачи новыми
    public boolean isAllSubtasksNew() {
        boolean isNew = true;

        for (Progress subtask: subtasksStatuses.values()) {
            if (subtask != Progress.NEW) {
                isNew = false;
                break;
            }
        }
        return isNew;
    }

    public HashMap<Integer, Progress> getsubtasksStatuses() {
        return subtasksStatuses;
    }

    // Устанавливает новое значение статуса подзадачи
    public void setSubtaskStatus(Integer ID, Progress status) {
        subtasksStatuses.remove(ID);
        subtasksStatuses.put(ID, status);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasksStatuses=" + subtasksStatuses +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", progressStatus=" + progressStatus +
                '}';
    }
}