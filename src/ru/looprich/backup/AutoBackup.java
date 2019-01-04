package ru.looprich.backup;

import org.bukkit.plugin.java.JavaPlugin;

public class AutoBackup extends JavaPlugin {
    AutoBackup plugin = this;



    @Override
    public void onEnable() {
        plugin.getDataFolder().mkdirs();
        Save save = new Save(plugin);
        save.shed();
    }

    @Override
    public void onDisable() {

    }

}

