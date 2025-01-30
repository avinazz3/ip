package babe.task;

import java.util.ArrayList;
import babe.exception.*;
import babe.storage.Storage;

public class TaskList {
    private ArrayList<Task> tasks;
    private final Storage storage;

    public TaskList() {
        this(new Storage());
    }

    public TaskList(Storage storage) {
        this.storage = storage;
        try {
            tasks = new ArrayList<>(storage.load());
        } catch (BabeException e) {
            System.out.println("Could not load saved tasks: " + e.getMessage());
            tasks = new ArrayList<>();
        }
    }

    public void addTask(Task task) throws BabeException {
        tasks.add(task);
        saveTaskList();
    }

    public Task getTask(int index) throws BabeException {
        validateIndex(index);
        return tasks.get(index);
    }

    public Task deleteTask(int index) throws BabeException {
        validateIndex(index);
        Task deletedTask = tasks.remove(index);
        saveTaskList();
        return deletedTask;
    }

    public void markTaskAsDone(int index) throws BabeException {
        validateIndex(index);
        tasks.get(index).markAsDone();
        saveTaskList();
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks); // Return a copy to prevent external modifications
    }

    private void validateIndex(int index) throws BabeException {
        if (index < 0 || index >= tasks.size()) {
            throw new BabeException("Please provide a valid babe.task number between 1 and " + tasks.size() + "!");
        }
    }

    private void saveTaskList() throws BabeException {
        storage.save(tasks);
    }
}