package panels;

import tasklist.PriorityGroup;
import tasklist.Task;

import javax.swing.*;

public class AddTaskPanel extends JPanel {
    private TaskPanelListener listener;
    private JPanel panel1;
    private JComboBox comboBox1;
    private JTextArea textField1;
    private JButton button1;
    private JButton button2;
    private JComboBox<PriorityGroup> priorityComboBox;
    private JButton submitButton;
    private JButton cancelButton;
    private JTextArea notes;

    public AddTaskPanel(TaskPanelListener listener) {
        this.listener = listener;

        for (PriorityGroup group : PriorityGroup.values()) {
            priorityComboBox.addItem(group);
        }

        submitButton.addActionListener(e -> {
            PriorityGroup selectedGroup = (PriorityGroup) priorityComboBox.getSelectedItem();
            String taskNotes = notes.getText();

            Task newTask = new Task(selectedGroup, taskNotes);
            listener.onTaskSubmitted(newTask); // callback to main app
        });

        cancelButton.addActionListener(e -> {
            listener.onTaskCancelled(); // callback to go back or close
        });
    }

    public JPanel getPanel() {
        return panel1;
    }
}


