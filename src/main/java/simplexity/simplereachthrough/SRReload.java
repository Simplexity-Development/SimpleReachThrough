package simplexity.simplereachthrough;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SRReload implements CommandExecutor {
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        SimpleReachThrough.getInstance().reloadConfig();
        ConfigHandler.getInstance().reloadConfigValues();
        sender.sendRichMessage(ConfigHandler.getInstance().getPluginReload());
        return false;
    }
}