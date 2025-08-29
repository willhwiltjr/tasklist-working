package Forms;

import panels.AddTaskPanel;
import panels.TaskPanelListener;
import tasklist.Task;

import javax.swing.*;

public class TasklistHome extends JFrame implements TaskPanelListener {

    public TasklistHome() {
        // Set up your frame
        AddTaskPanel addTaskPanel = new AddTaskPanel(this); // pass the interface
        setContentPane(addTaskPanel.getPanel());
        pack();
        setVisible(true);
    }

    @Override
    public void onTaskSubmitted(Task task) {
        // Save the task, write to CSV, update list, etc.
        System.out.println("New task: " + task);
        // switch to another panel if needed
    }

    @Override
    public void onTaskCancelled() {
        // Go back to home screen or task list
        System.out.println("Task creation cancelled.");
    }
}

