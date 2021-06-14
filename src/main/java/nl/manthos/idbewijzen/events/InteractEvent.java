package nl.manthos.idbewijzen.events;

import nl.manthos.idbewijzen.IDBewijzen;
import nl.manthos.idbewijzen.config.IDBConfig;
import nl.manthos.idbewijzen.config.IDBSettingsConfig;
import nl.manthos.idbewijzen.panel.IDBPanel;
import nl.manthos.idbewijzen.util.Formatting;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InteractEvent implements Listener {

    private IDBewijzen main;
    private IDBSettingsConfig settingsConfig;
    private IDBConfig idbConfig;
    private IDBPanel idbPanel;

    public InteractEvent(IDBewijzen main) {
        this.main = main;
        this.idbConfig = main.idbConfig;
        this.idbPanel = main.idbPanel;
        this.settingsConfig = main.settingsConfig;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack id = player.getInventory().getItemInMainHand();
        if (id.getType().equals(Material.BOOK)) {
            if (id.getItemMeta().getDisplayName().equals(idStack().getItemMeta().getDisplayName())) {
                if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    if (idbConfig.exists(player)) {

                        idbPanel.idBewijsUI(player);
                    }
                }
            }
        }
    }

    private ItemStack idStack() {
        ItemStack is = new ItemStack(Material.BOOK);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Formatting.format(settingsConfig.getConfig().getString("idbewijs.name")));
        if (this.settingsConfig.getConfig().getBoolean("idbewijs.glow")) {
            im.addEnchant(Enchantment.LUCK, 1, false);
        }
        if (this.settingsConfig.getConfig().getBoolean("idbewijs.keep")) {
            im.addEnchant(Enchantment.BINDING_CURSE, 1, false);
        }
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        is.setItemMeta(im);
        return is;
    }
}
