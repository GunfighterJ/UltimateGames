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
package me.ampayne2.ultimategames.chests;

import me.ampayne2.ultimategames.UltimateGames;
import me.ampayne2.ultimategames.arenas.Arena;
import me.ampayne2.ultimategames.enums.ChestType;
import me.ampayne2.ultimategames.games.Game;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Manages the UG chests for arenas.<br>
 * UNFINISHED.
 */
@SuppressWarnings("unchecked")
public class UGChestManager {
    private final UltimateGames ultimateGames;
    private List<RandomChest> randomChests = new ArrayList<RandomChest>();
    private List<StaticChest> staticChests = new ArrayList<StaticChest>();

    /**
     * Creates a new Chest Manager.
     *
     * @param ultimateGames A reference to the UltimateGames instance.
     */
    public UGChestManager(UltimateGames ultimateGames) {
        this.ultimateGames = ultimateGames;
        loadUGChests();
    }

    /**
     * Gets the ChestType of a UG Chest.
     *
     * @param ugChest The UG Chest.
     * @return The ChestType.
     */
    public ChestType getChestType(UGChest ugChest) {
        return ChestType.getChestTypeFromClass(ugChest.getClass());
    }

    /**
     * Checks to see if a chest is an Ultimate Game chest.
     *
     * @param chest The chest to check.
     * @return If the chest is an Ultimate Game chest.
     */
    public boolean isUGChest(Chest chest) {
        return isRandomChest(chest) || isStaticChest(chest);
    }

