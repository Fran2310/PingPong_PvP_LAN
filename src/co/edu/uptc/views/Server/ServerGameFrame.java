package co.edu.uptc.views.Server;

import javax.swing.JFrame;

import co.edu.uptc.presenters.ContractServerPlay;
import co.edu.uptc.utils.PropertiesReader;

public class ServerGameFrame extends JFrame {

    private ServerDashboard dashboard;
    private ServerGamePanel gamePanel;

    public ServerGameFrame() {
        int windowWidth = Integer.parseInt(PropertiesReader.getProperty("windowWidth"));
        int windowHeight = Integer.parseInt(PropertiesReader.getProperty("windowHeight"));
        this.setSize(windowWidth, windowHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Ping Pong Game");
        gamePanel = new ServerGamePanel(this);
        this.add(gamePanel);
    }

    public void begin() {
        this.setVisible(true);
        gamePanel.begin();
    }

    public ContractServerPlay.Presenter getPresenter() {
        return dashboard.getPresenter();
    }

    public void setDashboard(ServerDashboard dashboard) {
        this.dashboard = dashboard;
    }

}
