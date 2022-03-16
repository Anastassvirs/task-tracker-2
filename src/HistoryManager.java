import tasks.Task;

import java.util.ArrayDeque;

public interface HistoryManager {
    void add(Task task);

    ArrayDeque<Task> getHistory();
}
