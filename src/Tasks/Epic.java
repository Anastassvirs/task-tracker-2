package Tasks;

import java.util.HashMap;

public class Epic extends Task {
    HashMap<Integer, Progress> subtasksStatuses;

    public Epic(String taskName, String description, Integer id, Progress progressStatus,
                HashMap<Integer, Progress> subtasksStatuses) {
        super(taskName, description, id, progressStatus);
        this.subtasksStatuses = subtasksStatuses;
    }

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