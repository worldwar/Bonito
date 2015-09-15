package com.worldwar.backend;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class TorrentRegisterTest {

    @Mock
    private TorrentContext first;
    @Mock
    private TorrentContext second;
    @Mock
    private TorrentContext newFirst;

    TorrentUnit firstUnit = new TorrentUnit();
    TorrentUnit secondUnit = new TorrentUnit();
    TorrentUnit newFirstUnit = new TorrentUnit();


    private byte[] firstHashinfo = "first".getBytes();
    private byte[] secondHashinfo = "second".getBytes();
    @Before
    public void before() {
        firstUnit.setTargetPath("a");
        secondUnit.setTargetPath("b");
        newFirstUnit.setTargetPath("c");

        MockitoAnnotations.initMocks(this);
        when(first.hashinfo()).thenReturn(firstHashinfo);
        when(first.getUnit()).thenReturn(firstUnit);

        when(second.hashinfo()).thenReturn(secondHashinfo);
        when(second.getUnit()).thenReturn(secondUnit);

        when(newFirst.hashinfo()).thenReturn(firstHashinfo);
        when(newFirst.getUnit()).thenReturn(newFirstUnit);

        TorrentRegister.unregisterAll();
    }


    @Test
    public void shouldRegisterFirstContext() {
        assertNull(TorrentRegister.get(firstHashinfo));
        TorrentRegister.register(first);
        assertThat(TorrentRegister.get(firstHashinfo), is(first));
    }

    @Test
    public void shouldUnRegisterFirstContext() {
        TorrentRegister.register(first);
        TorrentRegister.unregister(first);
        assertNull(TorrentRegister.get(firstHashinfo));
    }

    @Test
    public void shouldRegisterSecondContext() {
        TorrentRegister.register(first);
        TorrentRegister.register(second);
        assertThat(TorrentRegister.get(secondHashinfo), is(second));
    }

    @Test
    public void shouldRegisterANewerContext() {
        TorrentRegister.register(first);
        TorrentRegister.register(second);
        TorrentRegister.register(newFirst);
        assertThat(TorrentRegister.get(firstHashinfo), is(newFirst));
    }
}