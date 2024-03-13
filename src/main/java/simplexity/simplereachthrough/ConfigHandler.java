package simplexity.simplereachthrough;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;

public class ConfigHandler {
    
    private static ConfigHandler instance;
    
    private boolean itemFramesEnabled, shouldBypassEmptyItemFrames, signsEnabled, shouldBypassUnwaxedSigns,
            paintingsEnabled;
    
    
    private final ArrayList<EntityType> passableEntities = new ArrayList<>();
    
    public ConfigHandler() {
    }
    
    public static ConfigHandler getInstance() {
        if (instance == null) instance = new ConfigHandler();
        return instance;
    }
    
    public void reloadConfigValues() {
        SimpleReachThrough.getInstance().reloadConfig();
        FileConfiguration config = SimpleReachThrough.getInstance().getConfig();
        itemFramesEnabled = config.getBoolean("item-frames.reach-through");
        shouldBypassEmptyItemFrames = config.getBoolean("item-frames.reach-through-empty");
        signsEnabled = config.getBoolean("signs.reach-through");
        shouldBypassUnwaxedSigns = config.getBoolean("signs.reach-through-unwaxed");
        paintingsEnabled = config.getBoolean("paintings.reach-through");
        if (itemFramesEnabled) {
            passableEntities.add(EntityType.ITEM_FRAME);
            passableEntities.add(EntityType.GLOW_ITEM_FRAME);
        }
        if (paintingsEnabled) passableEntities.add(EntityType.PAINTING);
        
    }
    
    public boolean isItemFramesEnabled() {
        return itemFramesEnabled;
    }
    
    public boolean isShouldBypassEmptyItemFrames() {
        return shouldBypassEmptyItemFrames;
    }
    
    public boolean isSignsEnabled() {
        return signsEnabled;
    }
    
    public boolean isShouldBypassUnwaxedSigns() {
        return shouldBypassUnwaxedSigns;
    }
    
    public boolean isPaintingsEnabled() {
        return paintingsEnabled;
    }
    
    public ArrayList<EntityType> getEntityList() {
        return passableEntities;
    }
    
    
}
