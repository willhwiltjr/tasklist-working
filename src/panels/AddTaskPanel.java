package panels;

import tasklist.PriorityGroup;
import tasklist.Task;
import tasklist.TaskFileHandler;

import javax.swing.*;
import java.io.IOException;

public class AddTaskPanel extends JPanel {
    private TaskPanelListener listener;
    private TaskFileHandler fileHandler;
    private JPanel panel1;
    private JComboBox<PriorityGroup> priorityComboBox;
    private JButton submitButton;
    private JButton cancelButton;
    private JTextArea notes;

    public AddTaskPanel(TaskPanelListener listener, TaskFileHandler fileHandler) {
        this.listener = listener;
        this.fileHandler = fileHandler;

        // Initialize the priority combo box
        for (PriorityGroup group : PriorityGroup.values()) {
            priorityComboBox.addItem(group);
        }

        // Submit button logic
        submitButton.addActionListener(e -> {
            PriorityGroup selectedGroup = (PriorityGroup) priorityComboBox.getSelectedItem();
            String taskNotes = notes.getText();

            if (selectedGroup == null) {
                JOptionPane.showMessageDialog(this, "Please select a priority level", "Missing Priority", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (taskNotes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter task notes.", "Missing Notes", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Task newTask = new Task(selectedGroup, taskNotes);

            // Check for duplicates
            try {
                if (fileHandler != null && fileHandler.taskExists(newTask)) {
                    JOptionPane.showMessageDialog(this, "Task already exists!", "Duplicate Task", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error checking for duplicate task:\n" + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            listener.onTaskSubmitted(newTask);
        });

        // Cancel button logic
        cancelButton.addActionListener(e -> listener.onTaskCancelled());
    }



    public JPanel getPanel() {
        return panel1;
    }
}


