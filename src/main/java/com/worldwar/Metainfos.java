package com.worldwar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.worldwar.bencoding.BObject;
import com.worldwar.bencoding.BType;
import com.worldwar.bencoding.BadBObjectException;

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

    private static Info info(Map map) {
        Info info = new Info();
        info.setName((String)map.get(INFO_NAME_KEY));
        info.setPieceLength((Integer) map.get(INFO_PIECE_LENGTH_KEY));
        String piecesString = (String)map.get(INFO_PIECES_KEY);
        byte[] p = piecesString.getBytes();
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
}
