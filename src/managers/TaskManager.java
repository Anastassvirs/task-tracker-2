package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    ArrayList<Task> getAllTasks();

    ArrayList<Subtask> getAllSubtasks();

    ArrayList<Epic> getAllEpics();

    void deleteAllTasks();

    void deleteAllSubtasks();

    void deleteAllEpics();

     Task findTaskByID(int ID);

     Subtask findSubtaskByID(int ID);

     Epic findEpicByID(int ID);

     Integer addTask(String nameOfTask, String description);

     Integer addSubtask(String nameOfTask, String description, Integer epicTaskNum);

     Integer addEpic(String nameOfTask, String description);

     void updateEpic(Epic epic);

     void updateTask(Task task);

     void updateSubtask(Subtask subtask);

     void deleteTaskByNum(Integer ID);

     void deleteSubtaskByNum(Integer ID);

     void deleteEpicByNum(Integer ID);

     ArrayList<Subtask> getSubtasksFromEpic(Integer numberOfEpic);

     List<Task> history();
}
