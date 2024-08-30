package me.Paabl0.net.misc;

import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalyticSocketThread extends Thread{

    private HashMap<Socket, PrintWriter> clients = new HashMap<>();
    private ServerSocket serverSocket;
    public AnalyticSocketThread(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run(){
        try {
            while (true) {
                Socket client = serverSocket.accept();
                clients.put(client, new PrintWriter(client.getOutputStream(), true));
            }
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public HashMap<Socket, PrintWriter> getClients() {
        return clients;
    }
}
