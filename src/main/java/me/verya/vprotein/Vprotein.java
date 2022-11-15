package me.verya.vprotein;

import com.google.inject.Inject;
import com.velocitypowered.api.event.player.ServerLoginPluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.verya.vprotein.backServer.GameServerManager;
import me.verya.vprotein.backServer.Networking.Channels;
import me.verya.vprotein.commands.JoinGame;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;


@Plugin(
        id = "vprotein",
        name = "Vprotein",
        version = BuildConstants.VERSION,
        description = "proxy implementation of protein",
        authors = {"D3lta"}
)
public class Vprotein {


    public static final String ID = "protein";
    private final Logger logger;
    private final ProxyServer proxy;

    @Inject
    public Vprotein(Logger logger, ProxyServer proxy)
    {
        this.logger = logger;
        this.proxy = proxy;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event)
    {

        Channels.registerChannels(proxy);
        GameServerManager serversManager = new GameServerManager(this,  proxy);
        JoinGame.register(this, proxy, serversManager);
        logger.info("protein Loaded");
    }


}
