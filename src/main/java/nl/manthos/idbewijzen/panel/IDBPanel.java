package nl.manthos.idbewijzen.panel;

import nl.manthos.idbewijzen.IDBewijzen;
import nl.manthos.idbewijzen.config.IDBConfig;
import nl.manthos.idbewijzen.config.IDBPlayerConfig;
import nl.manthos.idbewijzen.config.IDBSettingsConfig;
import nl.manthos.idbewijzen.util.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class IDBPanel implements Listener {

    private IDBewijzen main;
    private IDBConfig idbConfig;
    private IDBPlayerConfig idbPlayerConfig;
    private IDBSettingsConfig settingsConfig;

    public IDBPanel(IDBewijzen main) {
        this.main = main;
        this.idbConfig = main.idbConfig;
        this.idbPlayerConfig = main.idbPlayerConfig;
        this.settingsConfig = main.settingsConfig;
    }

    public void idBewijsUI(Player player) {

        //start
        Inventory inv = Bukkit.createInventory(null, 27, Formatting.format("&3ID Bewijs van &b" + player.getName()));

        //set items
        inv.setItem(10, playerSkull(player, Arrays.asList("", Formatting.format("&7Geboortedatum: " + idbPlayerConfig.getBirthday(player)), Formatting.format("&7Lengte: " + idbPlayerConfig.getLength(player)))));
        inv.setItem(13, city(Arrays.asList("", Formatting.format("&7" + idbConfig.getCity(player)))));
        inv.setItem(14, sex(Arrays.asList("", Formatting.format("&7" + idbConfig.getSex(player)))));
        inv.setItem(15, date(Arrays.asList("", Formatting.format("&7" + idbPlayerConfig.getDate(player)))));

        //FINISH
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        if (ChatColor.translateAlternateColorCodes('&', e.getClickedInventory().getTitle()).equalsIgnoreCase(ChatColor.DARK_AQUA + "ID Bewijs van " + ChatColor.AQUA + player.getName())) {
            Inventory inv = e.getClickedInventory();
            if (e.getCurrentItem() == null) return;
            if (e.getRawSlot() == -999) return;
            e.setCancelled(true);

            ItemStack current = e.getCurrentItem();
            String currentName = current.getItemMeta().getDisplayName();
        }
    }

    private ItemStack playerSkull(Player player, List<String> lore) {
        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta im = (SkullMeta) is.getItemMeta();
        im.setDisplayName(Formatting.format(this.settingsConfig.getConfig().getString("kleuren.primair") + player.getName()));
        im.setLore(lore);
        im.setOwningPlayer(player);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        is.setItemMeta(im);
        return is;
    }

    private ItemStack city(List<String> lore) {
        ItemStack is = new ItemStack(Material.BRICK);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Formatting.format(this.settingsConfig.getConfig().getString("kleuren.primair") + "Stad"));
        im.setLore(lore);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return is;
    }

    private ItemStack sex(List<String> lore) {
        ItemStack is = new ItemStack(Material.BANNER);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Formatting.format(this.settingsConfig.getConfig().getString("kleuren.primair") + "Geslacht"));
        im.setLore(lore);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return is;
    }

    private ItemStack date(List<String> lore) {
        ItemStack is = new ItemStack(Material.SIGN);
        ItemMeta im = is.getItemMeta();
        Formatting.format(Formatting.format(this.settingsConfig.getConfig().getString("kleuren.primair") + "Afgiftedatum"));
        im.setLore(lore);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return is;
    }
}
