import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    protected int numberOfTasks;
    private HashMap<Integer, Task> tasks; // Список обычных задач
    private HashMap<Integer, Subtask> subtasks; // Список всех подзадач
    private HashMap<Integer, Epic> epics; // Список эпиков

    public Manager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        numberOfTasks = 0;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> onlyTasks = new ArrayList<>();
        for(Task task: tasks.values()) {
            onlyTasks.add(task);
        }
        return onlyTasks;
    }

    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> onlySubtasks = new ArrayList<>();
        for(Subtask subtask: subtasks.values()) {
            onlySubtasks.add(subtask);
        }
        return onlySubtasks;
    }

    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> onlyEpics = new ArrayList<>();
        for(Epic epic: epics.values()) {
            onlyEpics.add(epic);
        }
        return onlyEpics;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        deleteAllEpics();
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

    public void addTask(String nameOfTask, String description) {
        Task task = new Task(nameOfTask, description, numberOfTasks, Status.NEW);
        tasks.put(numberOfTasks, task);
        numberOfTasks++;
    }

    public void addSubtask(String nameOfTask, String description, Integer epicTaskNum) {
        Subtask subtask = new Subtask(nameOfTask, description, numberOfTasks, Status.NEW, epicTaskNum);
        subtasks.put(numberOfTasks, subtask);
        epics.get(epicTaskNum).addSubtask(subtask);
        numberOfTasks++;
    }

    public void addEpic(String nameOfTask, String description) {
        Epic epic = new Epic(nameOfTask, description, numberOfTasks, Status.NEW);
        epics.put(epic.getId(), epic);
        numberOfTasks++;
    }

    public void deleteTaskByNum(Integer ID) {
        tasks.remove(ID);
    }

    public void deleteSubtaskByNum(Integer ID) {
        subtasks.remove(ID);
    }

    public void deleteEpicByNum(Integer ID) {
        epics.remove(ID);
    }

    public ArrayList<Subtask> getSubtasksFromEpic(Integer numberOfEpic) {
        Epic epic = epics.get(numberOfEpic);
        ArrayList<Subtask> subtasks = new ArrayList<>();

        for (Subtask subtask: epic.getsubtasks().values()) {
            subtasks.add(subtask);
        }
        return subtasks;
    }

    public void updateTaskStatus(Integer taskNubmer, Status status) {
        Task task = tasks.get(taskNubmer);
        Task newTask = new Task(task.getTaskName(), task.getDescription(), task.getId(), status);

        tasks.put(task.getId(), newTask);
    }

    // Обновление статуса подзадачи с возможностью изменения статуса эпика
    public void updateSubtaskStatus(Integer numberOfTask, Status status) {
        Subtask subtask = subtasks.get(numberOfTask);
        Subtask newSubtask = new Subtask(subtask.getTaskName(), subtask.getDescription(), subtask.getId(), status,
                subtask.getNumberOfEpicTask());
        subtasks.put(subtask.getId(), newSubtask);
        // Обновление статуса подзадачи у соответствующего эпика
        epics.get(subtask.getNumberOfEpicTask()).setSubtask(newSubtask.getId(), newSubtask);
        // При необходимсоти изменяем статус самого эпика
        updateEpicStatus(epics.get(subtask.getNumberOfEpicTask()));
    }

    // Проводится проверка на необходимость изменения статуса эпика с возможностью изменения
    public void updateEpicStatus(Epic epic) {
        Status status = Status.IN_PROGRESS;

        if (epic.isAllSubtasksDone()) {
            status = Status.DONE;
        } else if (epic.getsubtasks().isEmpty() || epic.isAllSubtasksNew()) {
            return;
        }
        Epic newEpic = new Epic(epic.getTaskName(), epic.getDescription(), epic.getId(), status);
        for (Subtask subtask: epic.getsubtasks().values()) {
            newEpic.addSubtask(subtask);
        }
        epics.put(epic.getId(), newEpic);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }
}
