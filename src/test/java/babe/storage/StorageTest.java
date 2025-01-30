package babe.storage;

import babe.task.*;
import babe.exception.BabeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.nio.file.Files;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {
    @TempDir
    Path tempDir;
    private Storage storage;
    private Path testFilePath;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test-tasks.txt");
        storage = new Storage(testFilePath.toString());
    }

    @Test
    public void saveAndLoad_emptyTaskList_success() throws BabeException {
        ArrayList<Task> tasks = new ArrayList<>();
        storage.save(tasks);

        ArrayList<Task> loadedTasks = storage.load();
        assertEquals(0, loadedTasks.size());
    }

    @Test
    public void saveAndLoad_multipleTaskTypes_success() throws BabeException {
        ArrayList<Task> tasks = new ArrayList<>();

        // Create test tasks
        Todo todo = new Todo("buy milk");
        todo.markAsDone();
        tasks.add(todo);

        LocalDateTime deadline = LocalDateTime.of(2024, 2, 1, 14, 30);
        tasks.add(new Deadline("submit report", deadline));

        LocalDateTime eventStart = LocalDateTime.of(2024, 2, 1, 10, 0);
        LocalDateTime eventEnd = LocalDateTime.of(2024, 2, 1, 12, 0);
        tasks.add(new Event("team meeting", eventStart, eventEnd));

        // Save tasks
        storage.save(tasks);

        // Load tasks and verify
        ArrayList<Task> loadedTasks = storage.load();
        assertEquals(3, loadedTasks.size());

        // Verify Todo
        assertTrue(loadedTasks.get(0) instanceof Todo);
        assertTrue(loadedTasks.get(0).isDone());
        assertEquals("buy milk", loadedTasks.get(0).getDescription());

        // Verify Deadline
        assertTrue(loadedTasks.get(1) instanceof Deadline);
        assertEquals("submit report", loadedTasks.get(1).getDescription());
        assertEquals(deadline, ((Deadline) loadedTasks.get(1)).getBy());

        // Verify Event
        assertTrue(loadedTasks.get(2) instanceof Event);
        assertEquals("team meeting", loadedTasks.get(2).getDescription());
        assertEquals(eventStart, ((Event) loadedTasks.get(2)).getStart());
        assertEquals(eventEnd, ((Event) loadedTasks.get(2)).getEnd());
    }

    @Test
    public void load_invalidTaskFormat_throwsBabeException() throws IOException {
        // Create a file with invalid task format
        Files.writeString(testFilePath, "Invalid|Format|Missing|Parts");

        assertThrows(BabeException.class, () -> {
            storage.load();
        });
    }

    @Test
    public void load_invalidDateFormat_throwsBabeException() throws IOException {
        // Create a file with invalid date format
        Files.writeString(testFilePath, "D|0|Submit Report|2024-02-01 invalid_time");

        assertThrows(BabeException.class, () -> {
            storage.load();
        });
    }
}