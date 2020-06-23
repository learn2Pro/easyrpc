package org.learn2pro.codeplayground.rpc.core;

/**
 * @PACKAGE: org.learn2pro.codeplayground.rpc.core
 * @author: Dell
 * @DATE: 2020/6/23
 */
public class RemoteAddr {
    /**
     * the ip
     */
    private String ip;
    /**
     * the port
     */
    private String port;

    public RemoteAddr(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
