package me.Tyloon1e.MyStarTool;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class StarEvents implements Listener {

	public List<String> list = new ArrayList<String>();

	static MyStarTool plugin;

	// you don't need this constructor for events but you'd do the same if e.g.
	// getting a List
	public StarEvents(MyStarTool instance) {
		plugin = instance;
	}

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TRIDENT))
			if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				Player player = (Player) event.getPlayer();

				if (event.getAction() == Action.RIGHT_CLICK_AIR) {
					if (!list.contains(player.getName()))
						list.add(player.getName());
					return;
				}

				if (event.getAction() == Action.LEFT_CLICK_AIR) {
					player.launchProjectile(Fireball.class);
				}
			}

		if (list.contains(event.getPlayer().getName())) {
			list.remove(event.getPlayer().getName());
		}
	}

	@EventHandler
	public void onLand(ProjectileHitEvent event) {
		if (event.getEntityType() == EntityType.TRIDENT) {
			if (event.getEntity().getShooter() instanceof Player) {
				Player player = (Player) event.getEntity().getShooter();

				if (list.contains(player.getName())) {
					Location location = event.getEntity().getLocation();

					location.setY(location.getY() + 1);

					for (int i = 1; i < 4; i++) {
						location.getWorld().spawnEntity(location, EntityType.DROWNED);
						location.setX(location.getX() + i);
					}
				}
			}
		}
	}
}
