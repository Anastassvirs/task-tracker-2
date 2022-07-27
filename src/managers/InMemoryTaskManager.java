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
        return this.tasks.get(ID);
    }

    @Override
    public Subtask findSubtaskByID(int ID) {
        historyManager.add(subtasks.get(ID));
        return this.subtasks.get(ID);
    }

    @Override
    public Epic findEpicByID(int ID) {
        historyManager.add(epics.get(ID));
        return this.epics.get(ID);
    }

    @Override
    public Task findEveryTaskByID(int ID) {
        Task task = null;
        if (this.tasks.get(ID) != null) {
            historyManager.add(this.tasks.get(ID));
            task = this.tasks.get(ID);
        } else if (this.subtasks.get(ID) != null) {
            historyManager.add(this.subtasks.get(ID));
            task = this.subtasks.get(ID);
        } else if (this.epics.get(ID) != null) {
            historyManager.add(this.epics.get(ID));
            task = this.epics.get(ID);
        } else {
            throw new NullPointerException();
        }
        return task;
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
        Epic newEpic = new Epic(epic.getTaskName(), epic.getDescription(), status, epic.getDuration(), epic.getStartTime());
        newEpic.setId(epic.getId());
        for (Subtask subtask: epic.getsubtasks().values()) {
            newEpic.addSubtask(subtask);
        }
        epics.put(epic.getId(), newEpic);
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public void setNumberOfTasks(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }
}
