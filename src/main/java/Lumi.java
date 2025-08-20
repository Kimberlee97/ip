import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Lumi {
    private static final String LOGO = "LUMI (˶ˆᗜˆ˵)";
    private static List<String[]> list = new ArrayList<>();

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
            } else if (input.equals("list")) {
                if (list.isEmpty()) {
                    System.out.println("No items yet");
                }
                for (int i = 0; i < list.size(); i++) {
                    int index = i + 1;
                    System.out.println(index + ". [" + list.get(i)[0] + "] " + list.get(i)[1]);
                }
            } else if (input.startsWith("unmark")) {
                String[] parts = input.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                list.set(index, new String[]{" ", list.get(index)[1]});
                System.out.println("Oki! I've marked this task as undone: ");
                System.out.println(list.get(index)[1]);
            } else if (input.startsWith("mark")) {
                String[] parts = input.split(" ");
                int index = Integer.parseInt(parts[1]) - 1;
                list.set(index, new String[]{"X", list.get(index)[1]});
                System.out.println("Nice! I've marked this task as done: ");
                System.out.println(list.get(index)[1]);
            } else {
                String[] newItem = new String[]{" ", input};
                list.add(newItem);
                System.out.println("added: " + input);
            }
        }
        scanner.close();
    }
}
