package babe.storage;

import babe.task.*;
import babe.exception.BabeException;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String DEFAULT_STORAGE_FILEPATH = "./data/tasks.txt";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private final Path filePath;

    public Storage() {
        this(DEFAULT_STORAGE_FILEPATH);
    }

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws BabeException If an error occurs while loading data.
     */
    public ArrayList<Task> load() throws BabeException {
        try {
            createDirectoryIfNotExists();
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
                return new ArrayList<>();
            }
            return loadFromFile();
        } catch (IOException e) {
            throw new BabeException("Error loading data: " + e.getMessage());
        }
    }

    /**
     * Creates the necessary directories if they do not exist.
     *
     * @throws IOException If an error occurs while creating directories.
     */
    private void createDirectoryIfNotExists() throws IOException {
        Path directory = filePath.getParent();
        if (directory != null && Files.notExists(directory)) {
            Files.createDirectories(directory);
        }
    }

    /**
     * Reads the task data from the storage file and converts it into task objects.
     *
     * @return An ArrayList of tasks parsed from the file.
     * @throws BabeException If an error occurs while reading or parsing the file.
     */
    private ArrayList<Task> loadFromFile() throws BabeException {
        ArrayList<Task> tasks = new ArrayList<>();
        try (Scanner scanner = new Scanner(filePath)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    tasks.add(parseTask(line));
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new BabeException("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Saves the given list of tasks to the storage file.
     *
     * @param tasks The list of tasks to be saved.
     * @throws BabeException If an error occurs while writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws BabeException {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(formatTask(task));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new BabeException("Error saving to file: " + e.getMessage());
        }
    }

    /**
     * Parses a task from a stored string format.
     * Format: TYPE | DONE | PRIORITY | DESCRIPTION | [DATETIME...]
     *
     * @param line The string representing a stored task.
     * @return The corresponding Task object.
     * @throws BabeException If the format is invalid or data is missing.
     */
    private Task parseTask(String line) throws BabeException {
        try {
            String[] parts = line.split("\\|");
            validateTaskParts(parts, line);

            String type = parts[0].trim();
            boolean isDone = "1".equals(parts[1].trim());
            Task.Priority priority = parsePriority(parts[2].trim());
            String description = parts[3].trim();

            return createTaskFromParts(type, isDone, priority, description, parts);
        } catch (DateTimeParseException e) {
            throw new BabeException("Invalid date format. Please use format: YYYY-MM-DD HHMM");
        } catch (Exception e) {
            throw new BabeException("Error parsing task: " + e.getMessage());
        }
    }

    /**
     * Parses a priority level from string.
     *
     * @param priorityStr The priority string to parse.
     * @return The corresponding Priority enum value.
     * @throws BabeException If the priority format is invalid.
     */
    private Task.Priority parsePriority(String priorityStr) throws BabeException {
        try {
            int level = Integer.parseInt(priorityStr);
            return Task.Priority.fromLevel(level);
        } catch (IllegalArgumentException e) {
            throw new BabeException("Invalid priority level: " + priorityStr);
        }
    }

    /**
     * Validates the parts of a task string.
     *
     * @param parts The parts of the task string.
     * @param line  The original task string for error reporting.
     * @throws BabeException If the task format is invalid.
     */
    private void validateTaskParts(String[] parts, String line) throws BabeException {
        if (parts.length < 4) {  // Now need at least 4 parts (type, done, priority, description)
            throw new BabeException("Invalid task format: " + line);
        }
    }

    /**
     * Creates a task from the parsed parts.
     *
     * @param type        The type of the task (T, D, E).
     * @param isDone      Whether the task is done.
     * @param priority    The priority level of the task.
     * @param description The description of the task.
     * @param parts       The parts of the task string.
     * @return The corresponding Task object.
     * @throws BabeException If the task type is unknown or data is missing.
     */
    private Task createTaskFromParts(String type, boolean isDone, Task.Priority priority,
                                     String description, String[] parts) throws BabeException {
        switch (type) {
            case "T":
                return new Todo(description, isDone, priority);
            case "D":
                validateDeadlineParts(parts);
                LocalDateTime by = parseDateTime(parts[4].trim());
                return new Deadline(description, by, isDone, priority);
            case "E":
                validateEventParts(parts);
                LocalDateTime start = parseDateTime(parts[4].trim());
                LocalDateTime end = parseDateTime(parts[5].trim());
                return new Event(description, start, end, isDone, priority);
            default:
                throw new BabeException("Unknown task type: " + type);
        }
    }

    /**
     * Validates the parts of a deadline task.
     *
     * @param parts The parts of the task string.
     * @throws BabeException If the deadline format is invalid.
     */
    private void validateDeadlineParts(String[] parts) throws BabeException {
        if (parts.length < 5) {  // Need 5 parts for deadline (including datetime)
            throw new BabeException("Invalid deadline format");
        }
    }

    /**
     * Validates the parts of an event task.
     *
     * @param parts The parts of the task string.
     * @throws BabeException If the event format is invalid.
     */
    private void validateEventParts(String[] parts) throws BabeException {
        if (parts.length < 6) {  // Need 6 parts for event (including start and end times)
            throw new BabeException("Invalid event format");
        }
    }

    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param dateTimeStr The date-time string to parse.
     * @return A LocalDateTime object representing the parsed date and time.
     * @throws DateTimeParseException If the date format is incorrect.
     */
    private LocalDateTime parseDateTime(String dateTimeStr) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeStr, DATE_FORMATTER);
    }

    /**
     * Formats a task into a string representation for storage.
     * Format: TYPE | DONE | PRIORITY | DESCRIPTION | [DATETIME...]
     *
     * @param task The task to be formatted.
     * @return A formatted string representation of the task.
     */
    private String formatTask(Task task) {
        StringBuilder builder = new StringBuilder();

        // Add type
        if (task instanceof Todo) {
            builder.append("T");
        } else if (task instanceof Deadline) {
            builder.append("D");
        } else if (task instanceof Event) {
            builder.append("E");
        }

        // Add completion status
        builder.append(" | ").append(task.isDone() ? "1" : "0");

        // Add priority
        builder.append(" | ").append(task.getPriority().getLevel());

        // Add description
        builder.append(" | ").append(task.getDescription());

        // Add date/time for Deadline and Event
        if (task instanceof Deadline deadline) {
            builder.append(" | ").append(deadline.getBy().format(DATE_FORMATTER));
        } else if (task instanceof Event event) {
            builder.append(" | ").append(event.getStart().format(DATE_FORMATTER))
                    .append(" | ").append(event.getEnd().format(DATE_FORMATTER));
        }

        return builder.toString();
    }
}