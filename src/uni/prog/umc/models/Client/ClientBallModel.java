package uni.prog.umc.models.Client;

import uni.prog.umc.pojos.BallPojo;
import uni.prog.umc.utils.PropertiesReader;
import uni.prog.umc.utils.Util;

public class ClientBallModel {

    private BallPojo ballPojo;
    private BallPojo ballPojoToDraw;
    private int boardPosition;

    public ClientBallModel() {
        ballPojo = new BallPojo();
        int ballSize = Integer.parseInt(PropertiesReader.getProperty("ballSize"));
        ballPojo.setSize(ballSize);
        ballPojoToDraw = new BallPojo();
        ballPojo.setSize(ballPojo.getSize());
    }

    public void calculateBallPojoToDrawPosition() {
        int windowWidth = Integer.parseInt(PropertiesReader.getProperty("windowWidth"));
        ballPojoToDraw.setxCoordinate(ballPojo.getxCoordinate() - (windowWidth  - 16)* boardPosition);
        ballPojoToDraw.setyCoordinate(ballPojo.getyCoordinate());
        ballPojoToDraw.setSize(ballPojo.getSize());
        ballPojoToDraw.setColor(ballPojo.getColor());
        Util.sleep(100);
    }

    public BallPojo getBallPojo() {
        return ballPojo;
    }

    public BallPojo getBallPojoToDraw() {
        return ballPojoToDraw;
    }

    public void setBoardPosition(int boardPosition) {
        this.boardPosition = boardPosition;
    }

    public void setBallPojo(BallPojo ballPojo) {
        this.ballPojo = ballPojo;
    }

}
