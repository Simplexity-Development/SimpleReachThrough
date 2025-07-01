package simplexity.simplereachthrough.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.Attachable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.simplereachthrough.SimpleReachThrough;
import simplexity.simplereachthrough.commands.ReachToggle;
import simplexity.simplereachthrough.config.ConfigHandler;
import simplexity.simplereachthrough.hooks.GriefPreventionHook;

import java.util.ArrayList;

public class EntityInteractListener implements Listener {

    private final ArrayList<EntityType> bypassList = ConfigHandler.getInstance().getEntityList();

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityInteract(PlayerInteractEntityEvent interactEntityEvent) {
        Player player = interactEntityEvent.getPlayer();
        if (interactEntityEvent.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (player.isSneaking()) return;
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        boolean toggleEnabled = playerPDC.getOrDefault(ReachToggle.toggleKey, PersistentDataType.BOOLEAN, true);
        if (!toggleEnabled) return;
        Entity entityClicked = interactEntityEvent.getRightClicked();
        if (!bypassList.contains(entityClicked.getType())) return;
        if (!(entityClicked instanceof Attachable attachableBlock)) return;
        if ((entityClicked instanceof ItemFrame itemFrame) && !doesItemFramePassConfigChecks(itemFrame)) return;
        if ((entityClicked instanceof Painting) && !doesPaintingPassConfigChecks()) return;
        Inventory containerInv = getContainerInventory(attachableBlock, entityClicked, player, interactEntityEvent);
        if (containerInv == null) return;
        interactEntityEvent.setCancelled(true);
        player.openInventory(containerInv);
    }

    private Inventory getContainerInventory(Attachable attachableBlock, Entity entityClicked, Player player, Event event) {
        Location entityLocation = entityClicked.getLocation().toBlockLocation();
        BlockFace face = attachableBlock.getAttachedFace();
        int attachedXOffset = face.getModX();
        int attachedYOffset = face.getModY();
        int attachedZOffset = face.getModZ();
        Location attachedBlockLocation = entityLocation.add(attachedXOffset, attachedYOffset, attachedZOffset);
        Block blockAtLocation = attachedBlockLocation.getBlock();
        if (!(blockAtLocation.getState() instanceof Container containerBlock)) return null;
        if (SimpleReachThrough.getInstance().isUsingGriefPrevention()) {
            boolean canOpen = GriefPreventionHook.canOpenContainer(player, blockAtLocation, event);
            if (!canOpen) return null;
        }
        return containerBlock.getInventory();
    }

    private boolean doesItemFramePassConfigChecks(ItemFrame itemFrame) {
        if (!ConfigHandler.getInstance().isItemFramesEnabled()) return false;
        if (!ConfigHandler.getInstance().isShouldBypassEmptyItemFrames() && itemFrame.getItem().getType().equals(Material.AIR))
            return false;
        return true;
    }

    private boolean doesPaintingPassConfigChecks() {
        return ConfigHandler.getInstance().isPaintingsEnabled();
    }
}
