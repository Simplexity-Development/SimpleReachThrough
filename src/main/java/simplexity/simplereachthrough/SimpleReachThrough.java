package simplexity.simplereachthrough;

import me.youhavetrouble.yardwatch.Protection;
import org.bukkit.plugin.java.JavaPlugin;
import simplexity.simplereachthrough.commands.ReachToggle;
import simplexity.simplereachthrough.commands.SRReload;
import simplexity.simplereachthrough.config.ConfigHandler;
import simplexity.simplereachthrough.config.LocaleHandler;
import simplexity.simplereachthrough.listeners.InteractionListener;

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

    public boolean hasYardWatchProvider() {
        boolean classExists = false;
        try {
            Class.forName("me.youhavetrouble.yardwatch.Protection");
            classExists = true;
        } catch (ClassNotFoundException e) {
            // ignore
        }
        return classExists && this.getServer().getServicesManager().isProvidedFor(Protection.class);
    }

}
