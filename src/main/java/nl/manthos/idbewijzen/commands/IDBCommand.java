package nl.manthos.idbewijzen.commands;

import nl.manthos.idbewijzen.IDBewijzen;
import nl.manthos.idbewijzen.config.IDBConfig;
import nl.manthos.idbewijzen.config.IDBPlayerConfig;
import nl.manthos.idbewijzen.items.ItemStacks;
import nl.manthos.idbewijzen.util.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class IDBCommand implements CommandExecutor {

    private IDBewijzen main;
    private IDBConfig idbConfig;
    private IDBPlayerConfig playerConfig;

    public IDBCommand(IDBewijzen main) {
        this.main = main;
        this.idbConfig = main.idbConfig;
        this.playerConfig = main.idbPlayerConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("idb.id")) {

                player.sendMessage(Formatting.format("&3Running &b&lIDBewijzen &7(" + main.getDescription().getVersion() + ")"));
                player.sendMessage(Formatting.format("&3Download Link: &b"));
                player.sendMessage(Formatting.format("&3Developer: &bMatthijs - Manthos"));
            } else {
                if (args.length == 0) {
                    player.sendMessage(Formatting.format("&3&m----------&r &b&lIDBewijzen &7" + main.getDescription().getVersion() + " &3&m----------"));
                    player.sendMessage("");
                    player.sendMessage(Formatting.format("&b/" + label + "&3 check &b<speler> &f- &bCheck of een speler een ID in bezit heeft."));
                    player.sendMessage(Formatting.format("&b/" + label + "&3 list &b<pagina> &f- &bBekijk alle geregistreerde ID kaarten."));
                    player.sendMessage(Formatting.format("&b/" + label + "&3 maak &b<speler> <stad> <geslacht> &f- &bMaak een ID voor een speler."));
                    player.sendMessage(Formatting.format("&b/" + label + "&3 verwijder &b<speler> &f- &bVerwijder een ID van een speler."));
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("check")) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        if (this.idbConfig.exists(target)) {
                            player.sendMessage(Formatting.format("&bDe speler met de naam &3" + target.getName() + "&b heeft een ID."));
                        } else {
                            player.sendMessage(Formatting.format("&bDe speler met de naam &3" + target.getName() + "&b staat niet in het archief."));
                        }
                    } else if (args[0].equalsIgnoreCase("verwijder")) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        if (this.idbConfig.exists(target)) {

                            this.idbConfig.removeID(target);
                            player.sendMessage(Formatting.format("&bJe hebt het ID bewijs van &3" + target.getName() + "&b verwijderd!"));
                        } else {
                            player.sendMessage(Formatting.format("&bDeze speler heeft &3geen &bID bewijs!"));
                        }
                    }
                } else if (args.length == 4) {
                    if (args[0].equalsIgnoreCase("maak")) {
                        if (player.hasPermission("idb.create")) {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                            if (!idbConfig.exists(target)) {
                                String city = args[2];
                                String sex = args[3];
                                this.idbConfig.addID(target, city, sex);
                                this.playerConfig.addPlayer(target);
                                player.sendMessage(Formatting.format("&bJe hebt een ID aangemaakt voor &3" + target.getName() + "&b met de stad &3" + city + "&b en het geslacht &3" + sex + "&b."));
                                player.getInventory().addItem(ItemStacks.idStack());
                            } else {
                                player.sendMessage(Formatting.format("&bDeze speler heeft al een &3ID&b."));
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("[IDBewijzen] Je kan deze command alleen gebruiken als speler!");
        }
        return true;
    }
}
