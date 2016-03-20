package com.worldwar.utility;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.worldwar.backend.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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

    public static byte[] calculateBitfield(File file, List<byte[]> pieces, int pieceLength) throws IOException {
        byte[] result = new byte[Numbers.times(pieces.size(), 8)];
        Iterator<byte[]> iterator = pieces.iterator();
        int lastIndex = pieces.size() - 1;
        int i;
        int offset = 0;
        for (i = 0; i <= lastIndex; offset += pieceLength, i++) {
            byte[] hash = hash(file, offset, pieceLength);
            byte[] piece = iterator.next();
            if (Arrays.equals(hash, piece)) {
                Bits.set(result, i);
            }
        }
        return result;
    }

    public static List<byte[]> hashes(byte[] content, int pieceLength) {
        int length = content.length;
        int pieceCount = Numbers.times(length, pieceLength);
        List<byte[]> result = new ArrayList<>(pieceCount);
        int i;
        int lastIndex = pieceCount - 1;
        int offset = 0;
        for (i = 0; i < lastIndex; offset += pieceLength, i++) {
            result.add(hash(content, offset, pieceLength));
        }
        result.add(hash(content, offset, length - offset));
        return result;
    }

    public static byte[] hash(byte[] content, int offset, int length) {
        return Hashing.sha1().hashBytes(content, offset, length).asBytes();
    }

    public static byte[] hash(File file, long offset, int length) throws IOException {
        return hashCode(file, offset, length).asBytes();
    }

    public static HashCode hashCode(File file, long offset, int length) throws IOException {
        byte[] content = new byte[length];
        int actualLength = read(content, file, offset, length);
        return Hashing.sha1().hashBytes(content, 0, actualLength);
    }

    public static List<byte[]> pieces(File file, int pieceLength) throws IOException {
        long length = file.length();
        int pieceCount = (int)Numbers.times(length, pieceLength);
        List<byte[]> result = new ArrayList<>(pieceCount);
        int i;
        long lastIndex = pieceCount - 1;
        long offset = 0;
        for (i = 0; i < lastIndex; offset += pieceLength, i++) {
            result.add(hashCode(file, offset, pieceLength).asBytes());
        }
        result.add(hashCode(file, offset, (int) (length - offset)).asBytes());
        return result;
    }

    public static byte[] piece(File file, int index, int begin, int length) throws IOException {
        int offset = index * Constants.PIECE_LENGTH + begin;
        byte[] actual = new byte[length];
        int actualLength = read(actual, file, offset, length);
        if (actualLength == length) {
            return actual;
        }
        else {
            return Arrays.copyOf(actual, actualLength);
        }
    }

    public static TrackerRequest trackerRequest(TorrentContext context) {
        TrackerRequest trackerRequest = new TrackerRequest();
        trackerRequest.setUrl(context.getAnnounce());
        trackerRequest.setEvent("started");
        trackerRequest.setPeer_id(new String(Messages.PEER_ID));
        trackerRequest.setPort(8888);
        trackerRequest.setInfo_hash(new String(context.hashinfo()));
        return trackerRequest;
    }

    public static Request requestBuilder(TrackerRequest request) {
       return new RequestBuilder("GET")
                .setUrl(request.getUrl())
                .addQueryParam("info_hash", request.getInfo_hash())
                .addQueryParam("event", request.getEvent())
                .addQueryParam("peer_id", request.getPeer_id())
                .addQueryParam("port", String.valueOf(request.getPort()))
                .addQueryParam("uploaded", String.valueOf(request.getUploaded()))
                .addQueryParam("downloaded", String.valueOf(request.getDownloaded()))
                .addQueryParam("left", String.valueOf(request.getLeft()))
                .addQueryParam("compact", String.valueOf(request.getCompact()))
                .addQueryParam("no_peer_id", String.valueOf(request.isNo_peer_id()))
                .addQueryParam("numwant", String.valueOf(request.getNumwant()))
                .addQueryParam("key", request.getKey())
                .addQueryParam("trackerid", request.getTrackerid()).build();
    }
}
