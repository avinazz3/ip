public class Babe {
    public static void main(String[] args) {
        String logo = " ____        _          \n"
                + "| __ )  __ _| |__   ___ \n"
                + "|  _ \\ / _` | '_ \\ / _ \\\n"
                + "| |_) | (_| | |_) |  __/\n"
                + "|____/ \\__,_|_.__/ \\___|\n";
        System.out.println("Hello from\n" + logo);

        printGreet();
        printExit();
    }

    private static void printGreet() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Babe");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    private static void printExit() {
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
