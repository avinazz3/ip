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

    /**
     * Adds a task to the list and saves the updated task list.
     *
     * @param task The task to be added.
     * @throws BabeException If an error occurs while saving the task list.
     */
    public void addTask(Task task) throws BabeException {
        tasks.add(task);
        saveTaskList();
    }

    /**
     * Retrieves a task from the list based on its index.
     *
     * @param index The index of the task.
     * @return The task at the specified index.
     * @throws BabeException If the index is out of bounds.
     */
    public Task getTask(int index) throws BabeException {
        validateIndex(index);
        return tasks.get(index);
    }

    /**
     * Deletes a task from the list based on its index and saves the updated task list.
     *
     * @param index The index of the task to delete.
     * @return The deleted task.
     * @throws BabeException If the index is out of bounds or an error occurs while saving.
     */
    public Task deleteTask(int index) throws BabeException {
        validateIndex(index);
        Task deletedTask = tasks.remove(index);
        saveTaskList();
        return deletedTask;
    }

    /**
     * Marks a task as done based on its index and saves the updated task list.
     *
     * @param index The index of the task to mark as done.
     * @throws BabeException If the index is out of bounds or an error occurs while saving.
     */
    public void markTaskAsDone(int index) throws BabeException {
        validateIndex(index);
        tasks.get(index).markAsDone();
        saveTaskList();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns a copy of the current list of tasks.
     *
     * @return A new ArrayList containing the tasks.
     */
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks); // Return a copy to prevent external modifications
    }

    /**
     * Validates that the given index is within the valid range of task indices.
     *
     * @param index The index to validate.
     * @throws BabeException If the index is out of bounds.
     */
    private void validateIndex(int index) throws BabeException {
        if (index < 0 || index >= tasks.size()) {
            throw new BabeException("Please provide a valid babe.task number between 1 and " + tasks.size() + "!");
        }
    }

    private void saveTaskList() throws BabeException {
        storage.save(tasks);
    }

    /**
     * Searches for tasks whose descriptions contain the specified keyword (case-insensitive).
     *
     * @param keyword the search term to look for in task descriptions
     * @return an ArrayList of Task objects whose descriptions contain the keyword
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}