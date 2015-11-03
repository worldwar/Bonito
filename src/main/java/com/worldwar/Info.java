package com.worldwar;


import java.util.List;

public class Info {
    private String name;
    private int pieceLength;
    private List<byte[]> pieces;
    private long length;
    private List<PathLength> files;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPieceLength() {
        return pieceLength;
    }

    public void setPieceLength(int pieceLength) {
        this.pieceLength = pieceLength;
    }

    public List<byte[]> getPieces() {
        return pieces;
    }

    public void setPieces(List<byte[]> pieces) {
        this.pieces = pieces;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public List<PathLength> getFiles() {
        return files;
    }

    public void setFiles(List<PathLength> files) {
        this.files = files;
    }
}