    /**
     * Checks to see if a chest is a Random Chest.
     *
     * @param chest The chest to check.
     * @return If the chest is a Random Chest.
     */
    public boolean isRandomChest(Chest chest) {
        for (RandomChest randomChest : randomChests) {
            if (chest.equals(randomChest.getChest())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if a chest is a Click Input Chest.
     *
     * @param chest The chest to check.
     * @return If the chest is an Input Chest.
     */
    public boolean isStaticChest(Chest chest) {
        for (StaticChest staticChest : staticChests) {
            if (chest.equals(staticChest.getChest())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the Ultimate Game chest of a chest.
     *
     * @param chest The chest.
     * @return The Ultimate Game chest.
     */
    public UGChest getUGChest(Chest chest) {
        RandomChest randomChest = getRandomChest(chest);
        StaticChest staticChest = getStaticChest(chest);
        if (randomChest != null) {
            return randomChest;
        } else if (staticChest != null) {
            return staticChest;
        } else {
            return null;
        }
    }

    /**
     * Gets the Random Chest of a chest.
     *
     * @param chest The chest.
     * @return The Random Chest.
     */
    public RandomChest getRandomChest(Chest chest) {
        for (RandomChest schest : randomChests) {
            if (chest.equals(schest.getChest())) {
                return schest;
            }
        }
        return null;
    }

    /**
     * Gets the Click Input Chest of a chest.
     *
     * @param chest The chest.
     * @return The Click Input Chest.
     */
    public StaticChest getStaticChest(Chest chest) {
        for (StaticChest staticChest : staticChests) {
            if (chest.equals(staticChest.getChest())) {
                return staticChest;
            }
        }
        return null;
    }

    /**
     * Gets the Ultimate Game chests of an arena.
     *
     * @param arena The arena.
     * @return The Ultimate Game Chests.
     */
    public List<UGChest> getUGChestsOfArena(Arena arena) {
        List<RandomChest> rChests = getRandomChestsOfArena(arena);
        List<StaticChest> sChests = getStaticChestsOfArena(arena);
        List<UGChest> ugChests = new ArrayList<UGChest>();
        ugChests.addAll(rChests);
        ugChests.addAll(sChests);
        return ugChests;
    }

    /**
     * Gets the Ultimate Game chests of a game.
     *
     * @param game The game.
     * @return The Ultimate Game Chests.
     */
    public List<UGChest> getUGChestsOfGame(Game game) {
        List<RandomChest> rChests = getRandomChestsOfGame(game);
        List<StaticChest> sChests = getStaticChestsOfGame(game);
        List<UGChest> ugChests = new ArrayList<UGChest>();
        ugChests.addAll(rChests);
        ugChests.addAll(sChests);
        return ugChests;
    }

    /**
     * Gets the Random Chests of an arena.
     *
     * @param arena The arena.
     * @return The Random Chests.
     */
    public List<RandomChest> getRandomChestsOfArena(Arena arena) {
        List<RandomChest> arenaChests = new ArrayList<RandomChest>();
        for (RandomChest randomChest : randomChests) {
            if (arena.equals(randomChest.getArena())) {
                arenaChests.add(randomChest);
            }
        }
        return arenaChests;
    }

    /**
     * Gets the Random Chests of a game.
     *
     * @param game The game.
     * @return The Random Chests.
     */
    public List<RandomChest> getRandomChestsOfGame(Game game) {
        List<RandomChest> gameChests = new ArrayList<RandomChest>();
        for (RandomChest randomChest : randomChests) {
            if (game.equals(randomChest.getArena().getGame())) {
                gameChests.add(randomChest);
            }
        }
        return gameChests;
    }

    /**
     * Gets the Click Input Chests of an arena.
     *
     * @param arena The arena.
     * @return The Input Chests.
     */
    public List<StaticChest> getStaticChestsOfArena(Arena arena) {
        List<StaticChest> arenaChests = new ArrayList<StaticChest>();
        for (StaticChest staticChest : staticChests) {
            if (arena.equals(staticChest.getArena())) {
                arenaChests.add(staticChest);
            }
        }
        return arenaChests;
    }

    /**
     * Gets the Click Input Chests of a game.
     *
     * @param game The game.
     * @return The Input Chests.
     */
    public List<StaticChest> getStaticChestsOfGame(Game game) {
        List<StaticChest> gameChests = new ArrayList<StaticChest>();
        for (StaticChest staticChest : staticChests) {
            if (game.equals(staticChest.getArena().getGame())) {
                gameChests.add(staticChest);
            }
        }
        return gameChests;
    }

    /**
     * Creates a UG Chest and adds it to the manager.
     *
     * @param label     Label of the chest.
     * @param chest     The physical chest.
     * @param arena     Arena the chest is in.
     * @param chestType Type of the chest.
     * @return The UG Chest created.
     */
    public UGChest createUGChest(String label, Chest chest, Arena arena, ChestType chestType) {
        FileConfiguration ugChestConfig = ultimateGames.getConfigManager().getUGChestConfig().getConfig();
        String chestPath = chestType.toString() + "." + arena.getGame().getName() + "." + arena.getName();
        List<String> chestInfo = new ArrayList<String>();
        chestInfo.add(0, chest.getWorld().getName());
        chestInfo.add(1, Integer.toString(chest.getX()));
        chestInfo.add(2, Integer.toString(chest.getY()));
        chestInfo.add(3, Integer.toString(chest.getZ()));
        if (chestType.hasLabel()) {
            chestInfo.add(4, label);
        }
        List<List<String>> ugChests;
        if (ugChestConfig.contains(chestPath)) {
            ugChests = (List<List<String>>) ugChestConfig.getList(chestPath);
            ugChests.add(chestInfo);
        } else {
            ugChests = new ArrayList<List<String>>();
            ugChests.add(chestInfo);
            ugChestConfig.createSection(chestPath);
        }
        ugChestConfig.set(chestPath, ugChests);
        ultimateGames.getConfigManager().getUGChestConfig().saveConfig();
        switch (chestType) {
            case RANDOM:
                RandomChest randomChest = new RandomChest(ultimateGames, chest, arena);
                randomChests.add(randomChest);
                return randomChest;
            case STATIC:
                StaticChest staticChest = new StaticChest(ultimateGames, label, chest, arena);
                staticChests.add(staticChest);
                return staticChest;
        }
        return null;
    }

    /**
     * Removes a UG Chest from the manager and config.
     *
     * @param chest The chest of the UG Chest to remove.
     */
    public void removeUGChest(Chest chest) {
        UGChest ugChest = getUGChest(chest);
        if (ugChest != null) {
            FileConfiguration ugChestConfig = ultimateGames.getConfigManager().getUGChestConfig().getConfig();
            String world = chest.getWorld().getName();
            Integer x = chest.getX();
            Integer y = chest.getY();
            Integer z = chest.getZ();
            ChestType chestType = getChestType(ugChest);
            String gamePath = chestType.toString() + "." + ugChest.getArena().getGame().getName();
            String arenaPath = gamePath + "." + ugChest.getArena().getName();
            if (ugChestConfig.contains(arenaPath)) {
                List<List<String>> ugChests = (List<List<String>>) ugChestConfig.getList(arenaPath);
                List<List<String>> newUGChests = new ArrayList<List<String>>(ugChests);
                for (List<String> chestInfo : ugChests) {
                    if (world.equals(chestInfo.get(0)) && x.equals(Integer.parseInt(chestInfo.get(1))) && y.equals(Integer.parseInt(chestInfo.get(2))) && z.equals(Integer.parseInt(chestInfo.get(3)))) {
                        newUGChests.remove(chestInfo);
                    }
                }
                ugChestConfig.set(arenaPath, newUGChests);
                if (ugChestConfig.getList(arenaPath).isEmpty()) {
                    ugChestConfig.set(arenaPath, null);
                }
                if (ugChestConfig.getConfigurationSection(gamePath).getKeys(true).isEmpty()) {
                    ugChestConfig.set(gamePath, null);
                }
                ultimateGames.getConfigManager().getUGChestConfig().saveConfig();
            }
            switch (chestType) {
                case RANDOM:
                    randomChests.remove(ugChest);
                    break;
                case STATIC:
                    staticChests.remove(ugChest);
                    break;
            }
        }
    }

    /**
     * Loads all of the Ultimate Game chests.
     */
    public void loadUGChests() {
        randomChests.clear();
        staticChests.clear();
        FileConfiguration ugChestConfig = ultimateGames.getConfigManager().getUGChestConfig().getConfig();
        for (ChestType chestType : EnumSet.allOf(ChestType.class)) {
            String chestTypeName = chestType.toString();
            if (ugChestConfig.getConfigurationSection(chestTypeName) != null) {
                for (String gameKey : ugChestConfig.getConfigurationSection(chestTypeName).getKeys(false)) {
                    if (ultimateGames.getGameManager().gameExists(gameKey)) {
                        String gamePath = chestTypeName + "." + gameKey;
                        for (String arenaKey : ugChestConfig.getConfigurationSection(gamePath).getKeys(false)) {
                            if (ultimateGames.getArenaManager().arenaExists(arenaKey, gameKey)) {
                                String arenaPath = gamePath + "." + arenaKey;
                                List<List<String>> ugChests = (List<List<String>>) ugChestConfig.getList(arenaPath);
                                for (List<String> chestInfo : ugChests) {
                                    World world = Bukkit.getWorld(chestInfo.get(0));
                                    Integer x = Integer.parseInt(chestInfo.get(1));
                                    Integer y = Integer.parseInt(chestInfo.get(2));
                                    Integer z = Integer.parseInt(chestInfo.get(3));
                                    Block locBlock = new Location(world, x, y, z).getBlock();
                                    if (locBlock.getType() == Material.WALL_SIGN || locBlock.getType() == Material.SIGN_POST) {
                                        Arena arena = ultimateGames.getArenaManager().getArena(arenaKey, gameKey);
                                        switch (chestType) {
                                            case RANDOM:
                                                randomChests.add(new RandomChest(ultimateGames, (Chest) locBlock.getState(), arena));
                                                break;
                                            case STATIC:
                                                staticChests.add(new StaticChest(ultimateGames, chestInfo.get(4), (Chest) locBlock.getState(), arena));
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
