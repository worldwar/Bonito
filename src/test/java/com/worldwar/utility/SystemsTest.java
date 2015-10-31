package com.worldwar.utility;

import com.worldwar.Metainfo;
import com.worldwar.Metainfos;
import com.worldwar.backend.Bits;
import com.worldwar.backend.Constants;
import com.worldwar.backend.TorrentContexts;
import com.worldwar.bencoding.BEncoding;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SystemsTest {

    private String dir = "tmp";
    private String fn = "tmp/test";
    private String fileForHash = "tmp/target.txt";
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

    @Test
    public void targetBitfieldShouldHasRightLength() throws IOException {
        int pieceLength = 7;
        int pieceCount = Numbers.times((int) size, pieceLength);
        List<byte[]> pieces = new ArrayList<>();

        for (int i = 0; i < pieceCount; i++) {
            pieces.add(new byte[20]);
        }

        byte[] bitfield = Systems.calculateBitfield(file, pieces, pieceLength);

        assertThat(bitfield.length, is(Numbers.times(pieceCount, 8)));
    }

    @Test
    public void shouldCalculateCorrectBitfieldIfTargetFileIsFinished() throws IOException {
        byte[] content = "Hello,world!Happy new year!Hello,world!Happy new year!Hello,world!Happy new year!".getBytes();
        int pieceLength = 7;
        int pieceCount = Numbers.times(content.length, pieceLength);
        List<byte[]> hashes = Systems.hashes(content, pieceLength);
        File target = Systems.file(fileForHash, content.length);
        Systems.write(target, 0, content);
        byte[] bitfield = Systems.calculateBitfield(target, hashes, pieceLength);
        assertThat(TorrentContexts.finished(bitfield, pieceCount), is(true));
    }

    @Test
    public void shouldCalculateCorrectBitfieldNotFinished() throws IOException {
        byte[] content = "Hello,world!Happy new year!Hello,world!Happy new year!Hello,world!Happy new year!".getBytes();
        byte[] uncompletedContent = "Hello,world!Happy new year!Hello,world!Happy new year!Hello,world!Happy new year\0".getBytes();
        int pieceLength = 7;
        int pieceCount = Numbers.times(content.length, pieceLength);
        List<byte[]> hashes = Systems.hashes(content, pieceLength);
        File target = Systems.file(fileForHash, content.length);
        Systems.write(target, 0, uncompletedContent);
        byte[] bitfield = Systems.calculateBitfield(target, hashes, pieceLength);
        byte[] expectBitfield = new byte[Numbers.times(pieceCount, 8)];
        for (int i = 0; i < pieceCount - 1; i++) {
            Bits.set(expectBitfield, i);
        }
        assertThat(Arrays.equals(bitfield, expectBitfield), is(true));
    }

    @Test
    public void shouldGenerateRightMetainfo() throws IOException {
        String filename = "animal.txt";
        Metainfo metainfo = Metainfos.generateMetainfo(filename);
        BEncoding.write("animal.torrent", Metainfos.bObject(metainfo));
        List<byte[]> pieces = metainfo.pieces();
        File file1 = new File(filename);
        int pieceCount = Numbers.times((int) file1.length(), Constants.PIECE_LENGTH);
        int byteCount = Numbers.times(pieceCount, 8);
        byte[] bytes = Systems.calculateBitfield(file1, pieces, Constants.PIECE_LENGTH);
        byte[] expect = new byte[byteCount];
        Bits.set(expect, 0, pieceCount);
        assertThat(Arrays.equals(bytes, expect), is(true));
    }
}