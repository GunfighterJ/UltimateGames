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
package me.ampayne2.ultimategames.events.players;

import me.ampayne2.ultimategames.arenas.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * An event called before a spectator joins.
 */
public class SpectatorPreJoinEvent extends PlayerEvent implements Cancellable {
    private boolean cancelled;

    /**
     * Creates a new SpectatorPreJoinEvent.
     *
     * @param player The spectator about to join.
     * @param arena  The arena the spectator is about to join.
     */
    public SpectatorPreJoinEvent(Player player, Arena arena) {
        super(player, arena);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
