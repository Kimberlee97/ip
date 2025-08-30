package lumi.storage;

import lumi.parsers.Parser;
import lumi.tasks.Task;

import lumi.exceptions.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.File;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class Storage {
    private final String filePath;
    private List<Task> list = new ArrayList<>();

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /** Converts the file to a List<Task> */
    public List<Task> load() throws IOException, LumiException {
        File file = new File(this.filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            file.createNewFile();
            scanner = new Scanner(file);
        }
        while (scanner.hasNext()) {
            try {
                Task task = Storage.convertStringToTask(scanner.nextLine());
                this.list.add(task);
            } catch (LumiException e) {
                throw new LumiException("Unable to load file: " + e.getMessage());
            }
        }
        return list;
    }

    /** Converts a String to a Task */
    private static Task convertStringToTask(String string) throws LumiException {
        try {
            String[] taskParts = string.split("\\| |\\|", 3);
            String type = taskParts[0];
            String status = taskParts[1];
            String desc = taskParts[2];
            String typeInput = "";
            boolean isDone = false;
            switch (type) {
            case "[T]":
                typeInput = "todo";
                break;
            case "[D]":
                typeInput = "deadline";
                break;
            case "[E]":
                typeInput = "event";
            }

            switch (status) {
            case "[X]":
                isDone = true;
                break;
            case "[ ]":
                isDone = false;
                break;
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

    /** Updates the file */
    public void updateFile() throws IOException, LumiException {
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
