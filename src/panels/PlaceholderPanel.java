package panels;

import javax.swing.*;
import java.awt.*;

public class PlaceholderPanel extends JPanel {
    public PlaceholderPanel(String message) {
        setLayout(new BorderLayout());
        add(new JLabel(message, SwingConstants.CENTER), BorderLayout.CENTER);
    }
}

