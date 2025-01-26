package co.edu.uptc.views.CustomComponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class WaitingLabel extends JLabel {

    public WaitingLabel(String text) {
        super(text, JLabel.CENTER);
        this.setForeground(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 20);
        this.setFont(font);
    }
}
