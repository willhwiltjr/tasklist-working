package tasklist;

import Forms.TasklistHome;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TasklistHome::new);
    }
}

