package managers;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class Managers {

    public static TaskManager getDefault() throws IOException{
        return loadFromFile(new File("input.csv"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    private static FileBackedTasksManager loadFromFile(File file) throws IOException {
        FileBackedTasksManager manager = new FileBackedTasksManager(new File("output.csv"));
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
                    manager.addOldTask(task);
                    break;
                case "EPIC":
                    Epic epic = new Epic(splitLine[2], splitLine[4].replaceAll("(\\r|\\n)", ""), Integer.parseInt(splitLine[0]), status);
                    manager.addOldEpic(epic);
                    break;
                default:
                    Subtask subtask = new Subtask(splitLine[2], splitLine[4], Integer.parseInt(splitLine[0]), status, Integer.parseInt(splitLine[5].replaceAll("(\\r|\\n)", "")));
                    manager.addOldSubtask(subtask);
            }
        }
        String line = split[split.length - 1];
        String[] splitLine = line.split(",");
        for (String item: splitLine) {
            item = item.replaceAll("(\\r|\\n)", "");
            int id = Integer.parseInt(item);
            manager.findEveryTaskByID(id);
        }
        return manager;
    }

    private static List<Integer> getHistoryFromString(String value) {
        List<Integer> newList = new ArrayList<>();
        String[] split = value.split(",");

        for (String item: split) {
            newList.add(Integer.parseInt(item));
        }
        return newList;
    }
}
