package babe.parser;

import babe.command.*;
import babe.task.*;
import babe.exception.BabeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Parses the user input and returns the corresponding command.
     *
     * @param input The user input string.
     * @return The corresponding Command object.
     * @throws BabeException If the input command format is invalid.
     */
    public static Command parseCommand(String input) throws BabeException {
        assert input != null : "Input string cannot be null"; // Ensure input is not null
        try {
            String commandType = getCommandType(input);
            return switch (commandType) {
                case "todo" -> new AddCommand(createTodo(input));
                case "deadline" -> new AddCommand(createDeadline(input));
                case "event" -> new AddCommand(createEvent(input));
                case "list" -> new ListCommand();
                case "mark" -> new MarkCommand(getIndex(input));
                case "unmark" -> new UnmarkCommand(getIndex(input));
                case "bye" -> new ExitCommand();
                case "delete" -> new DeleteCommand(getIndex(input));
                case "find" -> new FindCommand(getSearchKeyword(input));
                case "priority" -> new SetPriorityCommand(getIndex(input), getPriorityLevel(input));
                default -> throw new BabeException("I don't understand this command. Please try again!");
            };
        } catch (StringIndexOutOfBoundsException e) {
            throw new BabeException("The command format is incorrect!");
        }
    }

    /**
     * Validates that a priority level is between 1 and 3.
     *
     * @param level The priority level to validate.
     * @throws BabeException If the priority level is invalid.
     */
    private static void validatePriorityLevel(int level) throws BabeException {
        if (level < 1 || level > 3) {
            throw new BabeException("Priority level must be between 1 (High) and 3 (Low)!");
        }
    }

    /**
     * Extracts the search keyword from the user input.
     *
     * @param input The user input string containing the search keyword.
     * @return The search keyword.
     * @throws BabeException If the keyword is empty or invalid.
     */
    private static String getSearchKeyword(String input) throws BabeException {
        assert input != null : "Input string cannot be null"; // Ensure input is not null
        if (input.equals("find")) {
            throw new BabeException("Please provide a search keyword!");
        }
        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new BabeException("Please provide a search keyword!");
        }
        assert !keyword.isEmpty() : "Keyword should not be empty after trimming"; // Ensure keyword is not empty
        return keyword;
    }

    /**
     * Creates a Todo task from the user input.
     *
     * @param input The user input string containing the todo description and optional priority.
     * @return A Todo task object.
     * @throws BabeException If the description is empty or invalid.
     */
    private static Task createTodo(String input) throws BabeException {
        assert input != null : "Input string cannot be null"; // Ensure input is not null
        if (input.equals("todo")) {
            throw new BabeException("The description of a todo cannot be empty!");
        }
        String[] parts = input.substring(5).trim().split(" /p ");
        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new BabeException("The description of a todo cannot be empty!");
        }
        assert !description.isEmpty() : "Description should not be empty after trimming"; // Ensure description is not empty

        // Handle priority if provided
        Task.Priority priority;
        try {
            if (parts.length > 1) {
                int priorityLevel = Integer.parseInt(parts[1].trim());
                validatePriorityLevel(priorityLevel);
                priority = Task.Priority.fromLevel(priorityLevel);
            } else {
                priority = Task.Priority.MEDIUM;
            }
        } catch (NumberFormatException e) {
            throw new BabeException("Please provide a valid priority level (1-3)!");
        }

        return new Todo(description, priority);
    }

    /**
     * Creates a Deadline task from the user input.
     *
     * @param input The user input string containing the deadline description, due date, and optional priority.
     * @return A Deadline task object.
     * @throws BabeException If the description, deadline, or format is invalid.
     */
    static Task createDeadline(String input) throws BabeException {
        assert input != null : "Input string cannot be null"; // Ensure input is not null
        if (input.equals("deadline")) {
            throw new BabeException("The description of a deadline cannot be empty!");
        }
        if (!input.contains("/by")) {
            throw new BabeException("Please provide a deadline using /by!");
        }
        String[] mainParts = input.split(" /by ");
        if (mainParts.length != 2) {
            throw new BabeException("Please provide both a description and deadline!");
        }

        // Extract description and priority
        String descriptionPart = mainParts[0].substring(9).trim();
        String[] descAndPriority = descriptionPart.split(" /p ");
        String description = descAndPriority[0].trim();

        // Handle priority
        Task.Priority priority;
        try {
            if (descAndPriority.length > 1) {
                int priorityLevel = Integer.parseInt(descAndPriority[1].trim());
                validatePriorityLevel(priorityLevel);
                priority = Task.Priority.fromLevel(priorityLevel);
            } else {
                priority = Task.Priority.MEDIUM;
            }
        } catch (NumberFormatException e) {
            throw new BabeException("Please provide a valid priority level (1-3)!");
        }

        String byStr = mainParts[1].trim();
        if (description.isEmpty() || byStr.isEmpty()) {
            throw new BabeException("The description and deadline cannot be empty!");
        }
        assert !description.isEmpty() && !byStr.isEmpty() : "Description and deadline should not be empty"; // Ensure both are not empty

        try {
            LocalDateTime by = LocalDateTime.parse(byStr, INPUT_FORMATTER);
            return new Deadline(description, by, priority);
        } catch (DateTimeParseException e) {
            throw new BabeException("Please enter date and time in the format: yyyy-MM-dd HHmm\n" +
                    "For example: 2024-01-30 1430 for January 30, 2024, 2:30 PM");
        }
    }

    /**
     * Creates an Event task from the user input.
     *
     * @param input The user input string containing the event description, start time, end time, and optional priority.
     * @return An Event task object.
     * @throws BabeException If the description, start time, end time, or format is invalid.
     */
    private static Task createEvent(String input) throws BabeException {
        assert input != null : "Input string cannot be null"; // Ensure input is not null
        if (input.equals("event")) {
            throw new BabeException("The description of an event cannot be empty!");
        }
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new BabeException("Please provide event time using /from and /to!");
        }
        String[] timeParts = input.split(" /from | /to ");
        if (timeParts.length != 3) {
            throw new BabeException("Please provide a description, start time, and end time!");
        }

        // Extract description and priority
        String descriptionPart = timeParts[0].substring(6).trim();
        String[] descAndPriority = descriptionPart.split(" /p ");
        String description = descAndPriority[0].trim();

        // Handle priority
        Task.Priority priority;
        try {
            if (descAndPriority.length > 1) {
                int priorityLevel = Integer.parseInt(descAndPriority[1].trim());
                validatePriorityLevel(priorityLevel);
                priority = Task.Priority.fromLevel(priorityLevel);
            } else {
                priority = Task.Priority.MEDIUM;
            }
        } catch (NumberFormatException e) {
            throw new BabeException("Please provide a valid priority level (1-3)!");
        }

        String startStr = timeParts[1].trim();
        String endStr = timeParts[2].trim();

        if (description.isEmpty() || startStr.isEmpty() || endStr.isEmpty()) {
            throw new BabeException("The description, start time, and end time cannot be empty!");
        }
        assert !description.isEmpty() && !startStr.isEmpty() && !endStr.isEmpty() :
                "Description, start time, and end time should not be empty"; // Ensure all are not empty

        try {
            LocalDateTime start = LocalDateTime.parse(startStr, INPUT_FORMATTER);
            LocalDateTime end = LocalDateTime.parse(endStr, INPUT_FORMATTER);

            if (end.isBefore(start)) {
                throw new BabeException("Event end time cannot be before start time!");
            }

            return new Event(description, start, end, priority);
        } catch (DateTimeParseException e) {
            throw new BabeException("Please enter date and time in the format: yyyy-MM-dd HHmm\n" +
                    "For example: 2024-01-30 1430 for January 30, 2024, 2:30 PM");
        }
    }

    /**
     * Extracts the task index from the user input.
     *
     * @param input The user input string containing the command and task index.
     * @return The zero-based index of the task.
     * @throws BabeException If the input format is invalid or the index is not a number.
     */
    private static int getIndex(String input) throws BabeException {
        assert input != null : "Input string cannot be null"; // Ensure input is not null
        try {
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                throw new BabeException("Please provide a task number!");
            }
            int index = Integer.parseInt(parts[1]) - 1; // Convert to 0-based index
            assert index >= 0 : "Task index should be non-negative"; // Ensure index is valid
            return index;
        } catch (NumberFormatException e) {
            throw new BabeException("Please provide a valid task number!");
        }
    }

    /**
     * Extracts the priority level from the user input for the priority command.
     *
     * @param input The user input string containing the command, task index, and priority level.
     * @return The priority level (1-3).
     * @throws BabeException If the priority level is invalid or missing.
     */
    private static int getPriorityLevel(String input) throws BabeException {
        assert input != null : "Input string cannot be null"; // Ensure input is not null
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new BabeException("Please provide both task number and priority level (1-3)!");
        }
        try {
            int level = Integer.parseInt(parts[2]);
            validatePriorityLevel(level);
            return level;
        } catch (NumberFormatException e) {
            throw new BabeException("Please provide a valid priority level (1-3)!");
        }
    }

    /**
     * Extracts the command type from the user input.
     *
     * @param input The user input string.
     * @return The command type as a lowercase string.
     */
    private static String getCommandType(String input) {
        assert input != null : "Input string cannot be null"; // Ensure input is not null
        String[] parts = input.split(" ");
        return parts[0].toLowerCase();
    }
}