package ru.looprich.backup;

import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;

public class Save {
    private AutoBackup plugin;
    private int limit, i = 0;

    public Save(AutoBackup intance) {
        plugin = intance;
        limit = plugin.getConfig().getInt("file_limit");
    }

    void shed() {
        long time = 20L * 60 * plugin.getConfig().getInt("time");
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.scheduleAsyncRepeatingTask(plugin, () -> {
            File folder = plugin.getServer().getWorld(plugin.getServer().getWorlds().get(0).getUID()).getWorldFolder();
            if (i == limit) i = 0;
            this.i++;
            ZipUtil zip = new ZipUtil(plugin);
            try {
                String name = plugin.getDataFolder().getAbsolutePath() + File.separator + "AutoBackup" + this.i + ".zip";
                checkBackup(this.i);
                zip.saveCard(name, folder);
                plugin.getLogger().info("Бекап успешно создан - " + name);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, time, time);
    }

    void checkBackup(int i) {
        File myFolder = new File(plugin.getDataFolder().getAbsolutePath());
        File[] files = myFolder.listFiles();
        for (File f : files) {
            if (f.getName().equalsIgnoreCase("AutoBackup" + i)) {
                f.delete();
                plugin.getLogger().info("Бекап№" + i + " успешно удален!");
            }
        }
    }

}

