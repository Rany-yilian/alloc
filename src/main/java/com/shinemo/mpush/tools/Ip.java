package com.shinemo.mpush.tools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Enumeration;

public class Ip {

    /**
     * 获取ip地址
     */
    public static String getInetAddress(boolean getLocal){
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()){
                Enumeration<InetAddress> addresses = interfaces.nextElement().getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress address = addresses.nextElement();
                    if(address.isLoopbackAddress()) continue;
                    if(address.getHostAddress().contains(":")) continue;
                    if(getLocal){
                        if(address.isSiteLocalAddress()){
                            return address.getHostAddress();
                        }
                    }else {
                        if(!address.isSiteLocalAddress()){
                            return address.getHostAddress();
                        }
                    }
                }
            }
            return getLocal ? "127.0.0.1":null;
        }catch (Throwable e){
            return getLocal ?"127.0.0.1":null;
        }
    }

    public static boolean checkHealth(String ip,int port){
        try{
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip,port),1000);
            socket.close();
            return true;
        }catch (IOException e){
            return false;
        }
    }
}
