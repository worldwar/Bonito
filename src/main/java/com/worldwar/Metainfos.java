package com.worldwar;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.google.common.base.Charsets;
import com.worldwar.backend.Constants;
import com.worldwar.bencoding.*;
import com.worldwar.utility.Lists;
import com.worldwar.utility.Systems;

public class Metainfos {
    public static final String ANNOUNCE_KEY = "announce";
    public static final String CREATED_BY_KEY = "created by";
    public static final String ANNOUNCE_LIST_KEY = "announce-list";
    public static final String CREATION_DATE_KEY = "creation date";
    public static final String COMMENT_KEY = "comment";
    public static final String INFO_KEY = "info";
    public static final String INFO_NAME_KEY = "name";
    public static final String INFO_FILES_KEY = "files";
    public static final String INFO_PIECE_LENGTH_KEY = "piece length";
    public static final String INFO_PIECES_KEY = "pieces";
    public static final String INFO_LENGTH_KEY = "length";
    public static final String INFO_PATH_KEY = "path";

    public static Metainfo metainfo(BObject object) throws BadBObjectException {
        if (object.getType() == BType.DICTIONARY) {
            Metainfo metainfo = new Metainfo();
            Map<String, Object> dictionary = (Map<String, Object>)(object.normalize());
            metainfo.setAnnounce((String) dictionary.get(ANNOUNCE_KEY));
            metainfo.setCreatedBy((String) dictionary.get(CREATED_BY_KEY));
            metainfo.setAnnounces((List) dictionary.get(ANNOUNCE_LIST_KEY));
            metainfo.setCreationDate(new Date(1000 * Long.valueOf((Integer)dictionary.get(CREATION_DATE_KEY))));
            metainfo.setComment((String)dictionary.get(COMMENT_KEY));
            Map<String, Object> infoMap = (Map<String, Object>) dictionary.get(INFO_KEY);
            if (infoMap.containsKey(INFO_LENGTH_KEY)) {
                metainfo.setDictionary(false);
            } else {
                metainfo.setDictionary(true);
            }
            metainfo.setInfo(info(infoMap));
            return metainfo;
        } else {
            throw new BadBObjectException();
        }
    }

    public static BObject bObject(Metainfo metainfo) {
        Map<String, Object> map = new HashMap<>();
        map.put(ANNOUNCE_KEY, metainfo.getAnnounce());
        map.put(CREATED_BY_KEY, metainfo.getCreatedBy());
        map.put(ANNOUNCE_LIST_KEY, metainfo.getAnnounces());
        map.put(CREATION_DATE_KEY, (int)(metainfo.getCreationDate().getTime() / 1000));
        map.put(COMMENT_KEY, metainfo.getComment());
        Info info = metainfo.getInfo();

        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put(INFO_NAME_KEY, info.getName());
        infoMap.put(INFO_PIECE_LENGTH_KEY, info.getPieceLength());
        infoMap.put(INFO_PIECES_KEY, new String(Lists.concat(info.getPieces()), StandardCharsets.ISO_8859_1));

        if (!metainfo.isDictionary()) {
            infoMap.put(INFO_LENGTH_KEY, info.getLength());
        } else {
            List<PathLength> files = info.getFiles();
            List<Map> fileList = new ArrayList<>();
            for (PathLength pathLength : files) {
                Map<String, Object> pathMap = new HashMap<>();

                List<String> path = pathLength.getPath();
                long length = pathLength.getLength();
                pathMap.put(INFO_LENGTH_KEY, length);
                pathMap.put(INFO_PATH_KEY, path);
                fileList.add(pathMap);
            }
            infoMap.put(INFO_FILES_KEY, fileList);
        }
        map.put(INFO_KEY, infoMap);
        return BObjects.dictionary(map);
    }

    private static Info info(Map map) {
        Info info = new Info();
        info.setName((String)map.get(INFO_NAME_KEY));
        info.setPieceLength((Integer) map.get(INFO_PIECE_LENGTH_KEY));
        String piecesString = (String)map.get(INFO_PIECES_KEY);
        byte[] p = piecesString.getBytes(StandardCharsets.ISO_8859_1);
        List<byte[]> pieces = partition(p, 20);
        info.setPieces(pieces);
        if (map.containsKey(INFO_LENGTH_KEY)) {
            info.setLength((Integer)map.get(INFO_LENGTH_KEY));
        } else {
            List<Map<String, Object>> files = (List<Map<String, Object>>) map.get(INFO_FILES_KEY);
            List<PathLength> r = new ArrayList<>();
            for (Map<String, Object> element : files) {
                PathLength pathLength = new PathLength();
                pathLength.setLength((Integer)element.get(INFO_LENGTH_KEY));
                pathLength.setPath((List<String>)element.get(INFO_PATH_KEY));
                r.add(pathLength);
            }
            info.setFiles(r);
        }
        return info;
    }

    private static List<byte[]> partition(byte[] elements, int size) {
        List<byte[]> result = new ArrayList<>();
        for (int i = 0; i < elements.length; i = i + size) {
            byte[] range = Arrays.copyOfRange(elements, i, i + size);
            result.add(range);
        }
        return result;
    }

    public static Metainfo generateMetainfo(String filename) throws IOException {
        File file = new File(filename);

        Metainfo metainfo = new Metainfo();
        metainfo.setAnnounce("http://localhost:80/announce");
        metainfo.setComment("This is a test torrent");
        metainfo.setCreatedBy("Bonito-1.0.0");
        metainfo.setCreationDate(new Date());
        metainfo.setDictionary(false);

        Info info = new Info();
        info.setLength(file.length());
        info.setName(file.getName());
        info.setPieceLength(Constants.PIECE_LENGTH);
        info.setPieces(Systems.pieces(file, Constants.PIECE_LENGTH));

        metainfo.setInfo(info);
        return metainfo;
    }

    public static byte[] hashinfo(Info info) {
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put(INFO_PIECE_LENGTH_KEY, info.getPieceLength());
        infoMap.put(INFO_PIECES_KEY, new String(Lists.concat(info.getPieces())));
        infoMap.put(INFO_NAME_KEY, info.getName());

        long size = info.getLength();
        if (size != 0) {
            //single file mode
            infoMap.put(INFO_LENGTH_KEY, size);
        } else {
            //multiple file mode
            List<PathLength> files = info.getFiles();
            List<Map> fileList = new ArrayList<>();
            for (PathLength pathLength : files) {
                Map<String, Object> pathMap = new HashMap<>();
                List<String> path = pathLength.getPath();
                long length = pathLength.getLength();
                pathMap.put(INFO_LENGTH_KEY, length);
                pathMap.put(INFO_PATH_KEY, path);
                fileList.add(pathMap);
            }
            infoMap.put(INFO_FILES_KEY, fileList);
        }
        String encode = BEncoding.encode(infoMap);
        byte[] bytes = encode.getBytes(Charsets.UTF_8);
        return Systems.hash(bytes, 0, bytes.length);
    }

    public static byte[] hashinfo(Metainfo metainfo) {
        return hashinfo(metainfo.getInfo());
    }

    public static Metainfo read(String filename) {
        BObject bObject = BEncoding.read(filename);
        try {
            return metainfo(bObject);
        } catch (BadBObjectException e) {
            return null;
        }
    }
}
