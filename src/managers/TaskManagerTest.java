package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest <T extends TaskManager>{
    TaskManager taskManager;
    Task task;

    @BeforeEach
    void makeManager() {
        this.taskManager = Managers.getDefault("input.csv");
        task = new Task("Test", "Test description", Status.NEW);
        taskManager.addNewTask(task);
    }

    @Test
    void getAllTasks() {
        Task task1 = new Task("Test getAllTasks", "Test getAllTasks description", Status.NEW);
        taskManager.addNewTask(task1);
        Task task2 = new Task("Test getAllTasks2", "Test getAllTasks description2", Status.NEW);
        taskManager.addNewTask(task2);
        final Task[] tasks = new Task[] {task1, task2};

        final List<Task> tasksInManagerList = taskManager.getAllTasks();
        final Task[] tasksInManager = new Task[] {tasksInManagerList.get(0), tasksInManagerList.get(1)};

        assertNotNull(tasksInManagerList, "Задачи не возвращаются.");
        assertEquals(2, tasksInManagerList.size(), "Неверное количество задач.");

        assertArrayEquals(tasksInManager, tasks, "Массивы задач не совпадают");
    }

    @Test
    void getAllSubtasks() {
        Epic epic1 = new Epic("Test getAllEpics", "Test getAllEpics description",
                Status.NEW);
        taskManager.addNewEpic(epic1);
        Subtask subtask1 = new Subtask("Test getAllSubtasks", "Test getAllSubtasks description",
                Status.NEW, epic1.getId());
        taskManager.addNewSubtask(subtask1);
        Subtask subtask2 = new Subtask("Test getAllSubtasks2", "Test getAllSubtasks description2",
                Status.NEW, epic1.getId());
        taskManager.addNewSubtask(subtask2);
        final Subtask[] subtasks = new Subtask[] {subtask1, subtask2};

        final List<Subtask> subtasksInManagerList = taskManager.getAllSubtasks();
        final Subtask[] subtasksInManager = new Subtask[] {subtasksInManagerList.get(0), subtasksInManagerList.get(1)};

        assertNotNull(subtasksInManagerList, "Подзадачи не возвращаются.");
        assertEquals(2, subtasksInManagerList.size(), "Неверное количество подзадач.");

        assertArrayEquals(subtasksInManager, subtasks, "Массивы подзадач не совпадают");
    }

    @Test
    void getAllEpics() {
        Epic epic1 = new Epic("Test getAllEpics", "Test getAllEpics description", Status.NEW);
        taskManager.addNewEpic(epic1);
        Epic epic2 = new Epic("Test getAllEpics2", "Test getAllEpics description2", Status.NEW);
        taskManager.addNewEpic(epic2);
        final Epic[] epics = new Epic[] {epic1, epic2};

        final List<Epic> epicsInManagerList = taskManager.getAllEpics();
        final Epic[] epicsInManager = new Epic[] {epicsInManagerList.get(0), epicsInManagerList.get(1)};

        assertNotNull(epicsInManagerList, "Эпики не возвращаются.");
        assertEquals(2, epicsInManagerList.size(), "Неверное количество эпиков.");

        assertArrayEquals(epicsInManager, epics, "Массивы эпиков не совпадают");
    }

    @Test
    void deleteAllTasks() {
        Task task1 = new Task("Test deleteAllTasks", "Test deleteAllTasks description",
                Status.NEW);
        taskManager.addNewTask(task1);
        taskManager.deleteAllTasks();

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Список задач не создан, а не пуст.");
        assertEquals(0, tasks.size(), "Ненулевое количество задач.");
    }

    @Test
    void deleteAllSubtasks() {
        Epic epic1 = new Epic("Test deleteAllSubtasks", "Test deleteAllSubtasks description",
                Status.NEW);
        taskManager.addNewEpic(epic1);
        Subtask subtask1 = new Subtask("Test deleteAllSubtasks", "Test deleteAllSubtasks description",
                Status.NEW, epic1.getId());
        taskManager.addNewSubtask(subtask1);
        taskManager.deleteAllSubtasks();

        final List<Subtask> subtasks = taskManager.getAllSubtasks();

        assertNotNull(subtasks, "Список подзадач не создан, а не пуст.");
        assertEquals(0, subtasks.size(), "Ненулевое количество подадач.");
    }

    @Test
    void deleteAllEpics() {
        Epic epic1 = new Epic("Test deleteAllSubtasks", "Test deleteAllSubtasks description",
                Status.NEW);
        taskManager.addNewEpic(epic1);
        Subtask subtask1 = new Subtask("Test deleteAllSubtasks", "Test deleteAllSubtasks description",
                Status.NEW, epic1.getId());
        taskManager.addNewSubtask(subtask1);
        taskManager.deleteAllEpics();

        final List<Subtask> subtasks = taskManager.getAllSubtasks();
        final List<Epic> epics = taskManager.getAllEpics();

        assertNotNull(subtasks, "Список подзадач не создан, а не пуст.");
        assertNotNull(epics, "Список эпиков не создан, а не пуст.");
        assertEquals(0, subtasks.size(), "Ненулевое количество подадач.");
        assertEquals(0, epics.size(), "Ненулевое количество эпиков.");
    }

    @Test
    void findTaskByID() {
        Task task1 = new Task("Test findTaskByID", "Test findTaskByID description", Status.NEW);
        int taskId = taskManager.addNewTask(task1);
        Task task2 = new Task("Test findTaskByID2", "Test findTaskByID description2", Status.NEW);
        taskManager.addNewTask(task2);

        final Task task = taskManager.findTaskByID(taskId);

        assertNotNull(task, "Задача не найдена.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(2, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void findSubtaskByID() {
        Epic epic = new Epic("Test findSubtaskByID", "Test findSubtaskByID description for Epic",
                Status.NEW);
        final int epicId = taskManager.addNewEpic(epic);
        Subtask subtask = new Subtask("Test findSubtaskByID", "Test findSubtaskByID description",
                Status.NEW, epicId);
        final int subtaskId = taskManager.addNewSubtask(subtask);
        Subtask subtask2 = new Subtask("Test findSubtaskByID2", "Test findSubtaskByID description2",
                Status.NEW, epic.getId());
        taskManager.addNewSubtask(subtask2);

        final Subtask findedSubtask = taskManager.findSubtaskByID(subtaskId);

        assertNotNull(findedSubtask, "Подзадача не найдена.");
        assertNotNull(taskManager.getAllEpics().get(findedSubtask.getNumberOfEpicTask()), "Подзадача не найдена.");

        final List<Subtask> subtasks = taskManager.getAllSubtasks();

        assertNotNull(subtasks, "Подзадачи на возвращаются.");
        assertEquals(2, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(findedSubtask, subtasks.get(0), "Подзадачи не совпадают.");
    }

    @Test
    void findEpicByID() {
        Epic epic = new Epic("Test findEpicByID", "Test findEpicByID description", Status.NEW);
        final int epicId = taskManager.addNewEpic(epic);
        Epic epic2 = new Epic("Test findEpicByID2", "Test findEpicByID description2", Status.NEW);
        taskManager.addNewEpic(epic2);

        final Epic findedEpic = taskManager.findEpicByID(epicId);

        assertNotNull(findedEpic, "Эпик не найден.");

        final List<Epic> epics = taskManager.getAllEpics();

        assertNotNull(epics, "Эпики на возвращаются.");
        assertEquals(2, epics.size(), "Неверное количество эпиков.");
        assertEquals(epic, epics.get(0), "Эпики не совпадают.");
    }

    @Test
    void findEveryTaskByID() {
        Epic epic = new Epic("Test findSubtaskByID", "Test findSubtaskByID description for Epic",
                Status.NEW);
        final int epicId = taskManager.addNewEpic(epic);
        Epic epic2 = new Epic("Test findEpicByID2", "Test findEpicByID description2", Status.NEW);
        taskManager.addNewEpic(epic2);
        Subtask subtask = new Subtask("Test findSubtaskByID", "Test findSubtaskByID description",
                Status.NEW, epicId);
        final int subtaskId = taskManager.addNewSubtask(subtask);
        Subtask subtask2 = new Subtask("Test findSubtaskByID2", "Test findSubtaskByID description2",
                Status.NEW, epic.getId());
        taskManager.addNewSubtask(subtask2);
        Task task1 = new Task("Test findTaskByID", "Test findTaskByID description", Status.NEW);
        final int taskId = taskManager.addNewTask(task1);
        Task task2 = new Task("Test findTaskByID2", "Test findTaskByID description2", Status.NEW);
        taskManager.addNewTask(task2);

        final Task findedSubtask = taskManager.findEveryTaskByID(taskId);
        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(findedSubtask, "Задача не найдена.");
        assertEquals(findedSubtask, tasks.get(0), "Подзадачи не совпадают.");
    }


    @Test
    void addTask() {
        final int taskId = taskManager.addTask("Test addTask", "Test addTask description");

        final Task savedTask = taskManager.findTaskByID(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(taskManager.findTaskByID(taskId), savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(taskManager.findTaskByID(taskId), tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addEpic() {
        final int epicId = taskManager.addEpic("Test addEpic", "Test addEpic description");

        final Epic savedEpic = taskManager.findEpicByID(epicId);

        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(taskManager.findEpicByID(epicId), savedEpic, "Эпики не совпадают.");

        final List<Epic> epics = taskManager.getAllEpics();

        assertNotNull(epics, "Эпики на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(taskManager.findEpicByID(epicId), epics.get(0), "Эпики не совпадают.");
    }

    @Test
    void addSubtask() {
        final int epicId = taskManager.addEpic("Test addSubtask",
                "Test addSubtask description for epic");
        final int subtaskId = taskManager.addSubtask("Test addSubtask",
                "Test addSubtask description for epic", epicId);

        final Subtask savedSubtask = taskManager.findSubtaskByID(subtaskId);

        assertNotNull(savedSubtask, "Подзадача не найден.");
        assertEquals(taskManager.findSubtaskByID(subtaskId), savedSubtask, "Подзадачи не совпадают.");

        final List<Subtask> subtasks = taskManager.getAllSubtasks();

        assertNotNull(subtasks, "Подзадачи на возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(taskManager.findSubtaskByID(subtaskId), subtasks.get(0), "Подзадачи не совпадают.");
    }

    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        final int taskId = taskManager.addNewTask(task);

        final Task savedTask = taskManager.findTaskByID(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addNewSubtask() {
        Epic epic = new Epic("Test addNewSubtask", "Test addNewSubtask description for Epic",
                Status.NEW);
        final int epicId = taskManager.addNewEpic(epic);
        Subtask subtask = new Subtask("Test addNewSubtask", "Test addNewSubtask description",
                Status.NEW, epicId);
        final int subtaskId = taskManager.addNewSubtask(subtask);

        final Subtask savedSubtask = taskManager.findSubtaskByID(subtaskId);

        assertNotNull(savedSubtask, "Подзадача не найдена.");
        assertEquals(subtask, savedSubtask, "Подзадачи не совпадают.");

        final List<Subtask> subtasks = taskManager.getAllSubtasks();

        assertNotNull(subtasks, "Подзадачи на возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(subtask, subtasks.get(0), "Подзадачи не совпадают.");
    }

    @Test
    void addNewEpic() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description", Status.NEW);
        final int epicId = taskManager.addNewEpic(epic);

        final Epic savedEpic = taskManager.findEpicByID(epicId);

        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");

        final List<Epic> epics = taskManager.getAllEpics();

        assertNotNull(epics, "Эпики на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(epic, epics.get(0), "Эпики не совпадают.");
    }

    @Test
    void deleteTaskByNum() {
        Task task1 = new Task("Test deleteTaskByNum", "Test deleteTaskByNum description", Status.NEW);
        int taskId = taskManager.addNewTask(task1);
        Task task2 = new Task("Test deleteTaskByNum2", "Test deleteTaskByNum description2",
                Status.NEW);
        taskManager.addNewTask(task2);

        final Task savedTask = taskManager.findTaskByID(taskId);

        assertNotNull(savedTask, "Задача не найдена.");

        taskManager.deleteTaskByNum(taskId);

        NullPointerException ex = assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() {
                taskManager.findTaskByID(taskId);
            }
        });

        assertEquals(null, ex.getMessage());
    }

    @Test
    void deleteSubtaskByNum() {
        Epic epic = new Epic("Test deleteSubtaskByNum", "Tst deleteSubtaskByNum description for epic",
                Status.NEW);
        final int epicId = taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("Test deleteSubtaskByNum",
                "Test deleteSubtaskByNum description", Status.NEW, epicId);
        int subtaskId = taskManager.addNewSubtask(subtask1);
        Subtask subtask2 = new Subtask("Test deleteSubtaskByNum",
                "Test deleteSubtaskByNum description2", Status.NEW, epicId);
        taskManager.addNewSubtask(subtask2);

        taskManager.deleteSubtaskByNum(subtaskId);

        NullPointerException ex = assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() {
                taskManager.findSubtaskByID(subtaskId);
            }
        });

        assertEquals(null, ex.getMessage());
    }

    @Test
    void deleteEpicByNum() {
        Epic epic = new Epic("Test deleteEpicByNum", "Test deleteEpicByNum description", Status.NEW);
        final int epicId = taskManager.addNewEpic(epic);
        Epic epic2 = new Epic("Test deleteEpicByNum2", "Test deleteEpicByNum description2",
                Status.NEW);
        taskManager.addNewEpic(epic2);

        taskManager.deleteEpicByNum(epicId);

        NullPointerException ex = assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() {
                taskManager.findEpicByID(epicId);
            }
        });

        assertEquals(null, ex.getMessage());
    }

    @Test
    void getSubtasksFromEpic() {
        Epic epic1 = new Epic("Test getAllEpics", "Test getAllEpics description",
                Status.NEW);
        taskManager.addNewEpic(epic1);
        Subtask subtask1 = new Subtask("Test getAllSubtasks", "Test getAllSubtasks description",
                Status.NEW, epic1.getId());
        taskManager.addNewSubtask(subtask1);
        Subtask subtask2 = new Subtask("Test getAllSubtasks2", "Test getAllSubtasks description2",
                Status.NEW, epic1.getId());
        taskManager.addNewSubtask(subtask2);
        final Subtask[] subtasks = new Subtask[] {subtask1, subtask2};

        final List<Subtask> subtasksInEpicList = taskManager.getSubtasksFromEpic(epic1.getId());
        final Subtask[] subtasksInEpic = new Subtask[] {subtasksInEpicList.get(0), subtasksInEpicList.get(1)};

        assertNotNull(subtasksInEpicList, "Подзадачи не возвращаются.");
        assertEquals(2, subtasksInEpicList.size(), "Неверное количество подзадач.");

        assertArrayEquals(subtasksInEpic, subtasks, "Массивы подзадач не совпадают");
    }

    @Test
    void getNoSubtasksFromEpic() {
        Epic epic1 = new Epic("Test getAllEpics", "Test getAllEpics description",
                Status.NEW);
        taskManager.addNewEpic(epic1);

        final List<Subtask> subtasksInEpicList = taskManager.getSubtasksFromEpic(epic1.getId());

        assertNotNull(subtasksInEpicList, "Null вместо пустого массива подзадач");
        assertEquals(0, subtasksInEpicList.size(), "Ненулевое количество подзадач.");
    }

    @Test
    void history() {
        taskManager.findTaskByID(task.getId());
        List<Task> historyTasksList = taskManager.history();
        Task[] historyTasks = new Task[] {task};
        Task[] newHistoryTasks = new Task[1];
        for (int i = 0; i < historyTasksList.size(); i++) {
            newHistoryTasks[i] = historyTasksList.get(i);
        }

        assertNotNull(taskManager.history(), "Задачи не возвращаются.");
        assertEquals(1, taskManager.history().size(), "Неверное количество подзадач.");
        assertArrayEquals(newHistoryTasks, historyTasks, "Задачи не совпадают");
    }
}