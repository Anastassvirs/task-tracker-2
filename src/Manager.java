import Tasks.Epic;
import Tasks.Progress;
import Tasks.Subtask;
import Tasks.Task;

import java.util.HashMap;

public class Manager {
    int numberOfTasks;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Subtask> subtasks;
    private HashMap<Integer, Epic> epics;

    public Manager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        numberOfTasks = 0;
    }

    public HashMap<Integer, Task> getAllTasks() {
        return tasks;
    }

    public HashMap<Integer, Subtask> getAllSubtasks() {
        return subtasks;
    }

    public HashMap<Integer, Epic> getAllEpics() {
        return epics;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
    }

    public Task findTaskByID(int ID) {
        return tasks.get(ID);
    }

    public Subtask findSubtaskByID(int ID) {
        return subtasks.get(ID);
    }

    public Epic findEpicByID(int ID) {
        return epics.get(ID);
    }

    public void addTask(Task task) {
        tasks.put(numberOfTasks, task);
        numberOfTasks++;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(numberOfTasks, subtask);
        numberOfTasks++;
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void deleteTask(Task task) {
        tasks.remove(task.getId());
    }

    public void deleteSubtask(Subtask subtask) {
        subtasks.remove(subtask.getId());
    }

    public void deleteEpic(Epic epic) {
        epics.remove(epic.getId());
    }

    public HashMap<Integer, Subtask> getSubtasks(Epic epic) {
        HashMap<Integer, Subtask> subtasks = new HashMap<>();
        for (Integer ID: epic.getsubtasksStatuses().keySet()) {
            subtasks.put(ID, findSubtaskByID(ID));
        }
        return subtasks;
    }

    public void updateTaskStatus(Task task, Progress progress) {
        Task newTask = new Task(task.getTaskName(), task.getDescription(), task.getId(), progress);
        tasks.remove(task.getId());
        tasks.put(task.getId(), newTask);
    }

    public void updateSubtaskStatus(Subtask subtask, Progress progress) {
        subtasks.remove(subtask.getId());
        Subtask newSubtask = new Subtask(subtask.getTaskName(), subtask.getDescription(), subtask.getId(), progress, subtask.getNumberOfEpicTask());
        subtasks.put(subtask.getId(), newSubtask);
        epics.get(subtask.getNumberOfEpicTask()).setSubtaskStatus(newSubtask.getId(), newSubtask.getProgressStatus());
        updateEpicStatus(epics.get(subtask.getNumberOfEpicTask()));
    }

    public void updateEpicStatus(Epic epic) {
        Progress progress = Progress.IN_PROGRESS;
        if (epic.isAllSubtasksDone()) {
            progress = Progress.DONE;
        } else if (epic.getsubtasksStatuses().isEmpty() || epic.isAllSubtasksNew()) {
            return;
        }
        epics.remove(epic.getId());
        Epic newEpic = new Epic(epic.getTaskName(), epic.getDescription(), epic.getId(), progress, epic.getsubtasksStatuses());
        epics.put(epic.getId(), newEpic);
    }
}
