package com.worldwar.integration;

import com.worldwar.backend.Listener;
import com.worldwar.backend.Connector;
import com.worldwar.backend.HandlerFactory;
import com.worldwar.backend.PeerHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HandlerFactory.class)
public class PeerMessageInteractiveTest {

    private PeerHandler serverHandler = HandlerFactory.server();
    private PeerHandler clientHandler = HandlerFactory.client();
    private Listener listener = new Listener();
    private Connector connector = new Connector();

    @Before
    public void before() throws Exception {
        mockStatic(HandlerFactory.class);

        PowerMockito.doReturn(serverHandler).when(HandlerFactory.class, "server");
        PowerMockito.doReturn(clientHandler).when(HandlerFactory.class, "client");
    }

    @Test
    public void handshakeInteractiveTest() throws Exception {
        listener.listen();

        assertThat(serverHandler.getConnectionManager().getStatus().handshakeDone(), is(false));
        assertThat(clientHandler.getConnectionManager().getStatus().handshakeDone(), is(false));

        connector.connect("localhost", 9999);
        Thread.sleep(1000);

        assertThat(serverHandler.getConnectionManager().getStatus().handshakeDone(), is(true));
        assertThat(clientHandler.getConnectionManager().getStatus().handshakeDone(), is(true));
    }

    @Test
    public void unchokeAndInterestedInteractiveTest() throws InterruptedException {
        listener.listen();

        assertThat(serverHandler.getConnectionManager().getStatus().peerChoking(), is(true));
        assertThat(serverHandler.getConnectionManager().getStatus().peerInterested(), is(false));

        connector.connect("localhost", 9999);
        Thread.sleep(1000);

        assertThat(serverHandler.getConnectionManager().getStatus().peerChoking(), is(false));
        assertThat(serverHandler.getConnectionManager().getStatus().peerInterested(), is(true));
    }

    @After
    public void after() {
        connector.shutdownGracefully();
        listener.shutdownGracefully();
    }
}
