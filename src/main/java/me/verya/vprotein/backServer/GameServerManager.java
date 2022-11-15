package me.verya.vprotein.backServer;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import me.verya.vprotein.YmlLoader.YamlLoader;
import me.verya.vprotein.backServer.Networking.JoinGameProtocol;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameServerManager {
    private final List<GameServer> gameServersList;
    private JoinGameProtocol joinGameProtocol;

    public GameServerManager(Object plugin, ProxyServer proxy)
    {
        this.gameServersList = YamlLoader.load(proxy);
        this.joinGameProtocol = new JoinGameProtocol(plugin, proxy);
    }

    public boolean isARegisteredGameServer(RegisteredServer server)
    {
        for(var gameServer: gameServersList)
        {
            if(gameServer.getRegisteredSever().equals(server)) return true;
        }
        return false;
    }

    private List<GameServer> findHostsFor(String game)
    {
        List<GameServer> availableHosts = new ArrayList<>();
        for(var gameServer : gameServersList)
        {
            if(gameServer.getSupportedGames().contains(game))
                availableHosts.add(gameServer);
        }
        return availableHosts;
    }

    private GameServer findServerWaitingForThisGame(String gameName)
    {
        GameServer chosenHost = null;
        for(var server: gameServersList)
        {
            if(server.isLookingForPlayer(gameName))
            {
                chosenHost = server;
                break;
            }
        }
        return chosenHost;
    }

    private @NotNull GameServer findGameServer(String gameName)
    {
        var chosenHost = findServerWaitingForThisGame(gameName);
        if(chosenHost ==null)
        {
            var availableHosts = findHostsFor(gameName);
            if(availableHosts.isEmpty()) throw new RuntimeException("no host available for this game");
            int playerCount = Integer.MAX_VALUE;
            for(var host: availableHosts)
            {
                int hostCount = host.getPlayerCount();
                playerCount = Math.min(playerCount, hostCount);
                chosenHost = host;
            }
        }
        return chosenHost;
    }

    public void joinGame(Player player, String gameName) throws RuntimeException
    {
        var host = findGameServer(gameName);
        joinGameProtocol.connectTo(player, host, gameName);
    }
}
