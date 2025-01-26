package co.edu.uptc.presenters;

import java.awt.Color;
import java.util.List;

import co.edu.uptc.pojos.BallPojo;
import co.edu.uptc.pojos.RacketPojo;

public interface ContractServerPlay {

    public interface Model {
        public void setPresenter(Presenter presenter);
        public void start();   
        public void startGame();
        public BallPojo getBallPojoToDraw();
        public List<RacketPojo> getRacketsPojoToDraw();
        public boolean checkMinClientsAmount();
        public int getAdjustedHorizontalLimit();
        public int getAdjustedVerticalLimit();
        public void setBallColor(Color color);
    }

    public interface View {
        public void setPresenter(Presenter presenter);
        public void begin();
        public void beginGame();
        public void changeClientsAmount(int clientsAmount);       
    }

    public interface Presenter {
        public void setModel(Model model);
        public void setView(View view);
        public void makeMVP();
        public void begin();

        public void beginGame();
        public void changeClientsAmount(int clientsAmount);
        
        public BallPojo getBallPojoToDraw();
        public List<RacketPojo> getRacketsPojoToDraw();
        public boolean checkMinClientsAmount();
        public int getAdjustedHorizontalLimit();
        public int getAdjustedVerticalLimit();
        public void setBallColor(Color color);
    }
}
