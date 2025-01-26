package co.edu.uptc.presenters;

import co.edu.uptc.models.Client.ClientGameManager;
import co.edu.uptc.pojos.BallPojo;
import co.edu.uptc.pojos.RacketPojo;
import co.edu.uptc.presenters.ContractClientPlay.Model;
import co.edu.uptc.presenters.ContractClientPlay.View;
import co.edu.uptc.views.Client.ClientDashboard;

public class ClientPresenter implements ContractClientPlay.Presenter {

    private ContractClientPlay.Model model;
    private ContractClientPlay.View view;

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void begin() {
        makeMVP();
        view.begin();
    }

    @Override
    public void makeMVP() {
        ClientDashboard clientDashboard = new ClientDashboard();
        clientDashboard.setPresenter(this);
        setView(clientDashboard);
        ClientGameManager gameManager = new ClientGameManager();
        gameManager.setPresenter(this);
        setModel(gameManager);
    }

    @Override
    public BallPojo getBallPojoToDraw() {
        return model.getBallPojoToDraw();
    }

    @Override
    public RacketPojo getRacketsPojoToDraw() {
        return model.getRacketPojoToDraw();
    }

    @Override
    public void racketMovement(int keyCode) {
        model.racketMovement(keyCode);
    }

    @Override
    public void beginGame() {
        view.beginGame();
    }

    @Override
    public boolean checkServerIp(String ipAdress) {
        return model.checkServerIp(ipAdress);
    }

    @Override
    public void changeClientsAmount(int clientsAmount) {
       view.changeClientsAmount(clientsAmount);
    }
}
