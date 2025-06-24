package simplexity.simplereachthrough.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.Attachable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.simplereachthrough.commands.ReachToggle;
import simplexity.simplereachthrough.config.ConfigHandler;

import java.util.ArrayList;

public class InteractionListener implements Listener {

    private final ArrayList<EntityType> bypassList = ConfigHandler.getInstance().getEntityList();

    @EventHandler
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
        if (entityClicked instanceof ItemFrame itemFrame) {
            if (!doesItemFramePassConfigChecks(itemFrame)) return;
        }
        if (entityClicked instanceof Painting) {
            if (!doesPaintingPassConfigChecks()) return;
        }
        Inventory containerInv = getContainerInventory(attachableBlock, entityClicked);
        if (containerInv == null) return;
        interactEntityEvent.setCancelled(true);
        player.openInventory(containerInv);
    }

    @EventHandler
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
        Inventory inventoryClicked = getContainerInventory(directionalBlock, blockClicked);
        if (inventoryClicked == null) return;
        interactEvent.setCancelled(true);
        player.openInventory(inventoryClicked);
    }

    private Inventory getContainerInventory(Attachable attachableBlock, Entity entityClicked) {
        Location entityLocation = entityClicked.getLocation().toBlockLocation();
        BlockFace face = attachableBlock.getAttachedFace();
        int attachedXOffset = face.getModX();
        int attachedYOffset = face.getModY();
        int attachedZOffset = face.getModZ();
        Location attachedBlockLocation = entityLocation.add(attachedXOffset, attachedYOffset, attachedZOffset);
        Block blockAtLocation = attachedBlockLocation.getBlock();
        if (!(blockAtLocation.getState() instanceof Container containerBlock)) return null;
        return containerBlock.getInventory();
    }

    private Inventory getContainerInventory(Directional directionalBlock, Block blockClicked) {
        Location blockLocation = blockClicked.getLocation().toBlockLocation();
        BlockFace face = directionalBlock.getFacing().getOppositeFace();
        int attachedXOffset = face.getModX();
        int attachedYOffset = face.getModY();
        int attachedZOffset = face.getModZ();
        Location attachedBlockLocation = blockLocation.add(attachedXOffset, attachedYOffset, attachedZOffset);
        Block blockAtLocation = attachedBlockLocation.getBlock();
        if (!(blockAtLocation.getState() instanceof Container containerBlock)) return null;
        return containerBlock.getInventory();
    }

    private boolean doesItemFramePassConfigChecks(ItemFrame itemFrame) {
        if (!ConfigHandler.getInstance().isItemFramesEnabled()) return false;
        //todo: fix this when itemstacks change (no longer check for air)
        if (!ConfigHandler.getInstance().isShouldBypassEmptyItemFrames() && itemFrame.getItem().getType().equals(Material.AIR)) return false;
        return true;
    }

    private boolean doesSignPassConfigChecks(Sign sign) {
        if (!ConfigHandler.getInstance().isSignsEnabled()) return false;
        if (!ConfigHandler.getInstance().isShouldBypassUnwaxedSigns() && !sign.isWaxed()) return false;
        return true;
    }

    private boolean doesPaintingPassConfigChecks() {
        return ConfigHandler.getInstance().isPaintingsEnabled();
    }
}
