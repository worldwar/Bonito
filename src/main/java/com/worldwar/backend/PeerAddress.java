package com.worldwar.backend;

public class PeerAddress {
    private String address;
    private int port;

    public PeerAddress(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int hashCode() {
        return (address + ":" + port).hashCode();
    }
}
