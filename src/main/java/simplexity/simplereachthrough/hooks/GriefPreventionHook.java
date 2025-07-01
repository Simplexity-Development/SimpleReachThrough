package simplexity.simplereachthrough.hooks;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.function.Supplier;

public class GriefPreventionHook {

    public static boolean canOpenContainer(Player player, Block block, Event event) {
        Location blockLocation = block.getLocation();
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(blockLocation, false, null);
        if (claim == null) return true;
        Supplier<String> result = claim.checkPermission(player, ClaimPermission.Inventory, event);
        return result == null;
    }
}
