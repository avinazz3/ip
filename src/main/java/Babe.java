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
            } else if (!input.equals("bye")) {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("     added: " + input);
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