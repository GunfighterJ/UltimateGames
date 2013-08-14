package me.ampayne2.ultimategames.arenas;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Attachable;
import org.bukkit.material.MaterialData;
import me.ampayne2.ultimategames.UltimateGames;
import me.ampayne2.ultimategames.database.tables.BlockChangeTable;

public class LogManager {

    private UltimateGames ultimateGames;
    private Integer logTask;
    private List<BlockChange> pendingChanges = new ArrayList<BlockChange>();
    private List<BlockChange> savingChanges = new ArrayList<BlockChange>();

    public LogManager(UltimateGames ultimateGames) {
        this.ultimateGames = ultimateGames;
        startLogSaving();
    }

    public void logBlockChange(Arena arena, Material material, byte data, Location location) {
        pendingChanges.add(new BlockChange(arena, material, data, location));
    }

    public void startLogSaving() {
        logTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(ultimateGames, new Runnable() {
            @Override
            public void run() {
                if (!pendingChanges.isEmpty()) {
                    savingChanges.addAll(pendingChanges);
                    pendingChanges.clear();
                    for (BlockChange blockChange : savingChanges) {
                        Arena arena = blockChange.arena;
                        Material material = blockChange.material;
                        byte data = blockChange.data;
                        Location location = blockChange.location;
                        if (!((material == Material.WOODEN_DOOR || material == Material.IRON_DOOR_BLOCK) && (data & 0x8) == 0x8)) {
                            String gameName = arena.getGame().getGameDescription().getName();
                            String arenaName = arena.getName();
                            double x = location.getX();
                            double y = location.getY();
                            double z = location.getZ();
                            if (ultimateGames.getDatabaseManager().getDatabase().select(BlockChangeTable.class).where().equal("gameName", gameName).and().equal("arenaName", arenaName).and().equal("x", x)
                                    .and().equal("y", y).and().equal("z", z).execute().findOne() == null) {
                                BlockChangeTable table = new BlockChangeTable();
                                table.gameName = gameName;
                                table.arenaName = arenaName;
                                table.x = x;
                                table.y = y;
                                table.z = z;
                                table.material = material.name();
                                table.data = data;
                                table.needsBlockSupport = material == Material.SAND || material == Material.GRAVEL || material == Material.ACTIVATOR_RAIL || material == Material.ANVIL
                                        || material == Material.CARPET || material == Material.CACTUS || material == Material.CROPS || material == Material.DEAD_BUSH || material == Material.DRAGON_EGG
                                        || material == Material.FIRE || material == Material.FLOWER_POT || material == Material.IRON_DOOR_BLOCK || material == Material.LEVER
                                        || material == Material.LONG_GRASS || material == Material.MELON_STEM || material == Material.NETHER_WARTS || material == Material.POWERED_RAIL
                                        || material == Material.RAILS || material == Material.RED_MUSHROOM || material == Material.RED_ROSE || material == Material.REDSTONE_COMPARATOR_OFF
                                        || material == Material.REDSTONE_COMPARATOR_ON || material == Material.REDSTONE_TORCH_OFF || material == Material.REDSTONE_TORCH_ON
                                        || material == Material.REDSTONE_WIRE || material == Material.SAPLING || material == Material.STONE_BUTTON || material == Material.SUGAR_CANE_BLOCK
                                        || material == Material.TORCH || material == Material.TRAP_DOOR || material == Material.TRIPWIRE_HOOK || material == Material.WATER_LILY
                                        || material == Material.WOOD_BUTTON || material == Material.WOODEN_DOOR;
                                ultimateGames.getDatabaseManager().getDatabase().save(table);
                                System.out.println("Saved Block");
                            }
                        }
                    }
                    savingChanges.clear();
                }
            }
        }, 0L, 5L);
    }

    public void stopLogSaving() {
        Bukkit.getScheduler().cancelTask(logTask);
    }

    public Boolean rollbackArena(Arena arena) {
        List<BlockChangeTable> changes = ultimateGames.getDatabaseManager().getDatabase().select(BlockChangeTable.class).where().equal("gameName", arena.getGame().getGameDescription().getName())
                .and().equal("arenaName", arena.getName()).execute().find();
        while (!changes.isEmpty()) {
            for (BlockChangeTable entry : new ArrayList<BlockChangeTable>(changes)) {
                Location location = new Location(arena.getWorld(), entry.x, entry.y, entry.z);
                Material material = Material.valueOf(entry.material);
                Boolean rollback = true;
                if (location.getBlock().getType() == material && location.getBlock().getData() == entry.data) {
                    changes.remove(entry);
                    rollback = false;
                } else if (entry.needsBlockSupport) {
                    location.getBlock().setType(material);
                    location.getBlock().setData(entry.data);
                    BlockFace blockFace = BlockFace.DOWN;
                    MaterialData data = location.getBlock().getState().getData();
                    if (data instanceof Attachable) {
                        blockFace = ((Attachable) data).getAttachedFace();
                    }
                    rollback = location.getBlock().getRelative(blockFace).getType().isSolid();
                }
                if (rollback) {
                    if (material == Material.WOODEN_DOOR || material == Material.IRON_DOOR_BLOCK) {
                        location.getBlock().setTypeIdAndData(material.getId(), entry.data, false);
                        location.getBlock().getRelative(BlockFace.UP).setTypeIdAndData(material.getId(), (byte) (entry.data | 0x8), false);
                    } else {
                        location.getBlock().setType(material);
                        location.getBlock().setData(entry.data);
                    }
                    changes.remove(entry);
                }
            }
        }
        for (BlockChangeTable entry : ultimateGames.getDatabaseManager().getDatabase().select(BlockChangeTable.class).where().equal("gameName", arena.getGame().getGameDescription().getName()).and()
                .equal("arenaName", arena.getName()).execute().find()) {
            ultimateGames.getDatabaseManager().getDatabase().remove(
                    ultimateGames.getDatabaseManager().getDatabase().select(BlockChangeTable.class).where().equal("gameName", arena.getGame().getGameDescription().getName()).and().equal("arenaName",
                            arena.getName()).and().equal("x", entry.x).and().equal("y", entry.y).and().equal("z", entry.z).execute().findOne());
        }
        System.out.println("Rolled back");
        return true;
    }
}