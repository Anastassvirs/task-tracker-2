package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Status;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    private HistoryManager historyManager;
    private Task task;

    @BeforeEach
    public void init() {
        historyManager = new InMemoryHistoryManager();
        task = new Task("Таск 1", "я хорош", Status.DONE);
        task.setId(1);
    }

    @Test
    void add() {
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void addDouble() {
        historyManager.add(task);
        Task task1 = new Task("Таск 1", "я хорош", Status.DONE);
        task1.setId(1);
        historyManager.add(task1);

        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "Добавлено две одинаковых задачи");
    }

    @Test
    void removeFirst() {
        historyManager.add(task);
        Task task2 = new Task("Таск 2", "я не так хорош", Status.NEW);
        task2.setId(2);
        historyManager.add(task2);
        Task task3 = new Task("Таск 3", "я не очень хорош", Status.NEW);
        task3.setId(3);
        historyManager.add(task3);
        Task task4 = new Task("Таск 4", "я не хорош", Status.NEW);
        task4.setId(4);
        historyManager.add(task4);
        Task task5 = new Task("Таск 5", "я плох", Status.NEW);
        task5.setId(5);

        historyManager.remove(1);

        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(4, history.size(), "Задача не удалена");
        assertEquals(false, history.contains(task), "Удалена не та задача/задача не удалена");
    }

    @Test
    void removeLast() {
        historyManager.add(task);
        Task task2 = new Task("Таск 2", "я не так хорош", Status.NEW);
        task2.setId(2);
        historyManager.add(task2);
        Task task3 = new Task("Таск 3", "я не очень хорош", Status.NEW);
        task3.setId(3);
        historyManager.add(task3);
        Task task4 = new Task("Таск 4", "я не хорош", Status.NEW);
        task4.setId(4);
        historyManager.add(task4);
        Task task5 = new Task("Таск 5", "я плох", Status.NEW);
        task5.setId(5);

        historyManager.remove(5);

        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(4, history.size(), "Задача не удалена");
        assertEquals(false, history.contains(task5), "Удалена не та задача/задача не удалена");
    }

    @Test
    void middleRemove() {
        historyManager.add(task);
        Task task2 = new Task("Таск 2", "я не так хорош", Status.NEW);
        task2.setId(2);
        historyManager.add(task2);
        Task task3 = new Task("Таск 3", "я не очень хорош", Status.NEW);
        task3.setId(3);
        historyManager.add(task3);
        Task task4 = new Task("Таск 4", "я не хорош", Status.NEW);
        task4.setId(4);
        historyManager.add(task4);
        Task task5 = new Task("Таск 5", "я плох", Status.NEW);
        task5.setId(5);

        historyManager.remove(3);

        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(4, history.size(), "Задача не удалена");
        assertEquals(false, history.contains(task3), "Удалена не та задача/задача не удалена");
    }

}