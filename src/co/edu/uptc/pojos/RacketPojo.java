package co.edu.uptc.pojos;

import java.io.Serializable;

import co.edu.uptc.utils.DirectionEnum;

public class RacketPojo implements Serializable {
    private int xCoordinate;
    private int yCoordinate;
    private int height;
    private int width;
    private boolean isAvailable;
    private DirectionEnum position;
    
    public int getxCoordinate() {
        return xCoordinate;
    }
    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }
    public int getyCoordinate() {
        return yCoordinate;
    }
    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    public DirectionEnum getPosition() {
        return position;
    }
    public void setPosition(DirectionEnum position) {
        this.position = position;
    }
    @Override
    public String toString() {
        return "RacketPojo [xCoordinate=" + xCoordinate + ", yCoordinate=" + yCoordinate + ", height=" + height
                + ", width=" + width + ", isAvailable=" + isAvailable + ", position=" + position + "]";
    }

    
   
}
