import Tasks.Epic;
import Tasks.Progress;
import Tasks.Subtask;
import Tasks.Task;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        // Две обычные задачи
        Task task = new Task("a", "b", manager.numberOfTasks, Progress.NEW);
        manager.addTask(task);
        task = new Task("c", "d", manager.numberOfTasks, Progress.NEW);
        manager.addTask(task);

        // Две подзадачи + эпик
        Integer epicTaskNum = manager.numberOfTasks;
        manager.numberOfTasks++;
        Subtask subtask1 = new Subtask("sub1", "subdes", manager.numberOfTasks, Progress.NEW, epicTaskNum);
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("sub2", "subsub", manager.numberOfTasks, Progress.NEW, epicTaskNum);
        manager.addSubtask(subtask2);

        HashMap<Integer, Progress> subtasksForEpic = new HashMap<>();
        subtasksForEpic.put(subtask1.getId(), subtask1.getProgressStatus());
        subtasksForEpic.put(subtask2.getId(), subtask2.getProgressStatus());
        Epic epic = new Epic("ep1", "epicdes", epicTaskNum, Progress.NEW, subtasksForEpic);
        manager.addEpic(epic);

        // Проверка getSubtasks
        System.out.println("Все подзадачи первого эпика: " + manager.getSubtasks(epic));

        // Эпик с одной подзадачей
        HashMap<Integer, Progress> subtasksForEpic2 = new HashMap<>();
        epicTaskNum = manager.numberOfTasks;
        manager.numberOfTasks++;
        subtask1 = new Subtask("subsec", "desSubSec", manager.numberOfTasks, Progress.NEW, epicTaskNum);
        subtasksForEpic2.put(subtask1.getId(), subtask1.getProgressStatus());
        manager.addSubtask(subtask1);
        epic = new Epic("epic2", "epicdes2", epicTaskNum, Progress.NEW, subtasksForEpic2);
        manager.addEpic(epic);

        // Выводим списки задач по категориям
        System.out.println('\n' + "Список обычных задач: " + manager.getAllTasks());
        System.out.println("Список эпиков: " + manager.getAllEpics());
        System.out.println("Список подзадач: " + manager.getAllSubtasks());

        // Изменим одну из подзадач на Done и проверим, изменился ли эпик + поменяем статусы обычных задач
        System.out.println('\n' + "Изменяем статус первой подзадачи на Done + изменены статусы обычных задач:");
        manager.updateSubtaskStatus(manager.getAllSubtasks().get(3), Progress.DONE);
        manager.updateTaskStatus(manager.getAllTasks().get(0), Progress.DONE);
        manager.updateTaskStatus(manager.getAllTasks().get(1), Progress.IN_PROGRESS);
        System.out.println('\n' + "Список обычных задач: " + manager.getAllTasks());
        System.out.println("Список эпиков: " + manager.getAllEpics());
        System.out.println("Список подзадач: " + manager.getAllSubtasks());
        // Поменяем вторую подзадачу
        System.out.println('\n' + "Теперь вторую:");
        manager.updateSubtaskStatus(manager.getAllSubtasks().get(4), Progress.DONE);
        System.out.println('\n' + "Список эпиков: " + manager.getAllEpics());
        System.out.println("Список подзадач: " + manager.getAllSubtasks());

        // удалим первый эпик и вторую обычную задачу
        System.out.println('\n' + "Проверка удаления:");
        manager.deleteEpic(manager.getAllEpics().get(2));
        manager.deleteTask(manager.getAllTasks().get(1));
        System.out.println('\n' + "Список обычных задач: " + manager.getAllTasks());
        System.out.println("Список эпиков: " + manager.getAllEpics());
    }
}
