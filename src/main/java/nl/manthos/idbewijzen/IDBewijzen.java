package nl.manthos.idbewijzen;

import nl.manthos.idbewijzen.commands.IDBCommand;
import nl.manthos.idbewijzen.config.IDBConfig;
import nl.manthos.idbewijzen.config.IDBPlayerConfig;
import nl.manthos.idbewijzen.config.IDBSettingsConfig;
import nl.manthos.idbewijzen.events.InteractEvent;
import nl.manthos.idbewijzen.panel.IDBPanel;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class IDBewijzen extends JavaPlugin {

    public IDBConfig idbConfig;
    public IDBPlayerConfig idbPlayerConfig;
    public IDBPanel idbPanel;
    public IDBSettingsConfig settingsConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.settingsConfig = new IDBSettingsConfig(this);
        this.idbConfig = new IDBConfig(this);
        this.idbPlayerConfig = new IDBPlayerConfig(this);

        registerListeners(
                idbPanel = new IDBPanel(this),
                new InteractEvent(this)
        );

        getCommand("idbewijs").setExecutor(new IDBCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerListeners(Listener... listeners) {
        Arrays.asList(listeners).forEach(listener ->
                Bukkit.getPluginManager().registerEvents(listener, this)
        );
    }
}
