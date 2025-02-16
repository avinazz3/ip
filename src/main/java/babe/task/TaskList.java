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
        assert storage != null : "Storage cannot be null";
        this.storage = storage;
        try {
            tasks = new ArrayList<>(storage.load());
            assert tasks != null : "Tasks list cannot be null after loading";
        } catch (BabeException e) {
            System.out.println("Could not load saved tasks: " + e.getMessage());
            tasks = new ArrayList<>();
        }
        assert tasks != null : "Tasks list must be initialized";
    }

    /**
     * Adds a task to the list and saves the updated task list.
     *
     * @param task The task to be added.
     * @throws BabeException If an error occurs while saving the task list.
     */
    public void addTask(Task task) throws BabeException {
        assert task != null : "Cannot add null task";
        int oldSize = tasks.size();
        tasks.add(task);
        assert tasks.size() == oldSize + 1 : "Task list size should increase by 1";
        assert tasks.contains(task) : "Added task should be in the list";
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
        Task task = tasks.get(index);
        assert task != null : "Retrieved task cannot be null";
        return task;
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
        int oldSize = tasks.size();
        Task deletedTask = tasks.remove(index);
        assert tasks.size() == oldSize - 1 : "Task list size should decrease by 1";
        assert !tasks.contains(deletedTask) : "Deleted task should not be in the list";
        assert deletedTask != null : "Deleted task cannot be null";
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
        Task task = tasks.get(index);
        assert task != null : "Task to mark as done cannot be null";
        task.markAsDone();
        assert task.isDone() : "Task should be marked as done";
        saveTaskList();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int size() {
        assert tasks != null : "Tasks list cannot be null";
        return tasks.size();
    }

    /**
     * Returns a copy of the current list of tasks.
     *
     * @return A new ArrayList containing the tasks.
     */
    public ArrayList<Task> getTasks() {
        assert tasks != null : "Tasks list cannot be null";
        ArrayList<Task> tasksCopy = new ArrayList<>(tasks);
        assert tasksCopy.size() == tasks.size() : "Copy should have same size as original";
        return tasksCopy;
    }

    /**
     * Validates that the given index is within the valid range of task indices.
     *
     * @param index The index to validate.
     * @throws BabeException If the index is out of bounds.
     */
    private void validateIndex(int index) throws BabeException {
        assert tasks != null : "Tasks list cannot be null during index validation";
        if (index < 0 || index >= tasks.size()) {
            throw new BabeException("Please provide a valid babe.task number between 1 and " + tasks.size() + "!");
        }
    }

    private void saveTaskList() throws BabeException {
        assert tasks != null : "Cannot save null task list";
        assert storage != null : "Storage cannot be null during save";
        storage.save(tasks);
    }

    /**
     * Searches for tasks whose descriptions contain the specified keyword (case-insensitive).
     *
     * @param keyword the search term to look for in task descriptions
     * @return an ArrayList of Task objects whose descriptions contain the keyword
     */
    public ArrayList<Task> findTasks(String keyword) {
        assert keyword != null : "Search keyword cannot be null";
        assert tasks != null : "Tasks list cannot be null during search";

        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            assert task != null : "Individual task in list cannot be null";
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        assert matchingTasks != null : "Matching tasks list cannot be null";
        return matchingTasks;
    }
}