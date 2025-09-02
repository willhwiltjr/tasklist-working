package Forms;

import panels.AddTaskPanel;
import panels.EditTaskPanel;
import panels.TaskListPanel;
import panels.TaskPanelListener;
import tasklist.Task;
import tasklist.TaskFileHandler;

import javax.swing.*;
import java.awt.*;

public class TasklistHome extends JFrame implements TaskPanelListener {
    // Panel keys for CardLayout
    private static final String ADD_TASK_PANEL = "AddTask";
    private static final String ACTIVE_TASKS_PANEL = "ActiveTasks";
    private static final String COMPLETED_TASKS_PANEL = "CompletedTasks";
    private static final String EDIT_TASK_PANEL = "EditTask";

    private JPanel cardPanel;
    private CardLayout cardLayout;
    // You can define more panels like TASK_LIST_PANEL, TASK_DETAILS_PANEL, etc.

    public TasklistHome() {
        super("Task List Manager");
        initializeFrame();
        initializeUI();
        setVisible(true);
    }

    // Set up basic frame properties
    private void initializeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    // Set up UI components: buttons, panels, listeners
    private void initializeUI() {
        // Navigation buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnActive = new JButton("Active Tasks");
        JButton btnCompleted = new JButton("Completed Tasks");
        JButton btnAdd = new JButton("Add Task");
        JButton btnSettings = new JButton("Settings");

        buttonPanel.add(btnActive);
        buttonPanel.add(btnCompleted);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnSettings);
        add(buttonPanel, BorderLayout.NORTH);

        // Card layout panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel, BorderLayout.CENTER);

        // File handler
        TaskFileHandler fileHandler = new TaskFileHandler("uncompleted.csv", "completed.csv");

        // Add views to card panel
        cardPanel.add(new TaskListPanel(this, false), ACTIVE_TASKS_PANEL);
        cardPanel.add(new TaskListPanel(this, true), COMPLETED_TASKS_PANEL);
        cardPanel.add(new AddTaskPanel(this, fileHandler), ADD_TASK_PANEL);


        // Button listeners to switch views
        btnActive.addActionListener(e -> cardLayout.show(cardPanel, ACTIVE_TASKS_PANEL));
        btnCompleted.addActionListener(e -> cardLayout.show(cardPanel, COMPLETED_TASKS_PANEL));
        btnAdd.addActionListener(e -> cardLayout.show(cardPanel, ADD_TASK_PANEL));
        btnSettings.addActionListener(e -> cardLayout.show(cardPanel, EDIT_TASK_PANEL));

        // Initial view
        cardLayout.show(cardPanel, ACTIVE_TASKS_PANEL);
    }

    private void showEditTaskPanel(Task task) {

        EditTaskPanel editPanel = new EditTaskPanel(task, this);

        // Remove any existing edit panel (if you want to replace it)
        //cardPanel.remove(EDIT_TASK_PANEL);

        cardPanel.add(editPanel, EDIT_TASK_PANEL);
        cardLayout.show(cardPanel, EDIT_TASK_PANEL);

        // Refresh cardPanel so layout updates properly
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    @Override
    public void onEditTaskRequested(Task task) {
        showEditTaskPanel(task);
    }


    @Override
    public void onTaskSubmitted(Task task) {
        // Handle the task (e.g., write to file, show confirmation, swap views)
        System.out.println("New task: " + task);

        // Example: show confirmation or switch panel
        JOptionPane.showMessageDialog(this, "Task added:\n" + task.getTaskNotes());
        cardLayout.show(cardPanel, ACTIVE_TASKS_PANEL); // future expansion
    }

    @Override
    public void onTaskCancelled() {
        // Go back or reset fields
        JOptionPane.showMessageDialog(this, "Task creation cancelled.");
        // cardLayout.show(cardPanel, TASK_LIST_PANEL); // future expansion
    }
}


