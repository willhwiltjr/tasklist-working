package tasklist;

import Forms.TasklistHome;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Swing must run on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            TasklistHome home = new TasklistHome();
            home.setVisible(true);
        });
    }
}
