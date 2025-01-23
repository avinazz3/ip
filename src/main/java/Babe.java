import java.util.Scanner;

public class Babe {
    public static void main(String[] args) {
        String logo = """
                 ____        _         \s
                | __ )  __ _| |__   ___\s
                |  _ \\ / _` | '_ \\ / _ \\
                | |_) | (_| | |_) |  __/
                |____/ \\__,_|_.__/ \\___|
                """;
        System.out.println("Hello from\n" + logo);
        printGreet();

        String[] tasks = new String[100];
        int taskCount = 0;
        Scanner scanner = new Scanner(System.in);

        String input;
        do {
            input = scanner.nextLine();
            System.out.println("    ____________________________________________________________");

            if (input.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("     " + (i + 1) + ". " + tasks[i]);
                }
            } else if (!input.equals("bye")) {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("     added: " + input);
            }

            System.out.println("    ____________________________________________________________");
            System.out.println();
        } while (!input.equals("bye"));

        printExit();
    }


    private static void printGreet() {
        System.out.println("____________________________________________________________");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    private static void printExit() {
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
