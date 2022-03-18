import tasks.Status;
import tasks.Subtask;
import tasks.Task;


public class Main {
    public static void main(String[] args) {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        HistoryManager historyManager;
        historyManager = Managers.getDefaultHistory();
        // Две обычные задачи
        Integer taskNum1 = manager.addTask("a", "b");
        Integer taskNum2 = manager.addTask("c", "d");
        // Две подзадачи + эпик
        Integer epicTaskNum1 = manager.addEpic("ep1", "epicdes");
        Integer subtask1 = manager.addSubtask("sub1", "subdes", epicTaskNum1);
        Integer subtask2 = manager.addSubtask("sub2", "subsub", epicTaskNum1);
        //Ещё две обычные задачи для проверки работы history
        Integer taskNum3 = manager.addTask("e", "f");
        Integer taskNum4 = manager.addTask("g", "h");

        System.out.println('\n' + "Список обычных задач: " + manager.getAllTasks());
        System.out.println("Список эпиков: " + manager.getAllEpics());
        System.out.println("Список подзадач: " + manager.getAllSubtasks());

        Task task = manager.findTaskByID(taskNum3);
        task = manager.findTaskByID(taskNum1);
        task = manager.findSubtaskByID(subtask1);
        task = manager.findEpicByID(epicTaskNum1);
        task = manager.findSubtaskByID(subtask1);
        task = manager.findSubtaskByID(subtask2);
        System.out.println(historyManager.getHistory());
        task = manager.findTaskByID(taskNum1);
        task = manager.findTaskByID(taskNum1);
        task = manager.findTaskByID(taskNum1);
        task = manager.findTaskByID(taskNum2);
        task = manager.findTaskByID(taskNum4);
        task = manager.findTaskByID(taskNum1);
        System.out.println(historyManager.getHistory());

        // Проверка getSubtasks
        System.out.println("Все подзадачи первого эпика: " + manager.getSubtasksFromEpic(epicTaskNum1));

        // Эпик с одной подзадачей
        Integer epicTaskNum2 = manager.addEpic("epic2", "epicdes2");
        Integer oneSubtask = manager.addSubtask("subsec", "desSubSec", epicTaskNum2);

        // Выводим списки задач по категориям
        System.out.println('\n' + "Список обычных задач: " + manager.getAllTasks());
        System.out.println("Список эпиков: " + manager.getAllEpics());
        System.out.println("Список подзадач: " + manager.getAllSubtasks());

        // Изменим одну из подзадач на Done и проверим, изменился ли эпик + поменяем статусы обычных задач
        System.out.println('\n' + "Изменяем статус первой подзадачи на Done + изменены статусы обычных задач:");

        task = manager.findTaskByID(taskNum1);
        Task newTask = new Task(task.getTaskName(), task.getDescription(), task.getId(), Status.DONE);
        manager.updateTask(newTask);

        task = manager.findTaskByID(taskNum2);
        newTask = new Task(task.getTaskName(), task.getDescription(), task.getId(), Status.IN_PROGRESS);
        manager.updateTask(newTask);

        Subtask subtask = manager.findSubtaskByID(subtask1);
        Subtask newSubtask = new Subtask(subtask.getTaskName(), subtask.getDescription(), subtask.getId(), Status.DONE,
                subtask.getNumberOfEpicTask());
        manager.updateSubtask(newSubtask);

        System.out.println('\n' + "Список обычных задач: " + manager.getAllTasks());
        System.out.println("Список эпиков: " + manager.getAllEpics());
        System.out.println("Список подзадач: " + manager.getAllSubtasks());

        // Поменяем вторую подзадачу
        System.out.println('\n' + "Теперь вторую:");
        subtask = manager.findSubtaskByID(subtask2);
        newSubtask = new Subtask(subtask.getTaskName(), subtask.getDescription(), subtask.getId(), Status.DONE,
                subtask.getNumberOfEpicTask());
        manager.updateSubtask(newSubtask);
        System.out.println('\n' + "Список эпиков: " + manager.getAllEpics());
        System.out.println("Список подзадач: " + manager.getAllSubtasks());

        // удалим первый эпик и вторую обычную задачу
        System.out.println('\n' + "Проверка удаления:");
        manager.deleteEpicByNum(epicTaskNum1);
        manager.deleteTaskByNum(taskNum2);
        System.out.println('\n' + "Список обычных задач: " + manager.getAllTasks());
        System.out.println("Список эпиков: " + manager.getAllEpics());
    }
}
