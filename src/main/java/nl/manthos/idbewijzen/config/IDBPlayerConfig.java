package nl.manthos.idbewijzen.config;

import nl.manthos.idbewijzen.IDBewijzen;
import nl.manthos.idbewijzen.util.DataGenerator;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IDBPlayerConfig {

    private String fileName = "playerdata.yml";
    private File file;
    private YamlConfiguration config;

    public IDBPlayerConfig(IDBewijzen main) {

        this.file = new File(main.getDataFolder(), fileName);
        if (!this.file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = new YamlConfiguration().loadConfiguration(file);
        this.save();
    }

    public void addPlayer(OfflinePlayer player) {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        String header = "players." + player.getUniqueId();
        this.config.set(header + ".geboren", DataGenerator.birthday());
        this.config.set(header + ".lengte", DataGenerator.lengte());
        this.config.set(header + ".afgiftedatum", format.format(now));
        this.save();
    }

    public String getBirthday(OfflinePlayer player) {
        String header = "players." + player.getUniqueId();
        return this.config.getString(header + ".geboren");
    }

    public String getLength(OfflinePlayer player) {
        String header = "players." + player.getUniqueId();
        return this.config.getString(header + ".lengte");
    }

    public String getDate(OfflinePlayer player) {
        String header = "players." + player.getUniqueId();
        return this.config.getString(header + ".afgiftedatum");
    }

    public boolean exists(OfflinePlayer player) {
        String header = "players." + player.getUniqueId();
        return this.config.contains(header);
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
