package simplexity.simplereachthrough.hooks;

import me.youhavetrouble.yardwatch.Protection;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

import java.util.Collection;

public class YardWatchHook {

    public static boolean canInteractWithContainer(Player player, Block block) {
        ServicesManager servicesManager = Bukkit.getServicesManager();
        Collection<RegisteredServiceProvider<Protection>> protections = servicesManager.getRegistrations(Protection.class);
        for (RegisteredServiceProvider<Protection> protection : protections) {
            if (protection.getProvider().canInteract(player, block.getState(true))) continue;
            return false;
        }
        return true;
    }
}
