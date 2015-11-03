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

    public List<byte[]> pieces() {
        return info.getPieces();
    }

    public int pieceLength() {
        return info.getPieceLength();
    }

    public long targetLength() {
        return info.getLength();
    }
}
