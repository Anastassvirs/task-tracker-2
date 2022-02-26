import Tasks.Epic;
import Tasks.Progress;
import Tasks.Subtask;
import Tasks.Task;

import java.util.HashMap;

public class Manager {
    int numberOfTasks;
    HashMap<Integer, Task> tasks;
    HashMap<Integer, Subtask> subtasks;
    HashMap<Integer, Epic> epics;

    public Manager() {
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
        numberOfTasks += 1;
        tasks.put(numberOfTasks, task);
    }

    public void addSubtask(Subtask subtask) {
        numberOfTasks += 1;
        subtasks.put(numberOfTasks, subtask);
    }

    public void addEpic(Epic epic) {
        numberOfTasks += 1;
        epics.put(numberOfTasks, epic);
    }

    public void updateTask(Task task) {
        tasks.remove(task.getId());
        tasks.put(task.getId(), task);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.remove(subtask.getId());
        subtasks.put(subtask.getId(), subtask);
    }

    public void updateEpic(Epic epic) {
        epics.remove(epic.getId());
        epics.put(epic.getId(), epic);
    }

    public void deleteTask(int ID) {
        tasks.remove(ID);
    }

    public void deleteSubtask(int ID) {
        subtasks.remove(ID);
    }

    public void deleteEpic(int ID) {
        epics.remove(ID);
    }

    public HashMap<Integer, Subtask> getSubtasks(int ID) {
        Epic epic = epics.get(ID);
        return epic.getSubtasks();
    }

    public void changeTaskStatus(Task task, Progress progress) {
        tasks.remove(task.getId());
        Task newTask = new Task(task.getTaskName(), task.getDescription(), task.getId(), progress);
        tasks.put(task.getId(), newTask);
    }

    public void changeSubtaskStatus(Subtask subtask, Progress progress) {
        subtasks.remove(subtask.getId());
        Subtask newSubtask = new Subtask(subtask.getTaskName(), subtask.getDescription(), subtask.getId(), progress, subtask.getEpicTask());
        subtasks.put(subtask.getId(), newSubtask);
        //тут мб стоит добавить проверку не завершились ли все сабтаски
    }

    public void changeEpicStatus(Epic epic) {
        Progress progress = Progress.IN_PROGRESS;
        if (epic.isAllSubtasksDone()) {
            progress = Progress.DONE;
        } else if (epic.getSubtasks().isEmpty() || epic.isAllSubtasksNew()) {
            progress = Progress.NEW;
        }
        epics.remove(epic.getId());
        Epic newEpic = new Epic(epic.getTaskName(), epic.getDescription(), epic.getId(), progress, epic.getSubtasks());
        epics.put(epic.getId(), newEpic);
    }
}
