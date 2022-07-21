package tasks;

import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private TaskManager manager;
    private Integer subtask1;
    private Integer subtask2;
    Integer epicTaskNum;
    HashMap<Integer, Subtask> newsubs;

    @BeforeEach
     void createSubtasks() {
        manager = Managers.getDefault("input.csv");
        epicTaskNum = manager.addEpic("ep2", "cool epic doesn't need subtasks");
        subtask1 = manager.addSubtask("sub1", "little subbie", epicTaskNum);
        subtask2 = manager.addSubtask("sub2", "subsub", epicTaskNum);
        newsubs = manager.findEpicByID(epicTaskNum).getsubtasks();
    }

    @Test
    void emptyEpic() {
        assertNotNull(manager.getAllEpics());
        assertEquals(manager.findEpicByID(epicTaskNum).taskName, "ep2", "Эпик добавлен некорректно");
    }

    @Test
    void newEpicWithTwoNewSubtasks() {
        assertNotNull(manager.getAllEpics());
        assertEquals(manager.findEpicByID(epicTaskNum).progressStatus, Status.NEW, "Статус эпика не NEW," +
                " хотя все подзадачи имеют статус NEW");
    }

    @Test
    void doneEpicWithTwoDoneSubtasks() {
        for (Subtask sub: newsubs.values()) {
            Subtask nsub = new Subtask(sub.getTaskName(), sub.getDescription(), Status.DONE, epicTaskNum);
            nsub.setId(sub.getId());
            manager.updateSubtask(nsub);
        }
        assertNotNull(manager.getAllEpics());
        assertEquals(manager.findEpicByID(epicTaskNum).progressStatus, Status.DONE, "Статус эпика не DONE," +
                " хотя все подзадачи имеют статус DONE");
    }

    @Test
    void inProgressEpicWithDoneAndNewSubtasks() {
        for (Subtask sub: newsubs.values()) {
            Subtask nsub = new Subtask(sub.getTaskName(), sub.getDescription(), Status.DONE, epicTaskNum);
            nsub.setId(sub.getId());
            manager.updateSubtask(nsub);
            break;
        }
        assertNotNull(manager.getAllEpics());
        assertEquals(manager.findEpicByID(epicTaskNum).progressStatus, Status.IN_PROGRESS, "Статус эпика не IN_PROGRESS," +
                " хотя подзадачи имеют статусы DONE и NEW");
    }

    @Test
    void inProgressEpicWithTwoInProgressSubtasks() {
        for (Subtask sub: newsubs.values()) {
            Subtask nsub = new Subtask(sub.getTaskName(), sub.getDescription(), Status.IN_PROGRESS, epicTaskNum);
            nsub.setId(sub.getId());
            manager.updateSubtask(nsub);
        }
        assertNotNull(manager.getAllEpics());
        assertEquals(manager.findEpicByID(epicTaskNum).progressStatus, Status.IN_PROGRESS, "Статус эпика не IN_PROGRESS," +
                " хотя все подзадачи имеют статус IN_PROGRESS");
    }
}