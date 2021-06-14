package nl.manthos.idbewijzen.config;

import nl.manthos.idbewijzen.IDBewijzen;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

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
        config.addDefault("no-permission", "&b&lIDBewijzen &3- &bJe hebt hier geen permissies voor!");
        config.addDefault("idbewijs.name", "&b&lID Bewijs");
        config.addDefault("idbewijs.glow", false);
        config.addDefault("kleuren.primair", "&b");
        config.addDefault("kleuren.secundair", "&3");
        config.addDefault("kleuren.lores", "&7");
        config.addDefault("itemstacks.player.color", "&b&l");
        config.addDefault("itemstacks.stad.name", "&b&lStad");
        config.addDefault("itemstacks.geslacht.name", "&b&lGeslacht");
        config.addDefault("itemstacks.afgiftedatum.name", "&b&lAfgiftedatum");
        config.addDefault("itemstacks.stad.glow", false);
        config.addDefault("itemstacks.geslacht.glow", false);
        config.addDefault("itemstacks.afgiftedatum.glow", false);
        this.save();
    }

    public YamlConfiguration getConfig() {
         return this.config;
    }

    public void reload() {
        this.save();
    }

    private void save() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
