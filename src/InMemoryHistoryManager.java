import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private ListNode head;
    private ListNode tail;
    private int size;
    private HashMap<Integer, ListNode> numbersOfTasks;
    private ArrayList<Task> historyGet;

    public InMemoryHistoryManager() {
        numbersOfTasks = new HashMap<>();
        historyGet = new ArrayList<>();
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(Task task) { //нужно ли добавить проверку на то, есть ли уже этот node в списке?
        if (size >= 10) {
            removeNode(head);
        }
        if (numbersOfTasks.getOrDefault(task.getId(), null) != null) {
            remove(task.getId());
        }
        numbersOfTasks.put(task.getId(), linkLast(task));
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        removeNode(numbersOfTasks.get(id));
        numbersOfTasks.remove(id);
    }

    private ListNode linkLast(Task task) {
        final ListNode l = tail;
        final ListNode newNode = new ListNode(l, task, null);
        tail = newNode;
        if (l == null)
            head = newNode;
        else
            l.next = newNode;
        size++;
        return newNode;
    }

    public ArrayList<Task> getTasks() {
        historyGet.clear();
        historyGet.add(head.item);
        ListNode next = head.next;
        for (int i = 1; i < size; i++) {
            ListNode thisOne = next;
            next = thisOne.next;
            if (thisOne != null) {
                historyGet.add(thisOne.item);
            } else {
                break;
            }
        }
        return historyGet;
    }

    private Task removeNode(ListNode node) {
        final Task element = node.item;
        final ListNode next = node.next;
        final ListNode prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
        return element;
    }
}
