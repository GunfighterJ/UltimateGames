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
package me.ampayne2.ultimategames.command.commands;

import me.ampayne2.ultimategames.command.interfaces.UGCommand;
import me.ampayne2.ultimategames.utils.ParticleEffect;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SpawnParticle implements UGCommand {
	@Override
	public void execute(CommandSender sender, String[] args) {
		ParticleEffect particleEffect = ParticleEffect.valueOf(args[0]);
		Player player = (Player) sender;
		//offset x offset y offset z speed amount
		particleEffect.play(player.getLocation().add(new Vector(1, 1, 1)), Float.valueOf(args[1]), Float.valueOf(args[2]), Float.valueOf(args[3]), Float.valueOf(args[4]), Integer.valueOf(args[5]));
	}
}
