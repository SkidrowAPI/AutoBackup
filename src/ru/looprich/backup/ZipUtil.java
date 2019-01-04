package ru.looprich.backup;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    private AutoBackup plugin;

    public ZipUtil(AutoBackup intance) {
        plugin = intance;
    }

    public void saveCard(String name, File file) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(name));
        doZip(file, out);

        out.close();
    }

    private void doZip(File dir, ZipOutputStream out) throws IOException {
        for (File f : dir.listFiles()) {
            if (f.isDirectory())
                doZip(f, out);
            else {
                out.setLevel(plugin.getConfig().getInt("level_compress"));
                out.putNextEntry(new ZipEntry(f.getPath()));
                write(new FileInputStream(f), out);
            }
        }
    }

    private void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        in.close();
    }
}

