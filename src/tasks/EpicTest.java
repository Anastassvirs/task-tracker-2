package tasks;

import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private TaskManager manager;
    private Integer subtask;
    Epic epic;
    Integer epicTaskNum;

    @BeforeEach
     void createSubtasks() {
        manager = Managers.getDefault("input.csv");
        epic = new Epic("ep2", "cool epic doesn't need subtasks", Status.NEW);
        epicTaskNum = manager.addNewEpic(epic);
    }

    @Test
    void emptyEpic() {
        assertNotNull(manager.getAllEpics());
        assertEquals(manager.findEpicByID(epicTaskNum), epic, "Эпик добавлен некорректно");
    }

    @Test
    void newEpicWithTwoNewSubtasks() {
        Subtask subtaskSub = new Subtask("sub1", "little subbie", Status.NEW, epicTaskNum);
        subtask = manager.addNewSubtask(subtaskSub);
        subtaskSub = new Subtask("sub2", "subsub", Status.NEW, epicTaskNum);
        subtask = manager.addNewSubtask(subtaskSub);
        assertNotNull(manager.getAllEpics());
        assertEquals(manager.findEpicByID(epicTaskNum).progressStatus, Status.NEW, "Статус эпика не NEW," +
                " хотя все подзадачи имеют статус NEW");
    }

    @Test
    void doneEpicWithTwoDoneSubtasks() {
        Subtask subtaskSub = new Subtask("sub1", "little subbie", Status.DONE, epicTaskNum);
        subtask = manager.addNewSubtask(subtaskSub);
        subtaskSub = new Subtask("sub2", "subsub", Status.DONE, epicTaskNum);
        subtask = manager.addNewSubtask(subtaskSub);
        assertNotNull(manager.getAllEpics());
        assertEquals(manager.findEpicByID(epicTaskNum).progressStatus, Status.DONE, "Статус эпика не DONE," +
                " хотя все подзадачи имеют статус DONE");
    }

    @Test
    void inProgressEpicWithDoneAndNewSubtasks() {
        Subtask subtaskSub = new Subtask("sub1", "little subbie", Status.DONE, epicTaskNum);
        subtask = manager.addNewSubtask(subtaskSub);
        subtaskSub = new Subtask("sub2", "subsub", Status.NEW, epicTaskNum);
        subtask = manager.addNewSubtask(subtaskSub);
        assertNotNull(manager.getAllEpics());
        assertEquals(manager.findEpicByID(epicTaskNum).progressStatus, Status.IN_PROGRESS, "Статус эпика не IN_PROGRESS," +
                " хотя подзадачи имеют статусы DONE и NEW");
    }

    @Test
    void inProgressEpicWithTwoInProgressSubtasks() {
        Subtask subtaskSub = new Subtask("sub1", "little subbie", Status.IN_PROGRESS, epicTaskNum);
        subtask = manager.addNewSubtask(subtaskSub);
        subtaskSub = new Subtask("sub2", "subsub", Status.IN_PROGRESS, epicTaskNum);
        subtask = manager.addNewSubtask(subtaskSub);
        assertNotNull(manager.getAllEpics());
        assertEquals(manager.findEpicByID(epicTaskNum).progressStatus, Status.IN_PROGRESS, "Статус эпика не IN_PROGRESS," +
                " хотя все подзадачи имеют статус IN_PROGRESS");
    }
}