package me.verya.vprotein.backServer.Networking;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerLoginPluginMessageEvent;
import com.velocitypowered.api.proxy.ConnectionRequestBuilder;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.verya.vprotein.backServer.GameServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//communicate with the networking.LoginHandler on the serverSide
public class JoinGameProtocol {

    private Map<Player, String> gameRequestBuffer = new HashMap<>();
    public JoinGameProtocol(Object plugin, ProxyServer proxy)
    {
        proxy.getEventManager().register(plugin, this);
    }
    public void connectTo(Player player, GameServer server, String gameName)
    {
        player.sendMessage(Component.text("processing request"));
        //memorize it for the login request of the server
        gameRequestBuffer.put(player, gameName);
        var connectionRequest = player.createConnectionRequest(server.getRegisteredSever());
        var futureResult = connectionRequest.connect();
        futureResult.thenAccept((result) -> {
            if(!result.isSuccessful())
            {
                gameRequestBuffer.remove(player);
                var message = Component.text("failed due to an internal error : ").append(result.getReasonComponent().orElse(Component.text("unknown")));
                player.sendMessage(message.color(TextColor.color(0xE30500)));
            }
            else
            {
                player.sendMessage(Component.text("connection successful"));
            }
        });
    }


    @Subscribe
    public void onLoginMessage(ServerLoginPluginMessageEvent event)
    {
        var player = event.getConnection().getPlayer();
        if(!event.getIdentifier().equals(Channels.LOGIN_CHANNEL)) return;
        String gameName = gameRequestBuffer.get(player);
        gameRequestBuffer.remove(player);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(gameName);
        var response = ServerLoginPluginMessageEvent.ResponseResult.reply(out.toByteArray());
        event.setResult(response);
    }

    //crade
    static void joinGame(Player player)
    {

    }
}
