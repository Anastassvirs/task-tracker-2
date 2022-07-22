package managers;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected int numberOfTasks;
    protected HashMap<Integer, Task> tasks; // Список обычных задач
    protected HashMap<Integer, Subtask> subtasks; // Список всех подзадач
    protected HashMap<Integer, Epic> epics; // Список эпиков
    protected HistoryManager historyManager;


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
    public Task findEveryTaskByID(int ID) {
        Task task = null;
        if (tasks.get(ID) != null) {
            historyManager.add(tasks.get(ID));
            task = tasks.get(ID);
        } else if (subtasks.get(ID) != null) {
            historyManager.add(subtasks.get(ID));
            task = subtasks.get(ID);
        } else if (epics.get(ID) != null) {
            historyManager.add(epics.get(ID));
            task = epics.get(ID);
        }
        return task;
    }

    @Override
    public Integer addTask(String nameOfTask, String description) {
        Task task = new Task(nameOfTask, description, Status.NEW);
        task.setId(numberOfTasks);
        tasks.put(numberOfTasks, task);
        numberOfTasks++;
        return task.getId();
    }

    @Override
    public Integer addSubtask(String nameOfTask, String description, Integer epicTaskNum) {
        Subtask subtask = new Subtask(nameOfTask, description, Status.NEW, epicTaskNum);
        subtask.setId(numberOfTasks);
        subtasks.put(numberOfTasks, subtask);
        epics.get(epicTaskNum).addSubtask(subtask);
        numberOfTasks++;
        return subtask.getId();
    }

    @Override
    public Integer addEpic(String nameOfTask, String description) {
        Epic epic = new Epic(nameOfTask, description, Status.NEW);
        epic.setId(numberOfTasks);
        epics.put(epic.getId(), epic);
        numberOfTasks++;
        return epic.getId();
    }

    @Override
    public Integer addNewTask(Task task) {
        task.setId(numberOfTasks);
        tasks.put(numberOfTasks, task);
        numberOfTasks++;
        return task.getId();
    }

    @Override
    public Integer addNewSubtask(Subtask subtask) {
        subtask.setId(numberOfTasks);
        subtasks.put(numberOfTasks, subtask);
        epics.get(subtask.getNumberOfEpicTask()).addSubtask(subtask);
        numberOfTasks++;

        // При необходимсоти изменяем статус самого эпика
        updateEpicStatus(epics.get(subtask.getNumberOfEpicTask()));

        return subtask.getId();
    }

    @Override
    public Integer addNewEpic(Epic epic) {
        epic.setId(numberOfTasks);
        epics.put(epic.getId(), epic);
        numberOfTasks++;
        return epic.getId();
    }

    @Override
    public void deleteTaskByNum(Integer ID) {
        try {
            historyManager.remove(ID);
        } catch (NullPointerException e){}
        tasks.remove(ID);
    }

    @Override
    public void deleteSubtaskByNum(Integer ID) {
        try {
            historyManager.remove(ID);
        } catch (NullPointerException e){}
        subtasks.remove(ID);
    }

    @Override
    public void deleteEpicByNum(Integer ID) {
        try {
            historyManager.remove(ID);
        } catch (NullPointerException e){}
        for (Subtask subtask: getSubtasksFromEpic(ID)) {
            deleteSubtaskByNum(subtask.getId());
        }
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

    @Override
    public List<Task> history() {
        return historyManager.getHistory();
    }

    public String getNumbsFromHistory() {
        return historyManager.toString();
    }

    // Проводится проверка на необходимость изменения статуса эпика с возможностью изменения
    public void updateEpicStatus(Epic epic) {
        Status status = Status.IN_PROGRESS;

        if (epic.isAllSubtasksDone()) {
            status = Status.DONE;
        } else if (epic.getsubtasks().isEmpty() || epic.isAllSubtasksNew()) {
            status = Status.NEW;
        }
        Epic newEpic = new Epic(epic.getTaskName(), epic.getDescription(), status);
        newEpic.setId(epic.getId());
        for (Subtask subtask: epic.getsubtasks().values()) {
            newEpic.addSubtask(subtask);
        }
        epics.put(epic.getId(), newEpic);
    }
}
