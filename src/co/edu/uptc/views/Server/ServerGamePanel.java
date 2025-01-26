package co.edu.uptc.views.Server;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import co.edu.uptc.pojos.BallPojo;
import co.edu.uptc.pojos.RacketPojo;
import co.edu.uptc.utils.Util;

public class ServerGamePanel extends JPanel {

    private boolean running;
    private BallPojo ball;
    private List<RacketPojo> rackets;
    private ServerGameFrame gameFrame;

    public ServerGamePanel(ServerGameFrame dashboard) {
        this.gameFrame = dashboard;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int boardWidth = gameFrame.getPresenter().getAdjustedHorizontalLimit();
        int boardHeight = gameFrame.getPresenter().getAdjustedVerticalLimit();
        g.drawLine(0, boardHeight / 2, boardWidth, boardHeight / 2);
        g.drawLine(boardWidth / 2, 0, boardWidth / 2, boardHeight);
        if (ball != null) {
            g.setColor(ball.getColor());
            g.fillOval(ball.getxCoordinate(), ball.getyCoordinate(), ball.getSize(), ball.getSize());
        }
        if (rackets != null) {
            g.setColor(Color.MAGENTA);
            for (RacketPojo racket : rackets) {
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
        this.rackets = gameFrame.getPresenter().getRacketsPojoToDraw();
    }

    public void begin() {
        running = true;
        threadPaint();
    }
}
