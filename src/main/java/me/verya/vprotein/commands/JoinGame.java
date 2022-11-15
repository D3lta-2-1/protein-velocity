package me.verya.vprotein.commands;

import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.verya.vprotein.backServer.GameServerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.Arrays;
import java.util.List;

public class JoinGame implements SimpleCommand {

    private final GameServerManager gameServerManager;

    JoinGame(GameServerManager gameServerManager)
    {
        this.gameServerManager = gameServerManager;
    }

    public static void register(Object plugin, ProxyServer proxy, GameServerManager gameServerManager)
    {
        var commandManager = proxy.getCommandManager();
        CommandMeta meta = commandManager.metaBuilder("joinGame").aliases("j").plugin(plugin).build();
        commandManager.register(meta, new JoinGame(gameServerManager));
    }

    @Override
    public void execute(Invocation invocation) {
        var src = invocation.source();
        Player player = src instanceof Player ? (Player)src : null;
        if(player == null)  return;
       List<String> args = Arrays.asList(invocation.arguments());
       if(args.isEmpty())
       {
           player.sendMessage(Component.text("no game specified, please use /join [game]").color(TextColor.color(0xE30500)));
           return;
       }
       try
       {
           gameServerManager.joinGame(player, args.get(0));
       }
       catch (RuntimeException e)
       {
           player.sendMessage(Component.text(e.getMessage()).color(TextColor.color(0xE30500)));
       }
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return true; //invocation.source().hasPermission("command.test");
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        return List.of();
    }
}
