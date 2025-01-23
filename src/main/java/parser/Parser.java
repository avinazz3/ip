package parser;

import command.*;
import task.*;
import exception.BabeException;

public class Parser {
    public static Command parseCommand(String input) throws BabeException {
        try {
            String commandType = getCommandType(input);
            switch (commandType) {
                case "todo":
                    return new AddCommand(createTodo(input));
                case "deadline":
                    return new AddCommand(createDeadline(input));
                case "event":
                    return new AddCommand(createEvent(input));
                case "list":
                    return new ListCommand();
                case "mark":
                    return new MarkCommand(getIndex(input));
                case "unmark":
                    return new UnmarkCommand(getIndex(input));
                case "bye":
                    return new ExitCommand();
                case "delete":
                    return new DeleteCommand(getIndex(input));
                default:
                    throw new BabeException("I don't understand this command. Please try again!");
            }
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
        String by = parts[1].trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new BabeException("The description and deadline cannot be empty!");
        }
        return new Deadline(description, by);
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
        String start = parts[1].trim();
        String end = parts[2].trim();
        if (description.isEmpty() || start.isEmpty() || end.isEmpty()) {
            throw new BabeException("The description, start time, and end time cannot be empty!");
        }
        return new Event(description, start, end);
    }

    private static int getIndex(String input) throws BabeException {
        try {
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                throw new BabeException("Please provide a task number!");
            }
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new BabeException("Please provide a valid task number!");
        }
    }

    private static String getCommandType(String input) {
        String[] parts = input.split(" ");
        return parts[0].toLowerCase();
    }
}