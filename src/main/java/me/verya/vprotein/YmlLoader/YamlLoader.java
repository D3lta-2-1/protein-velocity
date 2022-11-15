package me.verya.vprotein.YmlLoader;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import me.verya.vprotein.backServer.GameServer;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class YamlLoader {
    /*public class YamlGameServer {
    }
    public static void load(File file, Logger logger)
    {
        logger.info("try to load " + file.getAbsolutePath());
        var yaml = new Yaml(new Constructor(YamlLoader.YamlGameServer.class));

        List<YamlGameServer> gameServerInfos = yaml.load(file.getPath());
        if(gameServerInfos.isEmpty())
        {
            logger.info("no infos");
            return;
        }
        for(var info : gameServerInfos)
        {
            if(info.getIp() != null)
            {
                logger.info(info.getIp());
            }
            for(var games : info.getGames())
            {
                logger.info(games);
            }
        }
    }*/

    public static List<GameServer> load(ProxyServer proxy)
    {
        //ugly hardcoded config because I don't want to spend time on snake-yml, pr opened
        List<GameServer> serversList = new ArrayList<>();
        var supportedGame = new ArrayList<String>();
        supportedGame.add("single/baba");
        supportedGame.add("single/cube");
        supportedGame.add("single/tombstone");

        var server1 = new GameServer(proxy.registerServer(new ServerInfo("test1", InetSocketAddress.createUnresolved("192.168.0.186", 20000))),
                        supportedGame);
        serversList.add(server1);

        return serversList;
    }
}
