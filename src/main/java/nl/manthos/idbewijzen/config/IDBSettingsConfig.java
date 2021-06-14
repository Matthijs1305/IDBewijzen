package nl.manthos.idbewijzen.config;

import nl.manthos.idbewijzen.IDBewijzen;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class IDBSettingsConfig {

    private String fileName = "config.yml";
    private File file;
    private YamlConfiguration config;

    public IDBSettingsConfig(IDBewijzen main) {

        this.file = new File(main.getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = new YamlConfiguration().loadConfiguration(file);
        this.setup();
        this.save();
    }

    private void setup() {
        config.options().copyDefaults(true);
        config.addDefault("kleuren.primair", "&b");
        config.addDefault("kleuren.secundair", "&3");
        this.save();
    }

    public YamlConfiguration getConfig() {
         return this.config;
    }

    private void save() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
