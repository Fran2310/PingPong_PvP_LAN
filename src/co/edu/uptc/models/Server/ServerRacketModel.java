package co.edu.uptc.models.Server;

import co.edu.uptc.pojos.RacketPojo;
import co.edu.uptc.utils.DirectionEnum;
import co.edu.uptc.utils.PropertiesReader;

public class ServerRacketModel {
    private RacketPojo racketPojo;
    private RacketPojo racketPojoToDraw;
    private int verticalLimit;
    private int horizontalLimit;
    private double horizontalDrawScale;
    private double verticalDrawScale;

    public ServerRacketModel() {
        racketPojo = new RacketPojo();
        int racketHeight = Integer.parseInt(PropertiesReader.getProperty("racketHeight"));
        int racketWidth = Integer.parseInt(PropertiesReader.getProperty("racketWidth"));
        racketPojo.setHeight(racketHeight);
        racketPojo.setWidth(racketWidth);
        racketPojoToDraw = new RacketPojo();
    }

    public void calculateRacketDrawScale() {
        copyValuesToRacketPojoToDraw();
        racketPojoToDraw.setHeight((int) (racketPojo.getHeight() * verticalDrawScale));
        racketPojoToDraw.setWidth((int) (racketPojo.getWidth() * horizontalDrawScale));
        racketPojoToDraw.setxCoordinate((int) (racketPojo.getxCoordinate() * horizontalDrawScale));
        racketPojoToDraw.setyCoordinate((int) (racketPojo.getyCoordinate() * verticalDrawScale));
    }

    private void copyValuesToRacketPojoToDraw() {
        racketPojoToDraw.setHeight(racketPojo.getHeight());
        racketPojoToDraw.setWidth(racketPojo.getWidth());
        racketPojoToDraw.setxCoordinate(racketPojo.getxCoordinate());
        racketPojoToDraw.setyCoordinate(racketPojo.getyCoordinate());
    }

    public void configurePosition() {
        configureHorizontalPosition();
        configureVerticalPosition();
    }

    private void configureHorizontalPosition() {
        if (racketPojo.getPosition() == DirectionEnum.LEFT) {
            racketPojo.setxCoordinate(10);
        } else if (racketPojo.getPosition() == DirectionEnum.RIGHT) {
            racketPojo.setxCoordinate(horizontalLimit - racketPojo.getWidth() - 10);
        }
    }

    private void configureVerticalPosition() {
        racketPojo.setyCoordinate((verticalLimit / 2) - (racketPojo.getHeight() / 2));
    }

    public void setHorizontalLimit(int horizontalLimit) {
        this.horizontalLimit = horizontalLimit;
    }

    public RacketPojo getRacketPojo() {
        return racketPojo;
    }

    public void setVerticalLimit(int verticalLimit) {
        this.verticalLimit = verticalLimit;
    }


    public RacketPojo getRacketPojoToDraw() {
        return racketPojoToDraw;
    }

    public void setHorizontalDrawScale(double horizontalDrawScale) {
        this.horizontalDrawScale = horizontalDrawScale;
    }

    public void setVerticalDrawScale(double verticalDrawScale) {
        this.verticalDrawScale = verticalDrawScale;
    }

    public void setRacketPojo(RacketPojo racketPojo) {
        this.racketPojo = racketPojo;
    }

    
}
