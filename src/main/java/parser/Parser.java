package parser;

import command.*;
import task.*;
import exception.BabeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public static Command parseCommand(String input) throws BabeException {
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
                default -> throw new BabeException("I don't understand this command. Please try again!");
            };
        } catch (StringIndexOutOfBoundsException e) {
            throw new BabeException("The command format is incorrect!");
        }
    }

    private static Task createTodo(String input) throws BabeException {
        if (input.equals("todo")) {
            throw new BabeException("The description of a todo cannot be empty!");
        }
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new BabeException("The description of a todo cannot be empty!");
        }
        return new Todo(description);
    }

    private static Task createDeadline(String input) throws BabeException {
        if (input.equals("deadline")) {
            throw new BabeException("The description of a deadline cannot be empty!");
        }
        if (!input.contains("/by")) {
            throw new BabeException("Please provide a deadline using /by!");
        }
        String[] parts = input.split(" /by ");
        if (parts.length != 2) {
            throw new BabeException("Please provide both a description and deadline!");
        }
        String description = parts[0].substring(9).trim();
        String byStr = parts[1].trim();
        if (description.isEmpty() || byStr.isEmpty()) {
            throw new BabeException("The description and deadline cannot be empty!");
        }
        try {
            LocalDateTime by = LocalDateTime.parse(byStr, INPUT_FORMATTER);
            return new Deadline(description, by);
        } catch (DateTimeParseException e) {
            throw new BabeException("Please enter date and time in the format: yyyy-MM-dd HHmm\n" +
                    "For example: 2024-01-30 1430 for January 30, 2024, 2:30 PM");
        }
    }

    private static Task createEvent(String input) throws BabeException {
        if (input.equals("event")) {
            throw new BabeException("The description of an event cannot be empty!");
        }
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new BabeException("Please provide event time using /from and /to!");
        }
        String[] parts = input.split(" /from | /to ");
        if (parts.length != 3) {
            throw new BabeException("Please provide a description, start time, and end time!");
        }
        String description = parts[0].substring(6).trim();
        String startStr = parts[1].trim();
        String endStr = parts[2].trim();
        if (description.isEmpty() || startStr.isEmpty() || endStr.isEmpty()) {
            throw new BabeException("The description, start time, and end time cannot be empty!");
        }

        try {
            LocalDateTime start = LocalDateTime.parse(startStr, INPUT_FORMATTER);
            LocalDateTime end = LocalDateTime.parse(endStr, INPUT_FORMATTER);

            if (end.isBefore(start)) {
                throw new BabeException("Event end time cannot be before start time!");
            }

            return new Event(description, start, end);
        } catch (DateTimeParseException e) {
            throw new BabeException("Please enter date and time in the format: yyyy-MM-dd HHmm\n" +
                    "For example: 2024-01-30 1430 for January 30, 2024, 2:30 PM");
        }
    }

    private static int getIndex(String input) throws BabeException {
        try {
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                throw new BabeException("Please provide a task number!");
            }
            return Integer.parseInt(parts[1]) - 1; // Convert to 0-based index
        } catch (NumberFormatException e) {
            throw new BabeException("Please provide a valid task number!");
        }
    }

    private static String getCommandType(String input) {
        String[] parts = input.split(" ");
        return parts[0].toLowerCase();
    }
}