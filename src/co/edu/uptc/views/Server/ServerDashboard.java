package co.edu.uptc.views.Server;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

import co.edu.uptc.presenters.ContractServerPlay;
import co.edu.uptc.presenters.ContractServerPlay.Presenter;
import co.edu.uptc.utils.RoleEnum;
import co.edu.uptc.views.WaitingPanel;

public class ServerDashboard extends JFrame implements ContractServerPlay.View {

    private ContractServerPlay.Presenter presenter;
    private ServerGameFrame gameFrame = new ServerGameFrame();
    private WaitingPanel waitingPanel = new WaitingPanel(startButtonActionListener(), RoleEnum.SERVER);

    public ServerDashboard() {
        this.setSize(600, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Esperando jugadores");
        gameFrame.setDashboard(this);
        this.add(waitingPanel);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void begin() {
        this.setVisible(true);
    }

    public ContractServerPlay.Presenter getPresenter() {
        return presenter;
    }

    private ActionListener startButtonActionListener() {
        return e -> {
            if (presenter.checkMinClientsAmount()) {
                this.dispose();
                presenter.beginGame();
                beginGame();
            } else {
                waitingPanel.changeFailStatus("Debe haber minimo dos jugadores");
            }
        };
    }

    @Override
    public void beginGame() {
        gameFrame.begin();
        presenter.setBallColor(waitingPanel.getColorSelected());
    }

    @Override
    public void changeClientsAmount(int clientsAmount) {
        waitingPanel.changePlayersAmount(clientsAmount);
    }

}
