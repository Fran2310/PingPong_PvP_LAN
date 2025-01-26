package co.edu.uptc.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import co.edu.uptc.utils.RoleEnum;
import co.edu.uptc.views.CustomComponents.WaitingLabel;

public class WaitingPanel extends JPanel {

    private JLabel lblWait, lblPlayers, lblPlayersAmount, lblStatus;
    private JButton btnStart, btnColor;
    private RoleEnum role;
    private int waitingMessageIndex = 0;
    private Color colorSelected;

    public WaitingPanel(ActionListener actionListener, RoleEnum role) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);
        
        this.role = role;
        initComponents(actionListener);
        addComponents();
        waitingAnimation();
    }

    private void initComponents(ActionListener actionListener) {
        lblWait = new WaitingLabel("Esperando a que se conecten");
        lblWait.setPreferredSize(new Dimension(400, 30));
        lblPlayers = new WaitingLabel("Conectados:");
        lblPlayersAmount = new WaitingLabel("0");
        lblStatus = new JLabel("", JLabel.CENTER);
        createStartButton(actionListener);
        createColorButton();
    }

    private void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        this.add(lblWait, gbc);
        gbc.gridwidth = 1;
        this.add(lblPlayers, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        this.add(lblPlayersAmount, gbc);
        gbc.fill = GridBagConstraints.VERTICAL;
        if (role == RoleEnum.SERVER) {
            this.add(btnStart, gbc);
            this.add(lblStatus, gbc);
            this.add(btnColor, gbc);
        }
    }

    private void createStartButton(ActionListener actionListener) {
        btnStart = new JButton("Iniciar");
        btnStart.addActionListener(actionListener);
    }

    private void createColorButton(){
        btnColor = new JButton("Elegir color");
        btnColor.addActionListener(e -> {
            colorSelected = JColorChooser.showDialog(null, "Elegir color", Color.BLUE);
        });
    }

    private void waitingAnimation() {
        String[] waitingMessages = { "Esperando a que se conecten", "Esperando a que se conecten.",
                "Esperando a que se conecten..", "Esperando a que se conecten..." };
        Timer timer = new Timer(500, e -> {
            lblWait.setText(waitingMessages[waitingMessageIndex]);
            waitingMessageIndex = (waitingMessageIndex + 1) % waitingMessages.length;
        });
        timer.start();
    }

    public void changePlayersAmount(int playersAmount) {
        lblPlayersAmount.setText(String.valueOf(playersAmount));
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public void changeFailStatus(String status) {
        lblStatus.setText(status);
        lblStatus.setForeground(Color.RED);
    }

    public Color getColorSelected() {
        return colorSelected;
    }
}
