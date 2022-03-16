import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager{
    protected int numberOfTasks;
    private HashMap<Integer, Task> tasks; // Список обычных задач
    private HashMap<Integer, Subtask> subtasks; // Список всех подзадач
    private HashMap<Integer, Epic> epics; // Список эпиков
    private HistoryManager historyManager;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        numberOfTasks = 0;
        historyManager = Managers.getDefaultHistory();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> onlyTasks = new ArrayList<>();
        for(Task task: tasks.values()) {
            onlyTasks.add(task);
        }
        return onlyTasks;
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> onlySubtasks = new ArrayList<>();
        for(Subtask subtask: subtasks.values()) {
            onlySubtasks.add(subtask);
        }
        return onlySubtasks;
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> onlyEpics = new ArrayList<>();
        for(Epic epic: epics.values()) {
            onlyEpics.add(epic);
        }
        return onlyEpics;
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        deleteAllSubtasks();
    }

    @Override
    public Task findTaskByID(int ID) {
        historyManager.add(tasks.get(ID));
        return tasks.get(ID);
    }

    @Override
    public Subtask findSubtaskByID(int ID) {
        historyManager.add(subtasks.get(ID));
        return subtasks.get(ID);
    }

    @Override
    public Epic findEpicByID(int ID) {
        historyManager.add(epics.get(ID));
        return epics.get(ID);
    }

    @Override
    public Integer addTask(String nameOfTask, String description) {
        Task task = new Task(nameOfTask, description, numberOfTasks, Status.NEW);
        tasks.put(numberOfTasks, task);
        numberOfTasks++;
        return task.getId();
    }

    @Override
    public Integer addSubtask(String nameOfTask, String description, Integer epicTaskNum) {
        Subtask subtask = new Subtask(nameOfTask, description, numberOfTasks, Status.NEW, epicTaskNum);
        subtasks.put(numberOfTasks, subtask);
        epics.get(epicTaskNum).addSubtask(subtask);
        numberOfTasks++;
        return subtask.getId();
    }

    @Override
    public Integer addEpic(String nameOfTask, String description) {
        Epic epic = new Epic(nameOfTask, description, numberOfTasks, Status.NEW);
        epics.put(epic.getId(), epic);
        numberOfTasks++;
        return epic.getId();
    }

    @Override
    public void deleteTaskByNum(Integer ID) {
        tasks.remove(ID);
    }

    @Override
    public void deleteSubtaskByNum(Integer ID) {
        subtasks.remove(ID);
    }

    @Override
    public void deleteEpicByNum(Integer ID) {
        epics.remove(ID);
    }

    @Override
    public ArrayList<Subtask> getSubtasksFromEpic(Integer numberOfEpic) {
        Epic epic = epics.get(numberOfEpic);
        ArrayList<Subtask> subtasks = new ArrayList<>();

        for (Subtask subtask: epic.getsubtasks().values()) {
            subtasks.add(subtask);
        }
        return subtasks;
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

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask.getProgressStatus() != subtasks.get(subtask.getId()).getProgressStatus()) {
            // Обновление статуса подзадачи у соответствующего эпика
            epics.get(subtask.getNumberOfEpicTask()).setSubtask(subtask.getId(), subtask);
            // При необходимсоти изменяем статус самого эпика
            updateEpicStatus(epics.get(subtask.getNumberOfEpicTask()));
        }
        subtasks.put(subtask.getId(), subtask);
    }

    public ArrayDeque<Task> history() {
        return historyManager.getHistory();
    }
}
