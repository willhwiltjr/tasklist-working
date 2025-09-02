package tasklist;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class TaskFileHandler {
    private final Path uncompletedFile;
    private final Path completedFile;

    public TaskFileHandler(String uncompletedFilename, String completedFilename) {
        this.uncompletedFile = Paths.get(uncompletedFilename);
        this.completedFile = Paths.get(completedFilename);

        //tracefilepaths comment out when finished debugging
        System.out.println("Uncompleted file path: " + uncompletedFile.toAbsolutePath());
        System.out.println("Completed file path: " + completedFile.toAbsolutePath());
    }

    public void moveTaskToCompleted(Task task) throws IOException {
        // Read all uncompleted tasks
        List<Task> uncompletedTasks = readTasks(uncompletedFile);

        // Remove the completed task from uncompleted list
        uncompletedTasks.removeIf(t -> t.toCSV().equals(task.toCSV()));

        // Write the updated uncompleted list
        writeTasks(uncompletedTasks, uncompletedFile);

        // Append the completed task to the completed file
        appendTask(task, completedFile);
    }


    private List<Task> readTasks(Path file) throws IOException {
        if (!Files.exists(file)) return new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            return reader.lines()
                    .filter(line -> !line.isBlank())
                    .map(Task::fromCSV)
                    .collect(Collectors.toList());
        }
    }

    private void writeTasks(List<Task> tasks, Path file) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (Task task : tasks) {
                writer.write(task.toCSV());
                writer.newLine();
            }
        }
    }

    private void appendTask(Task task, Path file) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                file, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(task.toCSV());
            writer.newLine();
        }
    }

    public boolean taskExists(Task task) throws IOException {
        List<Task> uncompletedTasks = readTasks(uncompletedFile);
        return uncompletedTasks.stream()
                .anyMatch(existing -> existing.toCSV().equals(task.toCSV()));
    }
    public List<Task> readUncompletedTasks() throws IOException {
        return readTasks(uncompletedFile);
    }

    public List<Task> readCompletedTasks() throws IOException {
        return readTasks(completedFile);
    }

    public void saveNewUncompletedTask(Task task) throws IOException {
        appendTask(task, uncompletedFile);
    }



}

