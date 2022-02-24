import java.util.HashMap;

public class Subtask extends Task{
    HashMap<Integer, Subtask> subtasks;

    public Subtask(HashMap<Integer, Subtask> subtasks) {
        this.subtasks = subtasks;
    }
}
