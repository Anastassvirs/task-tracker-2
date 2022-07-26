package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest {

    TaskManager taskManager;

    @BeforeEach
    void makeManager() {
        FileBackedTasksManager manager = new FileBackedTasksManager(new File("output.csv"));
        this.taskManager = manager.loadFromFile(new File("forTests.csv"));
    }

    @Test
    void addOldTask() {
        assertNotNull(taskManager.findTaskByID(1), "Задача не найдена.");
        assertEquals(
                taskManager.findTaskByID(1).getTaskName(),
                "task for tests",
                "Задачи не совпадают."
        );

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
    }

    @Test
    void addOldSubtask() {
        assertNotNull(taskManager.findSubtaskByID(3), "Задача не найдена.");
        assertEquals(
                taskManager.findSubtaskByID(3).getTaskName(),
                "subtask for tests",
                "Задачи не совпадают."
        );

        assertNotNull(taskManager.findEpicByID(taskManager.findSubtaskByID(3).getNumberOfEpicTask()).getsubtasks(),
                "Подзадачи не добавляются в эпик.");
        assertEquals(
                taskManager.findEpicByID(taskManager.findSubtaskByID(3).getNumberOfEpicTask()).getsubtasks().get(3),
                taskManager.findSubtaskByID(3),
                "В соответствующий эпик добавлена не та подзадача"
        );

        final List<Subtask> subtasks = taskManager.getAllSubtasks();
        assertNotNull(subtasks, "Подзадачи на возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество подзадач.");
    }

    @Test
    void addOldEpic() {
        assertNotNull(taskManager.findEpicByID(2), "Задача не найдена.");
        assertEquals(
                taskManager.findEpicByID(2).getTaskName(),
                "epic for tests",
                "Задачи не совпадают."
        );

        final List<Epic> epics = taskManager.getAllEpics();

        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
    }

}