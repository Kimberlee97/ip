package lumi.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lumi.exceptions.LumiException;
import lumi.parsers.Parser;
import lumi.tasks.Task;

/**
 * This class handles storage of {@Link Task} objects.
 * The Storage class is responsible for loading a date in a given file into a {@Link TaskList}
 * and updating the file.
 */
public class Storage {
    /** The path to the textfile used for storage */
    private final String filePath;

    private List<Task> list = new ArrayList<>();

    /**
     * Creates a new {@Link Storage} object associated with the given file path.
     * @param filePath The path to the text file used for storage.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads all tasks from the file into a list of tasks.
     * If the file does not exist, a new file is created.
     * If a line is invalid, it will be ignored.
     * @return A {@Link List<Task>} containing the tasks read from the file.
     * @throws IOException If there is an error creating or reading the file.
     * @throws LumiException If the task line cannot be parsed correctly.
     */
    public List<Task> load() throws IOException, LumiException {
        File file = new File(this.filePath);
        ensureFileExists(file);

        int errorCount = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                try {
                    Task task = convertStringToTask(scanner.nextLine());
                    this.list.add(task);
                } catch (LumiException e) {
                    errorCount++;
                }
            }
        }
        System.out.println("Number of invalid lines ignored: " + errorCount);
        return list;
    }

    /**
     * Ensures that the storage file and its parent directory exists.
     * @param file The storage file.
     * @throws IOException
     */
    private void ensureFileExists(File file) throws IOException {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    /**
     * Converts a raw string into a {@Link Task} object.
     * @param string The raw task string from the file.
     * @return The corresponding {@Link Task} object.
     * @throws LumiException If the string cannot be parsed into a {@Link Task}.
     */
    private static Task convertStringToTask(String string) throws LumiException {
        try {
            String[] taskParts = string.split("[\\[\\]]+");
            String type = "[" + taskParts[1] + "]";
            String status = "[" + taskParts[2] + "]";
            String desc = taskParts[3];
            String typeInput;
            boolean isDone;
            switch (type) {
            case "[T]":
                typeInput = "todo";
                break;
            case "[D]":
                typeInput = "deadline";
                break;
            case "[E]":
                typeInput = "event";
                break;
            default:
                throw new LumiException("Invalid label!");
            }

            switch (status) {
            case "[X]":
                isDone = true;
                break;
            case "[ ]":
                isDone = false;
                break;
            default:
                throw new LumiException("Invalid status!");
            }
            String input = typeInput + " " + desc;
            Task task = Parser.parse(input);
            if (isDone) {
                task.mark();
            }
            return task;
        } catch (LumiException | RuntimeException e) {
            throw new LumiException(e.getMessage());
        }
    }

    /**
     * Writes the current list of tasks back into the storage file.
     * @throws IOException If the file cannot be opened or written to.
     * @throws LumiException If the file cannot be updated.
     */
    public void updateFile() throws LumiException {
        try {
            FileWriter fw = new FileWriter(this.filePath);
            for (Task task : this.list) {
                fw.write(task.toString());
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new LumiException("Unable to update your file, sorry!");
        }
        System.out.println("Your file has been updated >.<");
    }
}
