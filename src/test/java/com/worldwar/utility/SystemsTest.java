package com.worldwar.utility;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SystemsTest {

    @Test
    public void shouldCreateFileWithTheSizeWhenItNotExist() throws IOException {
        String dir = "tmp";
        String fn = "tmp/test";
        File directory = ensureEmpty(dir);

        long size = 1024 * 8L;
        File file = Systems.file(fn, size);
        assertThat(file.length(), is(size));
    }

    private File ensureEmpty(String dir) {
        File file = new File(dir);
        if (file.exists()) {
            file.delete();
        }
        file.mkdir();
        return file;
    }
}