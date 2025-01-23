import java.util.Scanner;

public class Babe {
    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        int taskCount = 0;

        String logo = """
                 ____        _         \s
                | __ )  __ _| |__   ___\s
                |  _ \\ / _` | '_ \\ / _ \\
                | |_) | (_| | |_) |  __/
                |____/ \\__,_|_.__/ \\___|
                """;
        System.out.println("Hello from\n" + logo);

        printGreet();
        Scanner scanner = new Scanner(System.in);

        String input;
        do {
            input = scanner.nextLine();
            System.out.println("    ____________________________________________________________");

            if (input.equals("list")) {
                System.out.println("     Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("     " + (i + 1) + "." + tasks[i]);
                }
            } else if (input.startsWith("todo ")) {
                String description = input.substring(5);
                tasks[taskCount] = new Todo(description);
                taskCount++;
                System.out.println("     Got it. I've added this task:");
                System.out.println("       " + tasks[taskCount-1]);
                System.out.println("     Now you have " + taskCount + " tasks in the list.");
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.split(" /by ");
                String description = parts[0].substring(9);
                String by = parts[1];
                tasks[taskCount] = new Deadline(description, by);
                taskCount++;
                System.out.println("     Got it. I've added this task:");
                System.out.println("       " + tasks[taskCount-1]);
                System.out.println("     Now you have " + taskCount + " tasks in the list.");
            } else if (input.startsWith("event ")) {
                String[] parts = input.split(" /from | /to ");
                String description = parts[0].substring(6);
                String start = parts[1];
                String end = parts[2];
                tasks[taskCount] = new Event(description, start, end);
                taskCount++;
                System.out.println("     Got it. I've added this task:");
                System.out.println("       " + tasks[taskCount-1]);
                System.out.println("     Now you have " + taskCount + " tasks in the list.");
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                tasks[index].markAsDone();
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       " + tasks[index]);
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                tasks[index].markAsNotDone();
                System.out.println("     OK, I've marked this task as not done yet:");
                System.out.println("       " + tasks[index]);
            }

            System.out.println("    ____________________________________________________________");
            System.out.println();
        } while (!input.equals("bye"));

        printExit();
    }

    private static void printGreet() {
        System.out.println("    ____________________________________________________________");
        System.out.println("     Hello! I'm Babe");
        System.out.println("     What can I do for you?");
        System.out.println("    ____________________________________________________________");
        System.out.println();
    }

    private static void printExit() {
        System.out.println("    ____________________________________________________________");
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
        System.out.println();
    }
}