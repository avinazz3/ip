package task;

import java.util.ArrayList;
import exception.*;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTask(int index) throws BabeException {
        validateIndex(index);
        return tasks.get(index);
    }

    public Task deleteTask(int index) throws BabeException {
        validateIndex(index);
        return tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    private void validateIndex(int index) throws BabeException {
        if (index < 0 || index >= tasks.size()) {
            throw new BabeException("Please provide a valid task number between 1 and " + tasks.size() + "!");
        }
    }
}
