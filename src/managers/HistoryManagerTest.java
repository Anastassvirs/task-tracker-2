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
        task = new Task("Таск 1", "я хорош", 1, Status.DONE);
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
        historyManager.add(new Task("Таск 1", "я хорош", 1, Status.DONE));
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "Добавлено две одинаковых задачи");
    }

    @Test
    void removeFirst() {
        historyManager.add(task);
        historyManager.add(new Task("Таск 2", "я не так хорош", 2, Status.NEW));
        historyManager.add(new Task("Таск 3", "я не очень хорош", 3, Status.NEW));
        historyManager.add(new Task("Таск 4", "я не хорош", 4, Status.NEW));
        historyManager.add(new Task("Таск 5", "я плох", 5, Status.NEW));

        historyManager.remove(1);

        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(4, history.size(), "Задача не удалена");
        assertEquals(false, history.contains(task), "Удалена не та задача/задача не удалена");
    }

    @Test
    void removeLast() {
        historyManager.add(task);
        historyManager.add(new Task("Таск 2", "я не так хорош", 2, Status.NEW));
        historyManager.add(new Task("Таск 3", "я не очень хорош", 3, Status.NEW));
        historyManager.add(new Task("Таск 4", "я не хорош", 4, Status.NEW));
        historyManager.add(new Task("Таск 5", "я плох", 5, Status.NEW));

        historyManager.remove(5);

        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(4, history.size(), "Задача не удалена");
        assertEquals(false, history.contains(new Task("Таск 5", "я плох", 5, Status.NEW)), "Удалена не та задача/задача не удалена");
    }

    @Test
    void middleRemove() {
        historyManager.add(task);
        historyManager.add(new Task("Таск 2", "я не так хорош", 2, Status.NEW));
        historyManager.add(new Task("Таск 3", "я не очень хорош", 3, Status.NEW));
        historyManager.add(new Task("Таск 4", "я не хорош", 4, Status.NEW));
        historyManager.add(new Task("Таск 5", "я плох", 5, Status.NEW));

        historyManager.remove(3);

        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(4, history.size(), "Задача не удалена");
        assertEquals(false, history.contains(new Task("Таск 3", "я не очень хорош", 3, Status.NEW)), "Удалена не та задача/задача не удалена");
    }

}