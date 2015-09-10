package com.worldwar.backend.task;

import com.worldwar.backend.Connector;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

public class ConnectTask implements Callable<Void>{
    private InetSocketAddress address;

    public ConnectTask(InetSocketAddress address) {
        this.address = address;
    }

    @Override
    public Void call() throws Exception {
        try {
            Connector.connect(address.getHostString(), address.getPort());
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        return null;
    }
}
