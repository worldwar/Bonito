package com.worldwar.utility;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Systems {
    public static File file(String path, long size) throws IOException {
        File file = new File(path);
        file.createNewFile();
        try(RandomAccessFile w = new RandomAccessFile(file, "rw")) {
            w.setLength(size);
        }
        return file;
    }
}
