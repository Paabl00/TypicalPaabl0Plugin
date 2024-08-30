package me.Paabl0.net.misc;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.commands.AnalyticCommand;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AnalyticTool {
    private TypicalPabloPlugin typicalPabloPlugin;
    private ServerSocket server;
    private AnalyticSocketThread analyticSocketThread;
    private List<String> messageBuffer = new ArrayList<>();

   // private List<String> copiedMessageBuffer;

    public AnalyticTool(TypicalPabloPlugin typicalPabloPlugin) {
        this.typicalPabloPlugin = typicalPabloPlugin;

        try {
            server = new ServerSocket(1234);
            analyticSocketThread = new AnalyticSocketThread(server);
            analyticSocketThread.start();
            serverSyncMessages();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void serverSyncMessages() {

        if (!analyticSocketThread.getClients().isEmpty()) {
            for (Socket client : analyticSocketThread.getClients().keySet()) {
                if(client.isClosed()){
                    analyticSocketThread.getClients().remove(client);
                }
                if (!typicalPabloPlugin.getAnalyticCommand().getPlayerIPAdressList().isEmpty()) {
                    if (typicalPabloPlugin.getAnalyticCommand().getPlayerIPAdressList().contains(client.getInetAddress().getHostAddress())) {
                        if (!messageBuffer.isEmpty()) {
                            for (String lines : messageBuffer) {
                                analyticSocketThread.getClients().get(client).println(lines);
                            }
                        }
                        if (typicalPabloPlugin.getAnalyticCommand().getPlayerIPAdressList().getLast().equals(client.getInetAddress().getHostAddress())) {
                            messageBuffer.clear();
                        }
                    }
                }
            }
        }
    }

    public List<String> getMessageBuffer() {
        return messageBuffer;
    }
}
