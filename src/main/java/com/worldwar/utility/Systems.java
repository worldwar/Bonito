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

    public static void write(File target, long offset, byte[] block) throws IOException {
        try(RandomAccessFile file = new RandomAccessFile(target, "rw")) {
            file.seek(offset);
            file.write(block, 0, block.length);
        }
    }

    public static int read(byte[] actual, File source, long offset, int length) throws IOException {
        try(RandomAccessFile file = new RandomAccessFile(source, "r")) {
            file.seek(offset);
            return file.read(actual, 0, length);
        }
    }
}
