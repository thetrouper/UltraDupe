package me.trouper.ultradupe.server.util;

import me.trouper.ultradupe.UltraDupe;

import java.io.File;
public class FileUtils {
    public static boolean folderExists(String folderName) {
        File folder = new File(UltraDupe.getInstance().getDataFolder(), folderName);
        return folder.exists() && folder.isDirectory();
    }
    public static void createFolder(String folderName) {
        File folder = new File(UltraDupe.getInstance().getDataFolder(), folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}
