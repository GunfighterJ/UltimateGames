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
import me.ampayne2.ultimategames.arenas.Arena;
import me.ampayne2.ultimategames.command.UGCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

/**
 * A command that opens an arena.
 */
public class Open extends UGCommand {
    private final UltimateGames ultimateGames;

    /**
     * Creates the Open command.
     *
     * @param ultimateGames The {@link me.ampayne2.ultimategames.UltimateGames} instance.
     */
    public Open(UltimateGames ultimateGames) {
        super(ultimateGames, "open", "Opens an arena", "/ug arena open <arena> <game>", new Permission("ultimategames.arena.open", PermissionDefault.OP), 2, false);
        this.ultimateGames = ultimateGames;
    }

    @Override
    public void execute(String command, CommandSender sender, String[] args) {
        String arenaName = args[0];
        String gameName = args[1];
        if (!ultimateGames.getGameManager().gameExists(gameName)) {
            ultimateGames.getMessenger().sendMessage(sender, "games.doesntexist");
            return;
        } else if (!ultimateGames.getArenaManager().arenaExists(arenaName, gameName)) {
            ultimateGames.getMessenger().sendMessage(sender, "arenas.doesntexist");
            return;
        }
        Arena arena = ultimateGames.getArenaManager().getArena(arenaName, gameName);
        ultimateGames.getArenaManager().openArena(arena);
        ultimateGames.getMessenger().sendMessage(sender, "arenas.setstatus", arenaName, gameName, arena.getStatus().name());
    }
}
