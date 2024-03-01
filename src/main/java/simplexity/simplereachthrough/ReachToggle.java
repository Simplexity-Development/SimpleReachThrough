package simplexity.simplereachthrough;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class ReachToggle implements CommandExecutor {
    public static final NamespacedKey toggleKey =  new NamespacedKey(SimpleReachThrough.getInstance(), "toggle");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)){
            sender.sendRichMessage(ConfigHandler.getInstance().getOnlyPlayer());
            return false;
        }
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        boolean currentSetting = playerPDC.getOrDefault(toggleKey, PersistentDataType.BOOLEAN, true);
        playerPDC.set(toggleKey, PersistentDataType.BOOLEAN, !currentSetting);
        if (!currentSetting) {
            player.sendRichMessage(ConfigHandler.getInstance().getToggleEnabled());
        } else {
            player.sendRichMessage(ConfigHandler.getInstance().getToggleDisabled());
        }
        return true;
    }
}
