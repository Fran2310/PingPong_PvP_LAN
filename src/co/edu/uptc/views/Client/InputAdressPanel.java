package co.edu.uptc.views.Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import co.edu.uptc.views.CustomComponents.WaitingLabel;

public class InputAdressPanel extends JPanel{

    private JLabel lblInputText, lblStatus;
    private JTextField txtIpAdress;
    private JButton btnEnter;

    public InputAdressPanel(ActionListener actionListener) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);
        initComponents(actionListener);
        addComponents();
    }

    private void initComponents(ActionListener actionListener){
        lblInputText = new WaitingLabel("Ingresa la IP:");
        txtIpAdress = new JTextField();
        txtIpAdress.setPreferredSize(new Dimension(200, 30));
        lblStatus = new JLabel("", JLabel.CENTER);
        createEnterButton(actionListener);
    }

    private void addComponents(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        this.add(lblInputText, gbc);
        this.add(txtIpAdress, gbc);
        this.add(btnEnter, gbc);
        this.add(lblStatus, gbc);
    }

    private void createEnterButton(ActionListener actionListener){
        btnEnter = new JButton("Entrar");
        btnEnter.addActionListener(actionListener);
    }

    public void changeFailStatus(String status){
        lblStatus.setText(status);
        lblStatus.setForeground(Color.RED);
    }

    public String getIpAdress(){
        return txtIpAdress.getText();
    }
}
