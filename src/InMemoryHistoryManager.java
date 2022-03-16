import tasks.Task;

import java.util.ArrayDeque;

public class InMemoryHistoryManager implements HistoryManager{
    private ArrayDeque<Task> viewsList;

    public InMemoryHistoryManager() {
        viewsList = new ArrayDeque<>();
    }

    @Override
    public void add(Task task) {
        if (viewsList.size() >= 10) {
            viewsList.removeFirst();
        }
        viewsList.add(task);
    }

    @Override
    public ArrayDeque<Task> getHistory() {
        return viewsList;
    }
}
