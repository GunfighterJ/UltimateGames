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
package me.ampayne2.ultimategames.command.commands.arenas;

import me.ampayne2.ultimategames.UltimateGames;
import me.ampayne2.ultimategames.command.UGCommand;
import me.ampayne2.ultimategames.players.QueueManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

/**
 * A command that makes the sender leave an arena.
 */
public class Leave extends UGCommand {
    private final UltimateGames ultimateGames;

    /**
     * Creates the Leave command.
     *
     * @param ultimateGames The {@link me.ampayne2.ultimategames.UltimateGames} instance.
     */
    public Leave(UltimateGames ultimateGames) {
        super(ultimateGames, "leave", "Leaves an arena", "/ug leave", new Permission("ultimategames.leave", PermissionDefault.TRUE), true);
        this.ultimateGames = ultimateGames;
    }

    @Override
    public void execute(String command, CommandSender sender, String[] args) {
        Player player = (Player) sender;
        String playerName = player.getName();
        QueueManager queue = ultimateGames.getQueueManager();
        if (ultimateGames.getPlayerManager().isPlayerInArena(playerName)) {
            ultimateGames.getPlayerManager().removePlayerFromArena((Player) sender, true);
        } else if (ultimateGames.getPlayerManager().isPlayerSpectatingArena(playerName)) {
            ultimateGames.getPlayerManager().removeSpectatorFromArena((Player) sender);
        } else if (queue.isPlayerInQueue(playerName)) {
            queue.removePlayerFromQueues(player);
        } else {
            ultimateGames.getMessenger().sendMessage(sender, "ultimategames.cantleave");
        }
    }
}
