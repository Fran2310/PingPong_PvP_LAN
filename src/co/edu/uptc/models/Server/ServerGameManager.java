package co.edu.uptc.models.Server;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.net.ServerManager;
import co.edu.uptc.pojos.BallPojo;
import co.edu.uptc.pojos.ClientPojo;
import co.edu.uptc.pojos.RacketPojo;
import co.edu.uptc.presenters.ContractServerPlay;
import co.edu.uptc.presenters.ContractServerPlay.Presenter;
import co.edu.uptc.utils.DirectionEnum;
import co.edu.uptc.utils.PropertiesReader;
import co.edu.uptc.utils.Util;

public class ServerGameManager implements ContractServerPlay.Model {

    private ContractServerPlay.Presenter presenter;
    private ServerBallModel ballModel;
    private List<ServerRacketModel> racketsModel;
    private int horizontalLimit;
    private int verticalLimit;
    private double horizontalDrawScale;
    private double verticalDrawScale;
    private ServerManager server;

    public ServerGameManager() {
        server = new ServerManager();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start() {
        server.begin();
        lookForGameStart();
    }

    private boolean lookForGameStart() {
        Thread lookForGameStartThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int clientsAmount = 0;
                while (server.isSearching()) {
                    if (server.getClients().size() != clientsAmount) {
                        clientsAmount = server.getClients().size();
                        presenter.changeClientsAmount(clientsAmount);
                    }
                    Util.sleep(100);
                }
            }
        });
        lookForGameStartThread.start();
        return true;
    }

    @Override
    public void startGame() {
        setLimits();
        calculateDrawScale();
        initModels();
        server.beginEvents();
        sendAndReceiveInformationFromClient();
        ballModel.startMovement();
        checkBallAndRacketsCollision();
    }

    private void initModels() {
        initBall();
        initRackets();

    }

    private void initBall() {
        ballModel = new ServerBallModel();
        ballModel.setHorizontalLimit(horizontalLimit);
        ballModel.setVerticalLimit(verticalLimit);
        ballModel.setHorizontalDrawScale(horizontalDrawScale);
        ballModel.setVerticalDrawScale(verticalDrawScale);
        ballModel.configurePosition();
    }

    private void initRackets() {
        racketsModel = new ArrayList<ServerRacketModel>();
        racketsModel.add(createRacket(DirectionEnum.LEFT));
        racketsModel.add(createRacket(DirectionEnum.RIGHT));
    }

    private ServerRacketModel createRacket(DirectionEnum direction) {
        ServerRacketModel racketModel = new ServerRacketModel();
        racketModel.setHorizontalLimit(horizontalLimit);
        racketModel.setVerticalLimit(verticalLimit);
        racketModel.setHorizontalDrawScale(horizontalDrawScale);
        racketModel.setVerticalDrawScale(verticalDrawScale);
        racketModel.getRacketPojo().setPosition(direction);
        racketModel.configurePosition();
        racketModel.calculateRacketDrawScale();
        return racketModel;
    }

    private void sendAndReceiveInformationFromClient() {
        Thread sendClientInformationThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    server.setBallPojo(ballModel.getBallPojo());
                    server.sendClientsPackage();
                    for (int i = 0; i < 2; i++) {
                        if (server.getClients().get(i).getRacketPojo().isAvailable()) {
                            configureRacketXCoordinate(server.getClients().get(i));
                            racketsModel.get(i).setRacketPojo(server.getClients().get(i).getRacketPojo());
                            racketsModel.get(i).calculateRacketDrawScale();
                        }
                    }
                }
            }

        });
        sendClientInformationThread.setName("Send Client Information Thread");
        sendClientInformationThread.start();
    }

    private void configureRacketXCoordinate(ClientPojo clientPojo) {
        if (clientPojo.getRacketPojo().getPosition() == DirectionEnum.RIGHT) {
            clientPojo.getRacketPojo()
                    .setxCoordinate(clientPojo.getRacketPojo().getxCoordinate()
                            * server.getClients().size());
        }
    }

    private void checkBallAndRacketsCollision() {
        Thread checkBallAndRacketsCollisionThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    for (ServerRacketModel racketModel : racketsModel) {
                        checkBallAndRacketCollision(racketModel.getRacketPojo());
                    }
                    Util.sleep(40);
                }
            }

        });
        checkBallAndRacketsCollisionThread.setName("Check Collision Thread");
        checkBallAndRacketsCollisionThread.start();

    }

    private void checkBallAndRacketCollision(RacketPojo racketPojo) {
        BallPojo ball = ballModel.getBallPojo();
        Rectangle ballRectangle = new Rectangle(ball.getxCoordinate(), ball.getyCoordinate(), ball.getSize(),
                ball.getSize());
        Rectangle racketRectangle = new Rectangle(racketPojo.getxCoordinate(), racketPojo.getyCoordinate(),
                racketPojo.getWidth(), racketPojo.getHeight());

        if (ballRectangle.intersects(racketRectangle)) {
            ballModel.horizontalReflection();
        }
    }

    private void setLimits() {
        this.horizontalLimit = server.getClients().size()
                * (Integer.parseInt(PropertiesReader.getProperty("windowWidth")) - 16);
        this.verticalLimit = Integer.parseInt(PropertiesReader.getProperty("windowHeight")) - 40;
    }

    private void calculateDrawScale() {
        double windowHeight = Double.parseDouble(PropertiesReader.getProperty("windowHeight"));
        double windowWidth = Double.parseDouble(PropertiesReader.getProperty("windowWidth"));
        horizontalDrawScale = (windowWidth - 16.0) / horizontalLimit;
        verticalDrawScale = (windowHeight - 40.0) / verticalLimit;
    }

    @Override
    public int getAdjustedHorizontalLimit() {
        return (int) (horizontalLimit * horizontalDrawScale);
    }

    @Override
    public int getAdjustedVerticalLimit() {
        return (int) (verticalLimit * verticalDrawScale);
    }

    @Override
    public BallPojo getBallPojoToDraw() {
        return ballModel.getBallPojoToDraw();
    }

    @Override
    public List<RacketPojo> getRacketsPojoToDraw() {
        List<RacketPojo> racketPojosToDraw = new ArrayList<RacketPojo>();
        for (ServerRacketModel racketModel : racketsModel) {
            racketPojosToDraw.add(racketModel.getRacketPojoToDraw());
        }
        return racketPojosToDraw;
    }

    @Override
    public boolean checkMinClientsAmount() {
        return server.getClients().size() >= 2;
    }

    @Override
    public void setBallColor(Color color) {
       ballModel.getBallPojo().setColor(color);
    }
}
