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
package me.ampayne2.ultimategames.items;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameItem {
    protected final ItemStack item;
    protected final Set<ItemAction> actions;
    protected boolean consumable;

    /**
     * Creates a new GameItem.
     *
     * @param item       The ItemStack of the GameItem.
     * @param actions    The ItemActions of the GameItem.
     * @param consumable If the GameItem can only be used once.
     */
    public GameItem(ItemStack item, boolean consumable, ItemAction... actions) {
        this.item = item;
        this.actions = new HashSet<ItemAction>(Arrays.asList(actions));
        this.consumable = consumable;
    }

    /**
     * Gets the GameItem's ItemStack.
     *
     * @return The ItemStack.
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * Checks to see if a GameItem can only be used once.
     *
     * @return True if the GameItem can only be used once, else false.
     */
    public boolean isConsumable() {
        return consumable;
    }

    /**
     * Gets the GameItem's ItemAction.
     *
     * @return The ItemAction.
     */
    public Set<ItemAction> getActions() {
        return actions;
    }

    /**
     * Handles clicking the GameItem.
     *
     * @param event The PlayerInteractEvent.
     */
    public boolean click(PlayerInteractEvent event) {
        for (ItemAction action : actions) {
            if (event.getAction() == action.getAction()) {
                return action.perform(event);
            }
        }
        return false;
    }
}
