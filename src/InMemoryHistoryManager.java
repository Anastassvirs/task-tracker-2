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
            l.setNext(newNode);
        size++;
        return newNode;
    }

    public ArrayList<Task> getTasks() {
        historyGet.clear();
        historyGet.add(head.getItem());
        ListNode next = head.getNext();
        for (int i = 1; i < size; i++) {
            ListNode thisOne = next;
            next = thisOne.getNext();
            if (thisOne != null) {
                historyGet.add(thisOne.getItem());
            } else {
                break;
            }
        }
        return historyGet;
    }

    private Task removeNode(ListNode node) {
        final Task element = node.getItem();
        final ListNode next = node.getNext();
        final ListNode prev = node.getPrev();

        if (prev == null) {
            head = next;
        } else {
            prev.setNext(next);
            node.setPrev(null);
        }

        if (next == null) {
            tail = prev;
        } else {
            next.setPrev(prev);
            node.setNext(null);
        }

        node.setItem(null);
        size--;
        return element;
    }
}
