package com.worldwar.application;

import com.worldwar.bencoding.BEncoding;
import com.worldwar.bencoding.BObject;

public class Console {
    public static void main(String[] args) {
        BObject object = BEncoding.read("a.torrent");
        BEncoding.write("b.torrent", object);
        System.out.println(object);
    }
}
