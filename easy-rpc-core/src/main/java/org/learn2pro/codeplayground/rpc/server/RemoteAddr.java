package org.learn2pro.codeplayground.rpc.server;

import java.net.InetAddress;
import java.util.Objects;

/**
 * @PACKAGE: org.learn2pro.codeplayground.rpc.core
 * @author: Dell
 * @DATE: 2020/6/23
 */
public class RemoteAddr {

  private static final String LOCAL_IP = "127.0.0.1";
  private static final String DEFAULT_PORT = "8421";
  /**
   * the ip
   */
  private String ip;
  /**
   * the port
   */
  private String port;

  /**
   * the constructor
   *
   * @param ip   the ip
   * @param port the port
   */
  public RemoteAddr(String ip, String port) {
    this.ip = ip;
    this.port = port;
  }

  public RemoteAddr(String port) throws Exception {
    this.ip = InetAddress.getLocalHost().getHostAddress();
    this.port = port;
  }

  public static RemoteAddr local() {
    return new RemoteAddr(LOCAL_IP, DEFAULT_PORT);
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

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    RemoteAddr that = (RemoteAddr) o;
    return Objects.equals(ip, that.ip) &&
        Objects.equals(port, that.port);
  }

  @Override
  public String toString() {
    return "RemoteAddr{" +
        "ip='" + ip + '\'' +
        ", port='" + port + '\'' +
        '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(ip, port);
  }


}
