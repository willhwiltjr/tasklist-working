package panels;

import Forms.TasklistHome;
import tasklist.Task;
import tasklist.TaskFileHandler;
import tasklist.TaskTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TaskListPanel extends JPanel {
    private TaskPanelListener listener;
    private boolean showCompleted;
    private TaskFileHandler fileHandler;
    private JTable taskTable;
    private JButton refreshButton;
    private TasklistHome home; // only if needed, otherwise remove

    public TaskListPanel(TaskPanelListener listener, boolean showCompleted) {
        this.listener = listener;
        this.showCompleted = showCompleted;
        this.fileHandler = new TaskFileHandler("uncompleted.csv", "completed.csv");

        // Optional if you need to use home later, else remove this field and param
        // this.home = home;

        setLayout(new BorderLayout());

        taskTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(taskTable);
        add(scrollPane, BorderLayout.CENTER);

        refreshButton = new JButton("Refresh");
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.add(refreshButton);
        add(footer, BorderLayout.SOUTH);

        // Add right-click mouse listener for task editing
        taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) { // platform-specific right-click
                    int row = taskTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        taskTable.setRowSelectionInterval(row, row);
                        Task selectedTask = ((TaskTableModel) taskTable.getModel()).getTaskAt(row);
                        listener.onEditTaskRequested(selectedTask);
                    }
                }
            }
        });

        refreshButton.addActionListener(e -> refreshTasks());

        // Load tasks initially
        refreshTasks();
    }
        public void refreshTasks() {
            try {
                if (showCompleted) {
                    List<Task> completedTasks = fileHandler.readCompletedTasks();
                    showCompletedTasks(completedTasks);
                } else {
                    List<Task> activeTasks = fileHandler.readUncompletedTasks();
                    showUncompletedTasks(activeTasks);
                }
            } catch (IOException e) {
                showError("Failed to load tasks: " + e.getMessage());
            }
        }
        //this.add(scrollPane, BorderLayout.CENTER); old code

    private void showUncompletedTasks(List<Task> tasks) {
        List<Task> activeTasks = tasks.stream()
                .filter(task -> !task.getIsCompleted())
                .collect(Collectors.toList());

        taskTable.setModel(new TaskTableModel(activeTasks, false));
        SwingUtilities.invokeLater(this::setColumnWidths);
    }

    private void showCompletedTasks(List<Task> tasks) {
        try {
            List<Task> completedTasks = fileHandler.readCompletedTasks();  // we'll add this method
            taskTable.setModel(new TaskTableModel(completedTasks, true));
            SwingUtilities.invokeLater(this::setColumnWidths);
        } catch (IOException e) {
            showError("Failed to load completed tasks: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void setColumnWidths() {
        int columnCount = taskTable.getColumnCount();

        // Choose proportions based on which view is shown
        double[] widths;

        if (columnCount == 4) {
            // Completed tasks: Priority, Date Entered, Date Completed, Notes
            widths = new double[]{0.15, 0.15, 0.15, 0.55};
        } else {
            // Active tasks: Priority, Date Entered, Notes
            widths = new double[]{0.15, 0.15, 0.70};
        }

        // Default to table width if scrollpane not laid out yet
        int tableWidth = taskTable.getParent() != null
                ? taskTable.getParent().getWidth()
                : taskTable.getWidth();

        for (int i = 0; i < widths.length && i < taskTable.getColumnCount(); i++) {
            int preferredWidth = (int) (widths[i] * tableWidth);
            taskTable.getColumnModel().getColumn(i).setPreferredWidth(preferredWidth);
        }

        taskTable.doLayout();  // Force re-layout
    }



}

