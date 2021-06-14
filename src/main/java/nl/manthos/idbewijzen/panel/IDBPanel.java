package nl.manthos.idbewijzen.panel;

import nl.manthos.idbewijzen.IDBewijzen;
import nl.manthos.idbewijzen.config.IDBConfig;
import nl.manthos.idbewijzen.config.IDBPlayerConfig;
import nl.manthos.idbewijzen.config.IDBSettingsConfig;
import nl.manthos.idbewijzen.util.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
    private Inventory gui;

    public IDBPanel(IDBewijzen main) {
        this.main = main;
        this.idbConfig = main.idbConfig;
        this.idbPlayerConfig = main.idbPlayerConfig;
        this.settingsConfig = main.settingsConfig;
    }

    public void idBewijsUI(Player player) {

        //start
        this.gui = Bukkit.createInventory(null, 27, Formatting.format(settingsConfig.getConfig().getString("kleuren.secundair")
                + "ID Bewijs van " + settingsConfig.getConfig().getString("kleuren.primair") + player.getName()));

        //set items
        this.gui.setItem(10, playerSkull(player, Arrays.asList("", Formatting.format("&7Geboortedatum: "
                + idbPlayerConfig.getBirthday(player)), Formatting.format("&7Lengte: " + idbPlayerConfig.getLength(player) + "m"))));
        this.gui.setItem(13, city(Arrays.asList("", Formatting.format("&7" + idbConfig.getCity(player)))));
        this.gui.setItem(14, sex(Arrays.asList("", Formatting.format("&7" + idbConfig.getSex(player)))));
        this.gui.setItem(15, date(Arrays.asList("", Formatting.format("&7" + idbPlayerConfig.getDate(player)))));
        for (int i = 0; i < this.gui.getSize(); i++) {
            if (this.gui.getItem(i) == null || this.gui.getItem(i).getType() == Material.AIR) {
                this.gui.setItem(i, glass());
            }
        }
        //FINISH
        player.openInventory(this.gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (e.getRawSlot() == -999) return;
        if (e.getCurrentItem() == null) return;
        Player player = (Player) e.getWhoClicked();

        if (this.gui.getViewers().contains(e.getWhoClicked())) {
            e.setCancelled(true);
        }
    }

    private ItemStack playerSkull(Player player, List<String> lore) {
        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta im = (SkullMeta) is.getItemMeta();
        im.setDisplayName(Formatting.format(this.settingsConfig.getConfig().getString("itemstacks.player.color") + player.getName()));
        im.setLore(lore);
        im.setOwningPlayer(player);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        is.setItemMeta(im);
        return is;
    }

    private ItemStack city(List<String> lore) {
        ItemStack is = new ItemStack(Material.BRICK);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Formatting.format(this.settingsConfig.getConfig().getString("itemstacks.stad.name")));
        im.setLore(lore);
        if (this.settingsConfig.getConfig().getBoolean("itemstacks.stad.glow")) {
            im.addEnchant(Enchantment.LUCK, 1, false);
        }
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return is;
    }

    private ItemStack sex(List<String> lore) {
        ItemStack is = new ItemStack(Material.BANNER);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Formatting.format(this.settingsConfig.getConfig().getString("itemstacks.geslacht.name")));
        im.setLore(lore);
        if (this.settingsConfig.getConfig().getBoolean("itemstacks.geslacht.glow")) {
            im.addEnchant(Enchantment.LUCK, 1, false);
        }
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return is;
    }

    private ItemStack date(List<String> lore) {
        ItemStack is = new ItemStack(Material.SIGN);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Formatting.format(this.settingsConfig.getConfig().getString("itemstacks.afgiftedatum.name")));
        im.setLore(lore);
        if (this.settingsConfig.getConfig().getBoolean("itemstacks.afgiftedatum.glow")) {
            im.addEnchant(Enchantment.LUCK, 1, false);
        }
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return is;
    }

    private ItemStack glass() {
        ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(" ");
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        is.setItemMeta(im);
        return is;
    }
}
