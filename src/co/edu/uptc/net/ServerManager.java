package co.edu.uptc.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.pojos.BallPojo;
import co.edu.uptc.pojos.ClientPojo;
import co.edu.uptc.pojos.RacketPojo;

public class ServerManager {

    private ServerSocket serverSocket;
    private List<ClientPojo> clients;
    private boolean isSearching;
    private BallPojo ballPojo;

    public ServerManager() {
        clients = new ArrayList<>();
        ballPojo = new BallPojo();
    }

    public void begin() {
        try {
            serverSocket = new ServerSocket(9001);
            Thread sendClientsPackageThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    waitForClients(serverSocket);
                    while (true) {
                        sendClientsPackage();
                    }
                }
            });
            sendClientsPackageThread.setName("Send Clients Package Thread");
            sendClientsPackageThread.start();

        } catch (IOException e) {
            System.out.println("Server fail");
            return;
        }

    }

    private void waitForClients(ServerSocket serverSocket) {
        isSearching = true;

        while (isSearching) {
            Socket socketClient;
            try {
                socketClient = serverSocket.accept();
                ClientPojo client = new ClientPojo();
                client.setRacketPojo(new RacketPojo());
                client.setIpAddress(socketClient.getInetAddress().getHostAddress());
                clients.add(client);
                sendClientsPackage();
            } catch (IOException e) {
                System.out.println("Server fail");
                return;
            }

        }

    }

    public void sendClientsPackage() {
        for (ClientPojo clientPojo : clients) {
            try {
                clientPojo.setClientsAmount(clients.size());
                clientPojo.setBallPojo(ballPojo);
                clientPojo.setStarted(!isSearching);
                Socket socket = new Socket(clientPojo.getIpAddress(), 9002);
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(clientPojo);

                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                ClientPojo client = (ClientPojo) input.readObject();
                clientPojo.setRacketPojo(client.getRacketPojo());
                socket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void beginEvents() {
        isSearching = false;
        clients.get(0).setBoardPosition(0);
        clients.get(1).setBoardPosition(clients.size() - 1);
        for (int i = 2; i < clients.size(); i++) {
            clients.get(i).setBoardPosition(i - 1);
        }
        sendClientsPackage();

    }

    public boolean isSearching() {
        return isSearching;
    }

    public List<ClientPojo> getClients() {
        return clients;
    }

    public void setBallPojo(BallPojo ballPojo) {
        this.ballPojo = ballPojo;
    }

    public BallPojo getBallPojo() {
        return ballPojo;
    }

}
