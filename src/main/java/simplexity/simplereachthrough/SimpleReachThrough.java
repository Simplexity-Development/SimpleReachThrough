package simplexity.simplereachthrough;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import simplexity.simplereachthrough.commands.ReachToggle;
import simplexity.simplereachthrough.commands.SRReload;
import simplexity.simplereachthrough.config.ConfigHandler;
import simplexity.simplereachthrough.config.LocaleHandler;
import simplexity.simplereachthrough.listeners.EntityInteractListener;
import simplexity.simplereachthrough.listeners.InteractionListener;

public final class SimpleReachThrough extends JavaPlugin {

    public static SimpleReachThrough instance;
    private boolean usingGriefPrevention;

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        ConfigHandler.getInstance().reloadConfigValues();
        LocaleHandler.getInstance().loadLocale();
        getServer().getPluginManager().registerEvents(new InteractionListener(), this);
        getServer().getPluginManager().registerEvents(new EntityInteractListener(), this);
        getCommand("srreload").setExecutor(new SRReload());
        getCommand("reachtoggle").setExecutor(new ReachToggle());
        setupHooks();

    }

    public static SimpleReachThrough getInstance() {
        return instance;
    }

    private void setupHooks() {
        Plugin griefPrevention = getServer().getPluginManager().getPlugin("GriefPrevention");
        usingGriefPrevention = griefPrevention != null && griefPrevention.isEnabled();
    }

    public boolean isUsingGriefPrevention() {
        return usingGriefPrevention;
    }

}
