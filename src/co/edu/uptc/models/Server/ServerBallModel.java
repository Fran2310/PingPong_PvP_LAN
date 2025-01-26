package co.edu.uptc.models.Server;

import co.edu.uptc.pojos.BallPojo;
import co.edu.uptc.utils.PropertiesReader;
import co.edu.uptc.utils.Util;

public class ServerBallModel {

    private BallPojo ballPojo;
    private BallPojo ballPojoToDraw;
    private int movementSpeed;
    private int horizontalLimit;
    private int verticalLimit;
    private double horizontalDrawScale;
    private double verticalDrawScale;
    private double angle;

    public ServerBallModel() {
        ballPojo = new BallPojo();
        movementSpeed = Integer.parseInt(PropertiesReader.getProperty("ballSpeed"));
        int ballSize = Integer.parseInt(PropertiesReader.getProperty("ballSize"));
        ballPojo.setSize(ballSize);
        ballPojoToDraw = new BallPojo();
        chooseRandomAngle();
    }

    private void chooseRandomAngle() {
        angle = (Math.random() * (2 * Math.PI));
        if ((angle > 4.3 && angle < 5.1) || (angle > 1.2 && angle < 2.0)) {
            chooseRandomAngle();
        }
    }

    public void startMovement() {
        Thread movementThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    configureBallDrawScale();
                    Util.sleep(40);
                    move();
                }
            }
        });
        movementThread.setName("BallMovement");
        movementThread.start();
    }

    private void move() {
        moveXCoordinate();
        moveYCoordinate();
    }

    private void moveYCoordinate() {
        ballPojo.setyCoordinate(ballPojo.getyCoordinate() + ((int) (movementSpeed * Math.sin(angle))));
        if (((ballPojo.getyCoordinate() + ballPojo.getSize()) >= verticalLimit)
                || (ballPojo.getyCoordinate() <= 0)) {
            angle = -angle;
        }
    }

    private void moveXCoordinate() {
        ballPojo.setxCoordinate(ballPojo.getxCoordinate() + ((int) (movementSpeed * Math.cos(angle))));

        checkCollisionLeft();
        checkCollisionRight();
    }

    private void checkCollisionRight() {
        if ((ballPojo.getxCoordinate() + ballPojo.getSize()) > (horizontalLimit - movementSpeed)) {
            ballPojo.setxCoordinate(horizontalLimit - ballPojo.getSize());
            configurePosition();
            chooseRandomAngle();
        }
    }

    private void checkCollisionLeft() {
        if (ballPojo.getxCoordinate() < movementSpeed) {
            ballPojo.setxCoordinate(0);
            configurePosition();
            chooseRandomAngle();
        }

    }

    private void configureBallDrawScale() {
        copyValuesToBallPojoToDraw();
        ballPojoToDraw.setxCoordinate((int) (horizontalDrawScale * ballPojo.getxCoordinate()));
        ballPojoToDraw.setyCoordinate((int) (verticalDrawScale * ballPojo.getyCoordinate()));
        ballPojoToDraw.setSize((int) (verticalDrawScale * ballPojo.getSize()));
    }

    private void copyValuesToBallPojoToDraw() {
        ballPojoToDraw.setSize(ballPojo.getSize());
        ballPojoToDraw.setxCoordinate(ballPojo.getxCoordinate());
        ballPojoToDraw.setyCoordinate(ballPojo.getyCoordinate());
        ballPojoToDraw.setColor(ballPojo.getColor());
    }

    public void configurePosition() {
        ballPojo.setxCoordinate(horizontalLimit / 2 - ballPojo.getSize() / 2);
        ballPojo.setyCoordinate(verticalLimit / 2 - ballPojo.getSize() / 2);
    }

    public void horizontalReflection() {
        if (angle > 6.2 && angle < 6.3) {
            angle = -0.78;
        } else {
            angle = Math.PI - angle;
        }
    }

    public BallPojo getBallPojo() {
        return ballPojo;
    }

    public void setHorizontalLimit(int horizontalLimit) {
        this.horizontalLimit = horizontalLimit;
    }

    public void setVerticalLimit(int verticalLimit) {
        this.verticalLimit = verticalLimit;
    }

    public BallPojo getBallPojoToDraw() {
        return ballPojoToDraw;
    }

    public void setHorizontalDrawScale(double horizontalDrawScale) {
        this.horizontalDrawScale = horizontalDrawScale;
    }

    public void setVerticalDrawScale(double verticalDrawScale) {
        this.verticalDrawScale = verticalDrawScale;
    }

}
