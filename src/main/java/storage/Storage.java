package storage;

import task.*;
import exception.BabeException;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String DEFAULT_STORAGE_FILEPATH = "./data/tasks.txt";
    private final Path filePath;
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Storage() {
        this(DEFAULT_STORAGE_FILEPATH);
    }

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

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

    private void createDirectoryIfNotExists() throws IOException {
        Path directory = filePath.getParent();
        if (directory != null && Files.notExists(directory)) {
            Files.createDirectories(directory);
        }
    }

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

    private Task parseTask(String line) throws BabeException {
        try {
            String[] parts = line.split("\\|");
            if (parts.length < 3) {
                throw new BabeException("Invalid task format: " + line);
            }

            String type = parts[0].trim();
            boolean isDone = "1".equals(parts[1].trim());
            String description = parts[2].trim();

            return switch (type) {
                case "T" -> new Todo(description, isDone);
                case "D" -> {
                    if (parts.length < 4) throw new BabeException("Invalid deadline format");
                    LocalDateTime by = parseDateTime(parts[3].trim());
                    yield new Deadline(description, by, isDone);
                }
                case "E" -> {
                    if (parts.length < 5) throw new BabeException("Invalid event format");
                    LocalDateTime start = parseDateTime(parts[3].trim());
                    LocalDateTime end = parseDateTime(parts[4].trim());
                    yield new Event(description, start, end, isDone);
                }
                default -> throw new BabeException("Unknown task type: " + type);
            };
        } catch (DateTimeParseException e) {
            throw new BabeException("Invalid date format. Please use format: YYYY-MM-DD HHMM");
        } catch (Exception e) {
            throw new BabeException("Error parsing task: " + e.getMessage());
        }
    }

    private LocalDateTime parseDateTime(String dateTimeStr) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeStr, DATE_FORMATTER);
    }

    private String formatTask(Task task) {
        StringBuilder builder = new StringBuilder();

        // Add type
        if (task instanceof Todo) builder.append("T");
        else if (task instanceof Deadline) builder.append("D");
        else if (task instanceof Event) builder.append("E");

        // Add completion status
        builder.append(" | ").append(task.isDone() ? "1" : "0");

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