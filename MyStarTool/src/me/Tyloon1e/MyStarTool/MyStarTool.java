package me.Tyloon1e.MyStarTool;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class MyStarTool extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new StarEvents(this), this);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
		if (label.equalsIgnoreCase("mystartool") || label.equalsIgnoreCase("startool")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("No!");
				return true;
			}

			Player player = (Player) sender;

			if (player.getInventory().firstEmpty() == -1) /* checks if the inventory is full */ {
				Location location = player.getLocation();
				World world = player.getWorld();

				world.dropItemNaturally(location, getItem());
				player.sendMessage(ChatColor.GREEN + "Tyrone airdropped a new tool at your location since your bag is full!");
				return true;
			}

			player.getInventory().addItem(getItem());
			player.sendMessage(ChatColor.GREEN + "Tyrone airdropped you a new tool.");
			return true;
		}

		return false;
	}

	public ItemStack getItem() {
		ItemStack item = new ItemStack(Material.TRIDENT);
		ItemMeta meta = item.getItemMeta(); // you add the lore, enchants etc. to the meta itself

		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Star Tool");

		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7(Left Click) &a&oShoot explosives"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7(Right Click) &a&oSpawn minions"));

		meta.setLore(lore); // setLore accepts an array
		meta.addEnchant(Enchantment.PROTECTION_FALL, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setUnbreakable(true);

		item.setItemMeta(meta);

		return item;
	}
}
