package me.initsync.mongodb.test.api;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class PlayerKillRewardEvent extends Event implements Cancellable {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final Player player;
	private final Entity killedEntity;
	private final int levelsReward;
	
	private boolean cancelled;
	
	public PlayerKillRewardEvent(Player player, Entity killedEntity, int levelsReward) {
		this.player = Objects.requireNonNull(player, "The player is null.");
		this.killedEntity = Objects.requireNonNull(killedEntity, "The killed entity is null.");
		this.levelsReward = levelsReward;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Entity getKilledEntity() {
		return killedEntity;
	}
	
	public int getLevelsReward() {
		return levelsReward;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
}
