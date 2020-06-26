package org.learn2pro.codeplayground.rpc.client;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.Map;
import java.util.Set;
import javax.annotation.PreDestroy;
import org.learn2pro.codeplayground.rpc.codec.CodecDecodeHandler;
import org.learn2pro.codeplayground.rpc.codec.CodecEncodeHandler;
import org.learn2pro.codeplayground.rpc.config.RpcConfigServer;
import org.learn2pro.codeplayground.rpc.core.ConsumerBeanPostProcessor;
import org.learn2pro.codeplayground.rpc.core.abnormal.EasyNoProviderException;
import org.learn2pro.codeplayground.rpc.model.RpcRequest;
import org.learn2pro.codeplayground.rpc.model.RpcResponse;
import org.learn2pro.codeplayground.rpc.server.RemoteAddr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * name:org.learn2pro.codeplayground.rpc.client.ConsumerRouteClient author:tderong date:2020/6/24
 */
public class ConsumerRouter {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerRouter.class);
  /**
   * remote to channel one-to-one mapping
   */
  private static final Map<RemoteAddr, ChannelHandlerContext> CHANNELS = Maps.newConcurrentMap();
  /**
   * service to remote server multi-to-one mapping
   */
  private static final Map<String, RemoteAddr> SERVICE2REMOTE = Maps.newConcurrentMap();
  @Autowired
  private RpcConfigServer rpcConfigServer;
  @Autowired
  private ConsumerBeanPostProcessor consumerBeanPostProcessor;
  @Autowired
  private RpcConsumerHandler rpcConsumerHandler;
  @Autowired
  private CodecEncodeHandler codecEncodeHandler;

  private Bootstrap bootstrap;
  private NioEventLoopGroup group;

  public void init() throws Exception {
    Set<RemoteAddr> remoteServer = findCandidates(consumerBeanPostProcessor.getRemoteServices());
    this.group = new NioEventLoopGroup();
    this.bootstrap = new Bootstrap();
    bootstrap.group(group)
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, true)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline()
                .addLast("decoding",
                    new CodecDecodeHandler(rpcConfigServer.fetchCodec(), RpcResponse.class))
                .addLast("encoding", codecEncodeHandler)
                .addLast("consumer", rpcConsumerHandler);
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
        SERVICE2REMOTE.put(service, server);
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
        Channel channel = future.sync().channel();
        CHANNELS.put(addr, channel.pipeline().lastContext());
      } catch (InterruptedException e) {
        LOGGER.error("connect to remote server:" + addr + " failed,pls retry!");
      }
    }
  }

  public ChannelHandlerContext choose(String service) {
    RemoteAddr addr = SERVICE2REMOTE.get(service);
    if (addr == null) {
      throw new EasyNoProviderException("service:[" + service + "] not found provider in remote");
    }
    ChannelHandlerContext channel = CHANNELS.get(addr);
    if (channel == null) {
      throw new EasyNoProviderException("service:[" + service + "] not found channel in remote");
    }
    return channel;
  }

}
