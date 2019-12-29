package com.shinemo.mpush.tools;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

public class IpTest {

    @Test
    public void getLocalIp() throws Exception{
        System.out.println(Ip.getInetAddress(true));
    }

    @Test
    public void testIp(){
        boolean ret = Ip.checkHealth("94.191.72.51",6379);
        System.out.println(ret);
    }

    public void uriTest() throws URISyntaxException{
        String url = "127.0.0.1";
    }
}
