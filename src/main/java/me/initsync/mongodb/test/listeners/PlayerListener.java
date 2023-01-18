package me.initsync.mongodb.test.listeners;

import me.initsync.mongodb.test.api.PlayerKillRewardEvent;
import me.initsync.mongodb.test.api.model.UserModel;
import me.initsync.mongodb.test.api.model.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;
import java.util.UUID;

public class PlayerListener implements Listener {
	private final ProfileManager profileManager;
	
	public PlayerListener(ProfileManager profileManager) {
		this.profileManager = Objects.requireNonNull(Objects.requireNonNull(profileManager, "The ProfileManager object is null."));
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		String name = event.getPlayer().getName();
		UUID uuid = event.getPlayer().getUniqueId();
		
		profileManager.createProfile(uuid, name);
		profileManager.getProfile(uuid).getUser().loadData();
		
		Bukkit.getLogger().info("Data of player '" + name + "' loaded!");
	}
	
	@EventHandler
	public void onKillZombie(EntityDeathEvent event) {
		if ((!(event.getEntity() instanceof Zombie))) return;
		
		Player player = event.getEntity().getKiller();
		
		PlayerKillRewardEvent killRewardEvent = new PlayerKillRewardEvent(player, event.getEntity(), 1);
		Bukkit.getPluginManager().callEvent(killRewardEvent);
		if (killRewardEvent.isCancelled()) return;
		
		UserModel user = profileManager.getProfile(player.getUniqueId()).getUser();
		user.incrementKills(1);
		user.incrementLevel(1);
		
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 1);
		player.sendMessage(ChatColor.GREEN + "You has killed to a Zombie and received " + ChatColor.YELLOW + "1+ level " + ChatColor.GREEN + "as reward");
		player.setLevel(user.getLevel());
		
		user.saveData();
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		profileManager.getProfile(event.getEntity().getUniqueId()).getUser().decrementLevel(1);
	}
}
