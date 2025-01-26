package co.edu.uptc.models.Client;

import java.awt.event.KeyEvent;

import co.edu.uptc.net.ClientManager;
import co.edu.uptc.pojos.BallPojo;
import co.edu.uptc.pojos.RacketPojo;
import co.edu.uptc.presenters.ContractClientPlay;
import co.edu.uptc.presenters.ContractClientPlay.Presenter;
import co.edu.uptc.utils.DirectionEnum;
import co.edu.uptc.utils.PropertiesReader;
import co.edu.uptc.utils.Util;

public class ClientGameManager implements ContractClientPlay.Model {

    private ContractClientPlay.Presenter presenter;
    private ClientBallModel ballModel;
    private ClientRacketModel racketModel;
    private int horizontalLimit;
    private int verticalLimit;
    private ClientManager client;
    private boolean isWaiting;

    public ClientGameManager() {
        client = new ClientManager();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start() {
        initLimits();
        initModels();
        loadClientPojos();
        presenter.beginGame();
    }

    private void initModels() {
        initBall();
        initRacket();
    }

    private void initBall() {
        ballModel = new ClientBallModel();
        ballModel.setBoardPosition(client.getClientPojo().getBoardPosition());
    }

    private void initRacket() {
        racketModel = new ClientRacketModel();
        racketModel.setVerticalLimit(verticalLimit);
        racketModel.setHorizontalLimit(horizontalLimit);
        chooseRacketPosition();
        racketModel.configureInitialPosition();
    }

    private void chooseRacketPosition() {
        if (client.getClientPojo().getBoardPosition() == 0) {
            racketModel.getRacketPojo().setPosition(DirectionEnum.LEFT);
            racketModel.getRacketPojo().setAvailable(true);
        } else if (client.getClientPojo().getBoardPosition() == (client.getClientsAmount() - 1)) {
            racketModel.getRacketPojo().setPosition(DirectionEnum.RIGHT);
            racketModel.getRacketPojo().setAvailable(true);
        }
    }

    private void initLimits() {
        horizontalLimit = Integer.parseInt(PropertiesReader.getProperty("windowWidth")) - 16;
        verticalLimit = Integer.parseInt(PropertiesReader.getProperty("windowHeight")) - 40;
    }

    private void loadClientPojos() {
        Thread loadClientPojosThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ballModel.setBallPojo(client.getClientPojo().getBallPojo());
                    client.setRacketPojo(racketModel.getRacketPojo());
                    ballModel.calculateBallPojoToDrawPosition();
                }
            }
        });
        loadClientPojosThread.setName("Load Client Pojos Thread");
        loadClientPojosThread.start();
    }

    @Override
    public void racketMovement(int keyCode) {
        if (racketModel.getRacketPojo().isAvailable()
                && (racketModel.getRacketPojo().getPosition() == DirectionEnum.LEFT
                        || racketModel.getRacketPojo().getPosition() == DirectionEnum.RIGHT)) {
            moveRacket(keyCode);
        }
    }

    private void moveRacket(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                racketModel.move(DirectionEnum.UP);
                break;
            case KeyEvent.VK_DOWN:
                racketModel.move(DirectionEnum.DOWN);
                break;
        }
    }

    @Override
    public boolean checkServerIp(String ipAdress) {
        client.setServerIpAdress(ipAdress);
        try {
            client.pingWithServer();
        } catch (Exception e) {
            return false;
        }
        isWaiting = true;
        changePlayersAmount();
        return true;
    }

    private void changePlayersAmount() {
        Thread changePlayersAmountThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (isWaiting) {
                    presenter.changeClientsAmount(client.getClientsAmount());
                    Util.sleep(1000);
                    if (client != null) {
                        if (client.isStarted()) {
                            isWaiting = false;
                            start();
                        }
                    }
                }
            }
        });
        changePlayersAmountThread.setName("Change Players Amount Thread");
        changePlayersAmountThread.start();
    }

    @Override
    public RacketPojo getRacketPojoToDraw() {
        return racketModel.getRacketPojo();
    }

    @Override
    public BallPojo getBallPojoToDraw() {
        return ballModel.getBallPojoToDraw();
    }

    @Override
    public void setHorizontalLimit(int horizontalLimit) {
        this.horizontalLimit = horizontalLimit;

    }

    @Override
    public void setVerticalLimit(int verticalLimit) {
        this.verticalLimit = verticalLimit;
    }

}
