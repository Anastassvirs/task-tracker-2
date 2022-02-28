import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        // Две обычные задачи
        manager.addTask("a", "b");
        manager.addTask("c", "d");

        // Две подзадачи + эпик
        Integer epicTaskNum = manager.numberOfTasks;
        manager.addEpic("ep1", "epicdes");
        manager.addSubtask("sub1", "subdes", epicTaskNum);
        manager.addSubtask("sub2", "subsub", epicTaskNum);

        // Проверка getSubtasks
        System.out.println("Все подзадачи первого эпика: " + manager.getSubtasksFromEpic(2));

        // Эпик с одной подзадачей
        epicTaskNum = manager.numberOfTasks;
        manager.addEpic("epic2", "epicdes2");
        manager.addSubtask("subsec", "desSubSec", epicTaskNum);

        // Выводим списки задач по категориям
        System.out.println('\n' + "Список обычных задач: " + manager.getAllTasks());
        System.out.println("Список эпиков: " + manager.getAllEpics());
        System.out.println("Список подзадач: " + manager.getAllSubtasks());

        // Изменим одну из подзадач на Done и проверим, изменился ли эпик + поменяем статусы обычных задач
        System.out.println('\n' + "Изменяем статус первой подзадачи на Done + изменены статусы обычных задач:");

        Task task = manager.getAllTasks().get(0);
        Task newTask = new Task(task.getTaskName(), task.getDescription(), task.getId(), Status.DONE);
        manager.updateTask(newTask);

        task = manager.getAllTasks().get(1);
        newTask = new Task(task.getTaskName(), task.getDescription(), task.getId(), Status.IN_PROGRESS);
        manager.updateTask(newTask);

        Subtask subtask = manager.getAllSubtasks().get(0);
        Subtask newSubtask = new Subtask(subtask.getTaskName(), subtask.getDescription(), subtask.getId(), Status.DONE,
                subtask.getNumberOfEpicTask());
        manager.updateSubtask(newSubtask);

        System.out.println('\n' + "Список обычных задач: " + manager.getAllTasks());
        System.out.println("Список эпиков: " + manager.getAllEpics());
        System.out.println("Список подзадач: " + manager.getAllSubtasks());

        // Поменяем вторую подзадачу
        System.out.println('\n' + "Теперь вторую:");
        subtask = manager.getAllSubtasks().get(1);
        newSubtask = new Subtask(subtask.getTaskName(), subtask.getDescription(), subtask.getId(), Status.DONE,
                subtask.getNumberOfEpicTask());
        manager.updateSubtask(newSubtask);
        System.out.println('\n' + "Список эпиков: " + manager.getAllEpics());
        System.out.println("Список подзадач: " + manager.getAllSubtasks());

        // удалим первый эпик и вторую обычную задачу
        System.out.println('\n' + "Проверка удаления:");
        manager.deleteEpicByNum(2);
        manager.deleteTaskByNum(1);
        System.out.println('\n' + "Список обычных задач: " + manager.getAllTasks());
        System.out.println("Список эпиков: " + manager.getAllEpics());
    }
}
