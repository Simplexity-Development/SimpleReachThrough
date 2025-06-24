package simplexity.simplereachthrough.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import simplexity.simplereachthrough.config.LocaleHandler;
import simplexity.simplereachthrough.config.ConfigHandler;

public class SRReload implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        ConfigHandler.getInstance().reloadConfigValues();
        LocaleHandler.getInstance().loadLocale();
        sender.sendRichMessage(LocaleHandler.getInstance().getPluginReload());
        return false;
    }
}
