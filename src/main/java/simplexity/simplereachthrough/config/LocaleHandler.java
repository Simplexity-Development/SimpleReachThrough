package simplexity.simplereachthrough.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import simplexity.simplereachthrough.SimpleReachThrough;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@SuppressWarnings("unused")
public class LocaleHandler {

        private static LocaleHandler instance;
        private final String fileName = "locale.yml";
        private final File localeFile = new File(SimpleReachThrough.getInstance().getDataFolder(), fileName);
        private final FileConfiguration localeConfig = new YamlConfiguration();
    private String pluginReload, toggleEnabled, toggleDisabled, onlyPlayer;

        private LocaleHandler() {
            if (!localeFile.exists()) {
                SimpleReachThrough.getInstance().saveResource(fileName, false);
            }
        }

        public static LocaleHandler getInstance() {
            if (instance == null) instance = new LocaleHandler();
            return instance;
        }

        public FileConfiguration getLocaleConfig() {
            return localeConfig;
        }

        public void loadLocale() {
            try {
                localeConfig.load(localeFile);
            } catch (IOException | InvalidConfigurationException e) {
                SimpleReachThrough.getInstance().getLogger().severe("Error loading locale.yml");
                SimpleReachThrough.getInstance().getLogger().severe(Arrays.toString(e.getStackTrace()));
            }
            pluginReload = localeConfig.getString("plugin-reloaded");
            toggleEnabled = localeConfig.getString("reach-through.enabled");
            toggleDisabled = localeConfig.getString("reach-through.disabled");
            onlyPlayer = localeConfig.getString("only-player");
        }

    public String getPluginReload() {
        return pluginReload;
    }

    public String getToggleEnabled() {
        return toggleEnabled;
    }

    public String getToggleDisabled() {
        return toggleDisabled;
    }

    public String getOnlyPlayer() {
        return onlyPlayer;
    }

}
