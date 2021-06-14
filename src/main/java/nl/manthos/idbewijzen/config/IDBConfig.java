package nl.manthos.idbewijzen.config;

import nl.manthos.idbewijzen.IDBewijzen;
import nl.manthos.idbewijzen.util.DataGenerator;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IDBConfig {

    private String fileName = "iddata.yml";
    private File file;
    private YamlConfiguration config;

    public IDBConfig(IDBewijzen main) {

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
        this.config.options().copyDefaults(true);
        this.config.addDefault("last", 0);
        this.save();
    }

    public void addID(OfflinePlayer player, String city, String sex) {
        int old = this.config.getInt("last");
        int newValue = old + 1;
        String header = "ids." + player.getUniqueId();
        this.config.set(header + ".stad", city);
        this.config.set(header + ".geslacht", sex);
        this.config.set(header + ".nummer", newValue);
        this.config.set("last", newValue);
        this.save();
    }

    public void removeID(OfflinePlayer player) {
        String header = "ids." + player.getUniqueId();
        this.config.set(header, null);
        this.save();
    }

    public boolean exists(OfflinePlayer player) {
        String header = "ids." + player.getUniqueId();
        return this.config.contains(header);
    }

    public String getCity(OfflinePlayer player) {
        String header = "ids." + player.getUniqueId();
        return this.config.getString(header + ".stad");
    }

    public String getSex(OfflinePlayer player) {
        String header = "ids." + player.getUniqueId();
        return this.config.getString(header + ".geslacht");
    }

    public int getNextID() {
        int old = this.config.getInt("last");
        int newValue = old + 1;
        return newValue;
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
