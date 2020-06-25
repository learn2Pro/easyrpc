package org.learn2pro.codeplayground.rpc.client;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.Map;
import java.util.Set;
import javax.annotation.PreDestroy;
import org.learn2pro.codeplayground.rpc.config.RpcConfigServer;
import org.learn2pro.codeplayground.rpc.core.ConsumerProcessor;
import org.learn2pro.codeplayground.rpc.server.RemoteAddr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * name:org.learn2pro.codeplayground.rpc.client.ConsumerRouteClient author:tderong date:2020/6/24
 */
@Component
public class ConsumerRouter implements InitializingBean, ApplicationContextAware {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerRouter.class);
  /**
   * the connect server address
   */
  private RemoteAddr addr;
  private ApplicationContext app;
  @Autowired
  private RpcConfigServer rpcConfigServer;
  @Autowired
  private ConsumerProcessor consumerProcessor;
  @Autowired
  private RpcConsumerHandler rpcConsumerHandler;
  /**
   * remote to channel one-to-one mapping
   */
  private Map<RemoteAddr, Channel> channels = Maps.newConcurrentMap();
  /**
   * service to remote server multi-to-one mapping
   */
  private Map<String, RemoteAddr> service2remote = Maps.newConcurrentMap();
  private Bootstrap bootstrap;
  private NioEventLoopGroup group;

  public ConsumerRouter(RemoteAddr addr) {
    this.addr = addr;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Set<RemoteAddr> remoteServer = findCandidates(consumerProcessor.getRemoteServices());
    this.group = new NioEventLoopGroup(1);
    this.bootstrap = new Bootstrap();
    bootstrap.group(group)
        .channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(rpcConsumerHandler);
          }
        });
    doConnect(bootstrap, remoteServer);
  }

  @PreDestroy
  public void destroy() throws InterruptedException {
    LOGGER.info("client shutdown going...");
    group.shutdownGracefully().sync();
  }

  private Set<RemoteAddr> findCandidates(Set<String> services) {
    Set<RemoteAddr> candidates = Sets.newHashSet();
    for (String service : services) {
      try {
        RemoteAddr server = rpcConfigServer.sense(service);
        service2remote.put(service, server);
        candidates.add(server);
      } catch (Throwable throwable) {
        LOGGER.error("find remote server failed for service:[" + service + "]", throwable);
      }
    }
    return candidates;
  }

  private void doConnect(Bootstrap bootstrap, Set<RemoteAddr> remoteAddrs) {
    for (RemoteAddr addr : remoteAddrs) {
      ChannelFuture future = bootstrap.connect(addr.getIp(), Integer.parseInt(addr.getPort()));
      try {
        channels.put(addr, future.sync().channel());
      } catch (InterruptedException e) {
        LOGGER.error("connect to remote server:" + addr + " failed,pls retry!");
      }
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.app = applicationContext;
  }
}
