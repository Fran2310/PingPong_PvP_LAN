package co.edu.uptc.views.Client;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import co.edu.uptc.pojos.BallPojo;
import co.edu.uptc.pojos.RacketPojo;
import co.edu.uptc.utils.Util;

public class ClientGamePanel extends JPanel {

    private boolean running;
    private BallPojo ball;
    private RacketPojo racket;
    private ClientGameFrame gameFrame;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (ball != null) {        
            g.setColor(ball.getColor());
            g.fillOval(ball.getxCoordinate(), ball.getyCoordinate(), ball.getSize(), ball.getSize());
        }
        if (racket != null) {
            g.setColor(Color.MAGENTA);
            if (racket.isAvailable()) {
                g.fillRect(racket.getxCoordinate(), racket.getyCoordinate(), racket.getWidth(), racket.getHeight());
            }
        }
    }

    public void threadPaint() {
        int threadDelay = 5;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    Util.sleep(threadDelay);
                    loadObjectsPojo();
                    repaint();
                }
            }
        });
        thread.setName("Paint Thread");
        thread.start();
    }

    public void loadObjectsPojo() {     
        this.ball = gameFrame.getPresenter().getBallPojoToDraw();
        this.racket = gameFrame.getPresenter().getRacketsPojoToDraw();
    }

    public void begin() {
        running = true;
        threadPaint();
    }

    public void setGameFrame(ClientGameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    

}
