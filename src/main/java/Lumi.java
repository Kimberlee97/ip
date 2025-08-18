import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Lumi {
    private static final String LOGO = "LUMI (˶ˆᗜˆ˵)";
    private static List<String> list = new ArrayList<>();

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
            } else if (input.equals("list")){
                for (int i = 0; i < list.size(); i++) {
                    int index = i + 1;
                    System.out.println(index + ". " + list.get(i));
                }
            } else {
                list.add(input);
                System.out.println("added: " + input);
            }
        }
        scanner.close();
    }
}
