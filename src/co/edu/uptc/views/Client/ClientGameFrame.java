package co.edu.uptc.views.Client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import co.edu.uptc.presenters.ContractClientPlay;
import co.edu.uptc.utils.PropertiesReader;

public class ClientGameFrame extends JFrame {

    private ContractClientPlay.Presenter presenter;
    private ClientGamePanel gamePanel;

    public ClientGameFrame() {
        configureFrame();
        putKeyListener();
        addPanel();
    }

    private void configureFrame(){
        int windowWidth = Integer.parseInt(PropertiesReader.getProperty("windowWidth"));
        int windowHeight = Integer.parseInt(PropertiesReader.getProperty("windowHeight"));
        this.setSize(windowWidth, windowHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Ping Pong Game");
    }

    private void addPanel(){
        gamePanel = new ClientGamePanel();
        gamePanel.setGameFrame(this);
        this.add(gamePanel);
    }

    public void begin() {
        this.setVisible(true);
        gamePanel.begin();
    }

    private void putKeyListener(){
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                presenter.racketMovement(e.getKeyCode());
            }
        });
    }

    public ContractClientPlay.Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(ContractClientPlay.Presenter presenter) {
        this.presenter = presenter;
    }

}
