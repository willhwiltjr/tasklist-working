package Forms;

import panels.AddTaskPanel;
import panels.TaskPanelListener;
import tasklist.Task;
import tasklist.TaskFileHandler;

import javax.swing.*;
import java.awt.*;

public class TasklistHome extends JFrame implements TaskPanelListener {

    private JPanel cardPanel;  // For switching views
    private CardLayout cardLayout;

    public static final String ADD_TASK_PANEL = "AddTask";
    private JPanel panel1;
    // You can define more panels like TASK_LIST_PANEL, TASK_DETAILS_PANEL, etc.

    public TasklistHome() {
        setTitle("Task List Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);  // Or use pack()
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);

        // CardLayout setup
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create and add panels
        TaskFileHandler fileHandler = new TaskFileHandler("uncompleted.csv", "completed.csv");
        AddTaskPanel panel = new AddTaskPanel(this, fileHandler);

        // You can add more panels here later
        // cardPanel.add(new TaskListPanel(this).getPanel(), TASK_LIST_PANEL);

        // Set card panel as content pane
        setContentPane(cardPanel);
        cardLayout.show(cardPanel, ADD_TASK_PANEL);  // Show initial panel

        setVisible(true);
    }

    @Override
    public void onTaskSubmitted(Task task) {
        // Handle the task (e.g., write to file, show confirmation, swap views)
        System.out.println("New task: " + task);

        // Example: show confirmation or switch panel
        JOptionPane.showMessageDialog(this, "Task added:\n" + task.getTaskNotes());
        // cardLayout.show(cardPanel, TASK_LIST_PANEL); // future expansion
    }

    @Override
    public void onTaskCancelled() {
        // Go back or reset fields
        JOptionPane.showMessageDialog(this, "Task creation cancelled.");
        // cardLayout.show(cardPanel, TASK_LIST_PANEL); // future expansion
    }
}


