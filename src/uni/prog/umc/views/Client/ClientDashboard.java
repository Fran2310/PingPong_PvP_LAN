package uni.prog.umc.views.Client;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

import uni.prog.umc.presenters.ContractClientPlay;
import uni.prog.umc.presenters.ContractClientPlay.Presenter;
import uni.prog.umc.utils.RoleEnum;
import uni.prog.umc.views.WaitingPanel;

public class ClientDashboard extends JFrame implements ContractClientPlay.View {

    private ContractClientPlay.Presenter presenter;
    private InputAdressPanel inputAdressPanel = new InputAdressPanel(enterButtonActionListener());
    private WaitingPanel waitingPanel = new WaitingPanel(null, RoleEnum.CLIENT);
    private ClientGameFrame gameFrame = new ClientGameFrame();

    public ClientDashboard() {
        this.setSize(600, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        gameFrame.setPresenter(presenter);
        this.add(inputAdressPanel);
    }
    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void begin() {
        this.setVisible(true);
    }

    private ActionListener enterButtonActionListener() {
        return e -> {
            if(presenter.checkServerIp(inputAdressPanel.getIpAdress())){
                changeToWaitingPanel();
            } else {
                inputAdressPanel.changeFailStatus("Servidor no encontrado");
            }
        };
    }
    private void changeToWaitingPanel() {
        this.remove(inputAdressPanel);
        this.add(waitingPanel);

    }

    @Override
    public void beginGame() {
        gameFrame.setPresenter(presenter);
        this.dispose();
        gameFrame.begin();
    }
    @Override
    public void changeClientsAmount(int clientsAmount) {
       waitingPanel.changePlayersAmount(clientsAmount);
    }

}
