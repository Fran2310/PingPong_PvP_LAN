package co.edu.uptc.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import co.edu.uptc.pojos.ClientPojo;
import co.edu.uptc.pojos.RacketPojo;

public class ClientManager {

    private String serverIpAdress;
    private int clientsAmount;
    private ClientPojo clientPojo;
    private RacketPojo racketPojo;

    public ClientManager() {
        clientPojo = new ClientPojo();
        racketPojo = new RacketPojo();
        listenServerPackages();
    }

    private void listenServerPackages() {
        Thread listenServerPackagesThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(9002);
                    while (true) {
                        Socket socket = serverSocket.accept();
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        clientPojo = (ClientPojo) input.readObject();
                        clientsAmount = clientPojo.getClientsAmount();

                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        clientPojo.setRacketPojo(racketPojo);
                        output.writeObject(clientPojo);
                        socket.close();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        listenServerPackagesThread.setName("Listen Server Packages Thread");
        listenServerPackagesThread.start();

    }

    public void pingWithServer() throws UnknownHostException, IOException {
        Socket socket = new Socket(serverIpAdress, 9001);
        socket.close();
    }

    public String getServerIpAdress() {
        return serverIpAdress;
    }

    public void setServerIpAdress(String serverIpAdress) {
        this.serverIpAdress = serverIpAdress;
    }

    public int getClientsAmount() {
        return clientsAmount;
    }

    public boolean isStarted() {
        return clientPojo.isStarted();
    }

    public ClientPojo getClientPojo() {
        return clientPojo;
    }

    public RacketPojo getRacketPojo() {
        return racketPojo;
    }

    public void setRacketPojo(RacketPojo racketPojo) {
        this.racketPojo = racketPojo;
    }

}
