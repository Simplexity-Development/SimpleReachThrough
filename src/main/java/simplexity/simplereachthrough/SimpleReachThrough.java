package simplexity.simplereachthrough;

import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleReachThrough extends JavaPlugin {
    
    public static SimpleReachThrough instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        ConfigHandler.getInstance().reloadConfigValues();
        LocaleHandler.getInstance().loadLocale();
        this.getServer().getPluginManager().registerEvents(new InteractionListener(), this);
        this.getCommand("srreload").setExecutor(new SRReload());
        this.getCommand("reachtoggle").setExecutor(new ReachToggle());
    }
    
    public static SimpleReachThrough getInstance() {
        return instance;
    }
    
}
