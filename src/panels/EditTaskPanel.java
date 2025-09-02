package panels;

import tasklist.PriorityGroup;
import tasklist.Task;
import tasklist.TaskFileHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class EditTaskPanel extends JPanel {
    private final Task originalTask;
    private final TaskFileHandler fileHandler;
    private final TaskPanelListener listener;

    private final JComboBox<PriorityGroup> priorityComboBox;
    private final JTextArea notesTextArea;
    private final JCheckBox completedCheckBox;
    private final JButton saveButton;
    private final JButton cancelButton;

    public EditTaskPanel(Task task, TaskPanelListener listener) {
        this.originalTask = task;
        this.fileHandler = new TaskFileHandler("uncompleted.csv", "completed.csv");
        this.listener = listener;

        setLayout(new BorderLayout());

        // ========== CENTER PANEL ==========
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(new JLabel("Priority:"), gbc);
        gbc.gridx++;
        priorityComboBox = new JComboBox<>(PriorityGroup.values());
        priorityComboBox.setSelectedItem(task.getPriorityGroup());
        formPanel.add(priorityComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Notes:"), gbc);
        gbc.gridx++;
        notesTextArea = new JTextArea(task.getTaskNotes(), 5, 30);
        formPanel.add(new JScrollPane(notesTextArea), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Completed:"), gbc);
        gbc.gridx++;
        completedCheckBox = new JCheckBox();
        completedCheckBox.setSelected(task.getIsCompleted());
        formPanel.add(completedCheckBox, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ========== BOTTOM PANEL ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save Changes");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // ========== ACTION LISTENERS ==========
        saveButton.addActionListener(e -> saveTask());
        cancelButton.addActionListener(e -> listener.onTaskCancelled());
    }

    private void saveTask() {


        String updatedNotes = notesTextArea.getText().trim();
        PriorityGroup updatedPriority = (PriorityGroup) priorityComboBox.getSelectedItem();
        boolean markAsCompleted = completedCheckBox.isSelected();

            //try {
                // Always remove the original first
               // fileHandler.removeTaskFromUncompleted(originalTask);

               // if (markAsCompleted) {
                    //fileHandler.moveTaskToCompleted(updatedTask);  // will save to completed.csv
               // } else {
                  //  fileHandler.saveNewUncompletedTask(updatedTask);  // will save to uncompleted.csv
               // }

        if (updatedNotes.isEmpty() || updatedPriority == null) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Task updatedTask = new Task(updatedPriority, originalTask.getDateEntered(),updatedNotes, markAsCompleted);

        try {
            if (markAsCompleted) {
                fileHandler.moveTaskToCompleted(originalTask, updatedTask);
                JOptionPane.showMessageDialog(this, "Task updated successfully.");
            } else {
                fileHandler.saveNewUncompletedTask(updatedTask);
                JOptionPane.showMessageDialog(this, "Task edited successfully.");
                listener.onTaskSubmitted(updatedTask);
            }


        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to update task:\n" + e.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


