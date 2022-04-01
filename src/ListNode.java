import tasks.Task;

public class ListNode {
    Task item;
    ListNode next;
    ListNode prev;

    ListNode(ListNode prev, Task element, ListNode next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
