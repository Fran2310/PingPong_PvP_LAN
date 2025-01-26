import co.edu.uptc.presenters.ClientPresenter;
import co.edu.uptc.presenters.ServerPresenter;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length != 0) {
            if (args[0].equals("server")) {
                ServerPresenter presenter = new ServerPresenter();
                presenter.begin();
            } else if (args[0].equals("client")) {
                ClientPresenter clientPresenter = new ClientPresenter();
                clientPresenter.begin();
            } else {
                System.out.println("Por favor, use 'server' o 'client' como primer argumento.");
            }
        } else {
            System.out.println("Por favor, use 'server' o 'client' como primer argumento.");
        } 
    }
}
