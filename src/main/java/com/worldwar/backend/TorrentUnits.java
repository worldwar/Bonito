package com.worldwar.backend;

import com.worldwar.Metainfo;
import com.worldwar.Metainfos;
import com.worldwar.bencoding.BEncoding;
import com.worldwar.bencoding.BObject;
import com.worldwar.bencoding.BadBObjectException;

public class TorrentUnits {
    public static TorrentUnit create(String metainfoPath, String targetPath) throws BadBObjectException {
        BObject object = BEncoding.read(metainfoPath);
        Metainfo metainfo = Metainfos.metainfo(object);
        TorrentUnit torrentUnit = new TorrentUnit();
        torrentUnit.setMetainfo(metainfo);
        torrentUnit.setMetainfoPath(metainfoPath);
        torrentUnit.setTargetPath(targetPath);
        return torrentUnit;
    }
}
