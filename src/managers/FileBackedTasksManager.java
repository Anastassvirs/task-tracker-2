package managers;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

    public Integer addOldTask(Task task) {
        tasks.put(task.getId(), task);
        if (task.getId() > numberOfTasks) {
            numberOfTasks = task.getId() + 1;
        }
        return task.getId();
    }

    public Integer addOldSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        if (subtask.getId() > numberOfTasks) {
            numberOfTasks = subtask.getId() + 1;
        }
        epics.get(subtask.getNumberOfEpicTask()).addSubtask(subtask);
        return subtask.getId();
    }

    public Integer addOldEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        if (epic.getId() > numberOfTasks) {
            numberOfTasks = epic.getId() + 1;
        }
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

    public FileBackedTasksManager loadFromFile(File file) {
        try {
            String recoveryFile = Files.readString(Path.of(file.toString()));
            String[] split = recoveryFile.split("\n");
            for (int i = 1; i < split.length - 2; i++) {
                String line = split[i];
                Status status;
                String[] splitLine = line.split(",");

                switch (split[3]) {
                    case "IN_PROGRESS":
                        status = Status.IN_PROGRESS;
                        break;
                    case "DONE":
                        status = Status.DONE;
                        break;
                    default:
                        status = Status.NEW;
                }
                switch (splitLine[1]) {
                    case "TASK":
                        Task task = new Task(splitLine[2], splitLine[4].replaceAll("(\\r|\\n)", ""), Integer.parseInt(splitLine[0]), status);
                        addOldTask(task);
                        break;
                    case "EPIC":
                        Epic epic = new Epic(splitLine[2], splitLine[4].replaceAll("(\\r|\\n)", ""), Integer.parseInt(splitLine[0]), status);
                        addOldEpic(epic);
                        break;
                    default:
                        Subtask subtask = new Subtask(splitLine[2], splitLine[4], Integer.parseInt(splitLine[0]), status, Integer.parseInt(splitLine[5].replaceAll("(\\r|\\n)", "")));
                        addOldSubtask(subtask);
                }
            }
            String line = split[split.length - 1];
            List<Integer> historyList = getHistoryFromString(line);
            for (Integer item: historyList) {
                findEveryTaskByID(item);
            }
            return this;
        } catch (IOException exp){
            throw new ManagerSaveException("wow..exeption");
        }
    }

    private static List<Integer> getHistoryFromString(String value) {
        List<Integer> newList = new ArrayList<>();
        String[] split = value.split(",");

        for (String item: split) {
            newList.add(Integer.parseInt(item.replaceAll("(\\r|\\n)", "")));
        }
        return newList;
    }
}

class ManagerSaveException extends Error{
    public ManagerSaveException() {
        super();
    }

    public ManagerSaveException(final String message) {
        super(message);
    }
}