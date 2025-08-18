import java.util.Scanner;

public class Lumi {
    private static final String LOGO = "LUMI (˶ˆᗜˆ˵)";

    /** Prints a greeting */
    public static void greet() {
        System.out.println("Hello from\n" + LOGO + "\nWhat can I do for you?");
    }

    /** Prints a goodbye message */
    public static void bye() {
        System.out.println("Bye! See you next time :>");
    }

    public static void main(String[] args) {
        greet();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                bye();
                break;
            } else {
                System.out.println(input);
            }
        }
        scanner.close();
    }
}
