package simplexity.simplereachthrough;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SRReload implements CommandExecutor {
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        ConfigHandler.getInstance().reloadConfigValues();
        LocaleHandler.getInstance().loadLocale();
        sender.sendRichMessage(LocaleHandler.getInstance().getPluginReload());
        return false;
    }
}
