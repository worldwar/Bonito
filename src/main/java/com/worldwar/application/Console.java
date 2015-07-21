package com.worldwar.application;

import com.worldwar.Metainfo;
import com.worldwar.Metainfos;
import com.worldwar.bencoding.BEncoding;
import com.worldwar.bencoding.BObject;
import com.worldwar.bencoding.BadBObjectException;

public class Console {
    public static void main(String[] args) throws BadBObjectException {
        BObject object = BEncoding.read("a.torrent");
        Metainfo metainfo = Metainfos.metainfo(object);
        System.out.println(object);
    }
}
