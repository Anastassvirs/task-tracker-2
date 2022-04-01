import tasks.Status;
import tasks.Subtask;
import tasks.Task;


public class Main {
    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();
        // Две обычные задачи
        Integer taskNum1 = manager.addTask("task1", "strong and serious");
        Integer taskNum2 = manager.addTask("taskie daskie", "funny and nice");
        // Три подзадачи + эпик
        Integer epicTaskNum1 = manager.addEpic("ep1", "usual epic, you know");
        Integer subtask1 = manager.addSubtask("sub1", "little subbie", epicTaskNum1);
        Integer subtask2 = manager.addSubtask("sub2", "subsub", epicTaskNum1);
        Integer subtask3 = manager.addSubtask("sub3", "subsubsub", epicTaskNum1);
        // Пустой эпик
        Integer epicTaskNum2 = manager.addEpic("ep2", "cool epic doesn't need subtasks");

        System.out.println('\n' + "Список обычных задач: " + manager.getAllTasks());
        System.out.println("Список эпиков: " + manager.getAllEpics());
        System.out.println("Список подзадач: " + manager.getAllSubtasks());

        Task task = manager.findTaskByID(taskNum1);
        task = manager.findTaskByID(taskNum1);
        task = manager.findTaskByID(taskNum2);
        task = manager.findSubtaskByID(subtask1);
        task = manager.findTaskByID(taskNum2);
        task = manager.findEpicByID(epicTaskNum1);
        task = manager.findSubtaskByID(subtask3);
        task = manager.findSubtaskByID(subtask2);
        System.out.println(manager.history());

        // Проверяем, работает ли удаление
        manager.deleteTaskByNum(taskNum1);
        System.out.println(manager.history());

        // Удалим эпик с тремя задачами
        manager.deleteEpicByNum(epicTaskNum1);
        System.out.println(manager.history());

        task = manager.findEpicByID(epicTaskNum2);
        System.out.println(manager.history());


    }
}
