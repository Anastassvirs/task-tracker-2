package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private static File file;

    public FileBackedTasksManager(File file) {
        super();
        this.file = file;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Task findTaskByID(int ID) {
        Task task = super.findTaskByID(ID);
        save();
        return task;
    }

    @Override
    public Subtask findSubtaskByID(int ID) {
        Subtask subtask = super.findSubtaskByID(ID);
        save();
        return subtask;
    }

    @Override
    public Epic findEpicByID(int ID) {
        Epic epic = super.findEpicByID(ID);
        save();
        return epic;
    }

    @Override
    public Task findEveryTaskByID(int ID) {
        Task task = super.findEveryTaskByID(ID);
        save();
        return task;
    }

    public Integer addOldTask(Task task) {
        tasks.put(task.getId(), task);
        if (task.getId() > numberOfTasks) {
            numberOfTasks = task.getId() + 1;
        }
        save();
        return task.getId();
    }

    public Integer addOldSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        if (subtask.getId() > numberOfTasks) {
            numberOfTasks = subtask.getId() + 1;
        }
        epics.get(subtask.getNumberOfEpicTask()).addSubtask(subtask);
        save();
        return subtask.getId();
    }

    public Integer addOldEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        if (epic.getId() > numberOfTasks) {
            numberOfTasks = epic.getId() + 1;
        }
        save();
        return epic.getId();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteTaskByNum(Integer ID) {
        super.deleteTaskByNum(ID);
        save();
    }

    @Override
    public void deleteSubtaskByNum(Integer ID) {
        super.deleteSubtaskByNum(ID);
        save();
    }

    @Override
    public void deleteEpicByNum(Integer ID) {
        super.deleteEpicByNum(ID);
        save();
    }

    private void save() {
        try (FileWriter fileWriter = new FileWriter(file.toString());) {
            fileWriter.write("id,type,name,status,description,epic\n");
            for (Task item: getAllTasks()) {
                fileWriter.write(item.toString() + '\n');
            }
            for (Epic item: getAllEpics()) {
                fileWriter.write(item.toString() + '\n');
            }
            for (Subtask item: getAllSubtasks()) {
                fileWriter.write(item.toString() + '\n');
            }
            fileWriter.write("\n");
            fileWriter.write(getNumbsFromHistory());
        } catch (IOException exp) {
            System.out.println("Произошла ошибка во время записи файла.");

        }
    }

    public void setNewNumberOfTasks(Integer newNumb) {
        numberOfTasks = newNumb;
    }
}