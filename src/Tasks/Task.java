package Tasks;

// Класс, описывающий все задачи
public class Task {
    protected String taskName; // Имя задачи
    protected String description; // Описание задачи
    protected Integer id; // Уникальный номер задачи
    protected Progress progressStatus; // Статус задачи (Новая / В процессе / Выполнена)

    public Task(String taskName, String description, Integer id, Progress progressStatus) {
        this.taskName = taskName;
        this.description = description;
        this.id = id;
        this.progressStatus = progressStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Progress getProgressStatus() {
        return progressStatus;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", progressStatus=" + progressStatus +
                '}';
    }
}
