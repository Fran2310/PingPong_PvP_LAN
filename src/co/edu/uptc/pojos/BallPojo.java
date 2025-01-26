package co.edu.uptc.pojos;

import java.awt.Color;
import java.io.Serializable;

public class BallPojo implements Serializable {
    private int xCoordinate;
    private int yCoordinate;
    private int size;
    private Color color;

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
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    @Override
    public String toString() {
        return "BallPojo [xCoordinate=" + xCoordinate + ", yCoordinate=" + yCoordinate + ", size=" + size + ", color="
                + color + "]";
    }    

    
    
}
