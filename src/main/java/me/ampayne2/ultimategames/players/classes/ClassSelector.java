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
package me.ampayne2.ultimategames.players.classes;

import me.ampayne2.ultimategames.UltimateGames;
import me.ampayne2.ultimategames.arenas.Arena;
import me.ampayne2.ultimategames.games.items.GameItem;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * A class selector game item.
 */
public class ClassSelector extends GameItem {
    private final UltimateGames ultimateGames;
    private static final ItemStack CLASS_SELECTOR;

    /**
     * Creates a new class selector item.
     *
     * @param ultimateGames The {@link me.ampayne2.ultimategames.UltimateGames} instance.
     */
    public ClassSelector(UltimateGames ultimateGames) {
        super(CLASS_SELECTOR, false);
        this.ultimateGames = ultimateGames;
    }

    @Override
    public boolean click(Arena arena, PlayerInteractEvent event) {
        ultimateGames.getGameClassManager().getClassSelector(arena.getGame(), event.getPlayer()).open(event.getPlayer());
        return true;
    }

    static {
        CLASS_SELECTOR = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = CLASS_SELECTOR.getItemMeta();
        meta.setDisplayName("Class selector");
        CLASS_SELECTOR.setItemMeta(meta);
    }
}
