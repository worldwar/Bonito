package com.worldwar.utility;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SystemsTest {

    private String dir = "tmp";
    private String fn = "tmp/test";
    private long size = 1024 * 8L;
    private File file;

    @Before
    public void before() throws IOException {
        ensureEmpty(dir);
        file = Systems.file(fn, size);
    }

    @Test
    public void shouldCreateFileWithTheSizeWhenItNotExist() throws IOException {
        assertThat(file.length(), is(size));
    }

    private File ensureEmpty(String dir) {
        File file = new File(dir);
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    f.delete();
                }
            }
            file.delete();
        }
        file.mkdir();
        return file;
    }

    @Test
    public void shouldWriteCorrectContentToTargetWhenItIsEmpty() throws IOException {
        int offset = 5;
        byte[] bytes = "hello, World!".getBytes();
        int length = bytes.length;
        byte[] actual = new byte[length];
        byte[] empty = new byte[length];

        Systems.read(actual, file, offset, length);
        assertThat(Arrays.equals(actual, empty), is(true));

        Systems.write(file, offset, bytes);
        Systems.read(actual, file, offset, length);
        assertThat(Arrays.equals(actual, bytes), is(true));
    }

    @Test
    public void shouldWriteCorrectContentToTargetWhenItHasContent() throws IOException {
        byte[] hello = "hello".getBytes();
        byte[] world = "world".getBytes();

        int length = hello.length;
        byte[] actual = new byte[length];

        Systems.write(file, 0, hello);
        Systems.write(file, hello.length, world);
        Systems.read(actual, file, 0, length);
        assertThat(Arrays.equals(actual, hello), is(true));
        Systems.read(actual, file, length, length);
        assertThat(Arrays.equals(actual, world), is(true));
    }
}