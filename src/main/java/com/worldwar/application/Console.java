package com.worldwar.application;

import com.worldwar.Metainfo;
import com.worldwar.Metainfos;
import com.worldwar.bencoding.BEncoding;
import com.worldwar.bencoding.BadBObjectException;

import java.io.IOException;

public class Console {
    public static void main(String[] args) throws BadBObjectException, IOException {
        Metainfo metainfo = Metainfos.generateMetainfo("tmp/elephant.jpg");
        BEncoding.write("tmp/animal.torrent", Metainfos.bObject(metainfo));
        System.out.println("finished");
    }
}
