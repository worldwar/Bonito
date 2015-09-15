package com.worldwar;

import java.util.Date;
import java.util.List;

public class Metainfo {
    private String announce;
    private List<List<String>> announces;
    private String createdBy;
    private Date creationDate;
    private String comment;
    private boolean isDictionary;
    private Info info;

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public List<List<String>> getAnnounces() {
        return announces;
    }

    public void setAnnounces(List<List<String>> announces) {
        this.announces = announces;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isDictionary() {
        return isDictionary;
    }

    public void setDictionary(boolean isDictionary) {
        this.isDictionary = isDictionary;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public long size() {
        return info.getLength();
    }
}

class Info {
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

class PathLength {
    private List<String> path;
    private long length;

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public long getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}