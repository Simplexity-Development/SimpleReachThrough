package simplexity.simplereachthrough.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.simplereachthrough.SimpleReachThrough;
import simplexity.simplereachthrough.commands.ReachToggle;
import simplexity.simplereachthrough.config.ConfigHandler;
import simplexity.simplereachthrough.hooks.GriefPreventionHook;

@SuppressWarnings("RedundantIfStatement")
public class InteractionListener implements Listener {


    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onInteractEvent(PlayerInteractEvent interactEvent) {
        Player player = interactEvent.getPlayer();
        Block blockClicked = interactEvent.getClickedBlock();
        if (interactEvent.getHand() == null || interactEvent.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (player.isSneaking()) return;
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        boolean toggleEnabled = playerPDC.getOrDefault(ReachToggle.toggleKey, PersistentDataType.BOOLEAN, true);
        if (!toggleEnabled) return;
        if (blockClicked == null) return;
        if (!(blockClicked.getState() instanceof Sign signClicked)) return;
        if (!(blockClicked.getBlockData() instanceof Directional directionalBlock)) return;
        if (!doesSignPassConfigChecks(signClicked)) return;
        Inventory inventoryClicked = getContainerInventory(directionalBlock, blockClicked, player, interactEvent);
        if (inventoryClicked == null) return;
        interactEvent.setCancelled(true);
        player.openInventory(inventoryClicked);
    }

    private Inventory getContainerInventory(Directional directionalBlock, Block blockClicked, Player player, Event event) {
        Location blockLocation = blockClicked.getLocation().toBlockLocation();
        BlockFace face = directionalBlock.getFacing().getOppositeFace();
        int attachedXOffset = face.getModX();
        int attachedYOffset = face.getModY();
        int attachedZOffset = face.getModZ();
        Location attachedBlockLocation = blockLocation.add(attachedXOffset, attachedYOffset, attachedZOffset);
        Block blockAtLocation = attachedBlockLocation.getBlock();
        if (!(blockAtLocation.getState() instanceof Container containerBlock)) return null;
        if (SimpleReachThrough.getInstance().isUsingGriefPrevention()) {
            boolean canOpen = GriefPreventionHook.canOpenContainer(player, blockClicked, event);
            if (!canOpen) return null;
        }
        return containerBlock.getInventory();
    }

    private boolean doesSignPassConfigChecks(Sign sign) {
        if (!ConfigHandler.getInstance().isSignsEnabled()) return false;
        if (!ConfigHandler.getInstance().isShouldBypassUnwaxedSigns() && !sign.isWaxed()) return false;
        return true;
    }
}
