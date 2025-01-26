package co.edu.uptc.presenters;

import java.awt.Color;
import java.util.List;

import co.edu.uptc.models.Server.ServerGameManager;
import co.edu.uptc.pojos.BallPojo;
import co.edu.uptc.pojos.RacketPojo;
import co.edu.uptc.presenters.ContractServerPlay.Model;
import co.edu.uptc.presenters.ContractServerPlay.View;
import co.edu.uptc.views.Server.ServerDashboard;

public class ServerPresenter implements ContractServerPlay.Presenter {

    private ContractServerPlay.Model model;
    private ContractServerPlay.View view;

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
        model.start();
        view.begin();
    }

    @Override
    public void beginGame() {
        model.startGame();
        view.beginGame();
    }

    @Override
    public void makeMVP() {
        ServerDashboard serverDashboard = new ServerDashboard();
        serverDashboard.setPresenter(this);
        setView(serverDashboard);
        ServerGameManager gameManager = new ServerGameManager();
        gameManager.setPresenter(this);
        setModel(gameManager);
    }

  

    @Override
    public BallPojo getBallPojoToDraw() {
        return model.getBallPojoToDraw();
    }

    @Override
    public List<RacketPojo> getRacketsPojoToDraw() {
        return model.getRacketsPojoToDraw();
    }

   
    @Override
    public void changeClientsAmount(int clientsAmount) {
        view.changeClientsAmount(clientsAmount);
    }

    @Override
    public boolean checkMinClientsAmount() {
        return model.checkMinClientsAmount();
    }

    @Override
    public int getAdjustedHorizontalLimit() {
        return model.getAdjustedHorizontalLimit();
    }

    @Override
    public int getAdjustedVerticalLimit() {
        return model.getAdjustedVerticalLimit();
    }

    @Override
    public void setBallColor(Color color) {
       model.setBallColor(color);
    }

}
