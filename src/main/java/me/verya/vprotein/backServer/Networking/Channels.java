package me.verya.vprotein.backServer.Networking;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import me.verya.vprotein.Vprotein;

public class Channels {
    public static final ChannelIdentifier LOGIN_CHANNEL = MinecraftChannelIdentifier.create(Vprotein.ID, "login");
    public static void registerChannels(ProxyServer proxy)
    {
        var registar = proxy.getChannelRegistrar();
        registar.register(LOGIN_CHANNEL);
    }
}
