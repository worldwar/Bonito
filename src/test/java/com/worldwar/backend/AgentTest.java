package com.worldwar.backend;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AgentTest {

    @Test
    public void testAdd() throws Exception {
        Agent agent = new Agent("bonito.json");
        assertThat(agent.count(), is(2));
        agent.add("/Users/zhuran/temp/otp.torrent",
                "/Users/zhuran/temp/Erlang and OTP in Action.pdf", "Erlang and OTP in Action.pdf");
        agent = new Agent("bonito.json");
        assertThat(agent.count(), is(3));
    }
}
