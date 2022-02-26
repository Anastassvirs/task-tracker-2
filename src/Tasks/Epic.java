package Tasks;

import java.util.HashMap;

public class Epic extends Task {
    HashMap<Integer, Subtask> subtasks;

    public Epic(String taskName, String description, Integer id, Progress progressStatus, HashMap<Integer, Subtask> subtasks) {
        super(taskName, description, id, progressStatus);
        this.subtasks = subtasks;
    }

    public boolean isAllSubtasksDone() {
        boolean isDone = true;
        for (Subtask subtask: subtasks.values()) {
            if (subtask.getProgressStatus() != Progress.DONE) {
                isDone = false;
                break;
            }
        }
        return isDone;
    }

    public boolean isAllSubtasksNew() {
        boolean isNew = true;
        for (Subtask subtask: subtasks.values()) {
            if (subtask.getProgressStatus() != Progress.NEW) {
                isNew = false;
                break;
            }
        }
        return isNew;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }
}