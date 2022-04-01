## Это репозиторий проекта "Трекер задач"  
#### Код создан Свирской Анастасией в рамках обучающей программы от Яндекс Практикум.

Наше приложение работает с тремя видами задач:
1. Классическая задача.
2. Большая задача(Эпик). 
3. Подзадачи(входят в эпик).

Приложение написано на *Java*. Программа запускается из файла Main:
```java
public class Main {
    public static void main(String[] args) {
       //код программы
    }
}
```
И включает в себя 8 классов:
* Main.java
* InMemoryTaskManager.java
* InMemoryHistoryManager.java
* Task.java
* Subtask.java
* Epic.java
* Managers.java
* ListNode

1 enum:
* Status.java

И 2 интерфейса:
* HistoryManager
* TaskManager

Программа представляет собой бэкенд трекера задач. Управление происходит через класс InMemoryTaskManager. Программа 
реализует управление задачами и просмотр истории вызовов задач.

------
По всем интересующим вопросам обращаться на почту: foreducationYa@ya.ru