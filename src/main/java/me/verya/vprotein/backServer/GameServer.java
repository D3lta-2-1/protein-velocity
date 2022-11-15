package me.verya.vprotein.backServer;

import com.velocitypowered.api.proxy.server.RegisteredServer;

import java.util.ArrayList;
import java.util.List;

//simple struct to represent a Minecraft server which is hosting games
public class GameServer {
    private final RegisteredServer registeredServer;
    private final List<String> supportedGames;
    private final List<String> gamesWaitingForPlayers;

    public GameServer(RegisteredServer server, List<String> supportedGames)
    {
        this.registeredServer = server;
        this.supportedGames = supportedGames;
        this.gamesWaitingForPlayers = new ArrayList<>();

    }

    public RegisteredServer getRegisteredSever() {
        return registeredServer;
    }

    public List<String> getSupportedGames(){
        return supportedGames;
    }
    public int getPlayerCount()
    {
        return registeredServer.getPlayersConnected().size();
    }

    public boolean isLookingForPlayer(String gameName)
    {
        return gamesWaitingForPlayers.contains(gameName);
    }

}
