package panels;

import tasklist.PriorityGroup;
import tasklist.Task;
import tasklist.TaskFileHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AddTaskPanel extends JPanel {
    private JPanel panel1; // This is bound to your form root
    private JComboBox<PriorityGroup> priorityComboBox;
    private JButton submitButton;
    private JButton cancelButton;
    private JTextArea notes;

    private final TaskPanelListener listener;
    private final TaskFileHandler fileHandler;

    public AddTaskPanel(TaskPanelListener listener, TaskFileHandler fileHandler) {
        this.listener = listener;
        this.fileHandler = fileHandler;

        // This line hooks up the .form layout with this panel
        //$$$setupUI$$$();

        // Add the generated panel (from .form) to this JPanel
        setLayout(new BorderLayout());
        add(panel1, BorderLayout.CENTER);

        // Populate priority combo box
        priorityComboBox.removeAllItems();
        for (PriorityGroup group : PriorityGroup.values()) {
            priorityComboBox.addItem(group);
        }

        // Attach button listeners
        submitButton.addActionListener(e -> handleSubmit());
        cancelButton.addActionListener(e -> listener.onTaskCancelled());
    }

    private void handleSubmit() {
        PriorityGroup selectedGroup = (PriorityGroup) priorityComboBox.getSelectedItem();
        String taskNotes = notes.getText().trim();

        if (selectedGroup == null || taskNotes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please complete all fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Task newTask = new Task(selectedGroup, taskNotes);
        try {
            if (fileHandler != null && fileHandler.taskExists(newTask)) {
                JOptionPane.showMessageDialog(this, "Task already exists!", "Duplicate Task", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //create/write the task to file
            fileHandler.saveNewUncompletedTask(newTask);
            //notifylistener
            listener.onTaskSubmitted(newTask);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error checking for duplicate task:\n" + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
}


