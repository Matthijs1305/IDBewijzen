package nl.manthos.idbewijzen.events;

import nl.manthos.idbewijzen.IDBewijzen;
import nl.manthos.idbewijzen.config.IDBConfig;
import nl.manthos.idbewijzen.items.ItemStacks;
import nl.manthos.idbewijzen.panel.IDBPanel;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEvent implements Listener {

    private IDBewijzen main;
    private IDBConfig idbConfig;
    private IDBPanel idbPanel;

    public InteractEvent(IDBewijzen main) {
        this.main = main;
        this.idbConfig = main.idbConfig;
        this.idbPanel = main.idbPanel;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack id = player.getInventory().getItemInMainHand();
        if (id.getType().equals(Material.BOOK)) {
            if (id.getItemMeta().getDisplayName().equals(ItemStacks.idStack().getItemMeta().getDisplayName())) {
                if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    if (idbConfig.exists(player)) {

                        idbPanel.idBewijsUI(player);
                    }
                }
            }
        }
    }
}
