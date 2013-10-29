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
package me.ampayne2.ultimategames.countdowns;

import me.ampayne2.ultimategames.UltimateGames;
import me.ampayne2.ultimategames.arenas.Arena;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Countdown extends BukkitRunnable {
    protected final UltimateGames ultimateGames;
    protected final Arena arena;
    protected int ticksLeft;
    protected long period;
    private Integer taskId = null;

    /**
     * Creates a new Countdown.
     *
     * @param ultimateGames A reference to the UltimateGames instance.
     * @param arena         The arena of the countdown.
     * @param initialTicks  Initial ticks of the countdown.
     * @param period        Amount of ticks to wait between each run.
     */
    public Countdown(UltimateGames ultimateGames, Arena arena, int initialTicks, long period) {
        this.ultimateGames = ultimateGames;
        this.arena = arena;
        this.ticksLeft = initialTicks;
        this.period = period;
    }

    public boolean start() {
        if (taskId == null) {
            taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(ultimateGames, this, 0, period);
            return true;
        } else {
            return false;
        }
    }

    public void stop() {
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = null;
        }
    }

    public int getTicksLeft() {
        return ticksLeft;
    }
}
