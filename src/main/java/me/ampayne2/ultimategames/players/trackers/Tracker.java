/*
 * This file is part of UltimateGames.
 *
 * Copyright (c) 2013-2013, UltimateGames <http://github.com/ampayne2/>
 *
 * UltimateGames is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * UltimateGames is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with UltimateGames.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.ampayne2.ultimategames.players.trackers;

import me.ampayne2.ultimategames.UltimateGames;
import me.ampayne2.ultimategames.arenas.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * The base class for a tracker.
 */
public abstract class Tracker implements Runnable, Listener {
    private final UltimateGames ultimateGames;
    private final Player player;
    private final Arena arena;
    private final int taskId;

    /**
     * Creates a new Tracker.
     *
     * @param ultimateGames The {@link me.ampayne2.ultimategames.UltimateGames} instance.
     * @param player        The tracking player.
     * @param arena         The tracking player's arena.
     */
    public Tracker(UltimateGames ultimateGames, Player player, Arena arena) {
        this.ultimateGames = ultimateGames;
        this.player = player;
        this.arena = arena;
        taskId = ultimateGames.getServer().getScheduler().scheduleSyncRepeatingTask(ultimateGames, this, 0, 5);
        ultimateGames.getServer().getPluginManager().registerEvents(this, ultimateGames);
    }

    /**
     * Gets the tracking player.
     *
     * @return The tracking player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the tracking player's arena.
     *
     * @return The tracking player's arena.
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Stops the tracker from tracking.
     */
    public void stop() {
        ultimateGames.getServer().getScheduler().cancelTask(taskId);
    }

    /**
     * Updates the tracker.
     */
    public abstract void run();

    /**
     * Stops the tracker when the tracking player disconnects.
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer().equals(player)) {
            stop();
        }
    }
}
