package org.learn2pro.codeplayground.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author: Dell
 * @DATE: 2020/6/23
 */
@Component
public class ProviderRouter implements InitializingBean {

  /**
   * logger instance
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ProviderRouter.class);
  private RemoteAddr addr;

  /**
   * start server for rpc provider
   *
   * @throws Exception init failed
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      ServerBootstrap server = new ServerBootstrap();
      server.group(group)
          .channel(NioServerSocketChannel.class)
          .localAddress(new InetSocketAddress(Integer.parseInt(addr.getPort())))
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline()
                  .addLast("pb_enconding", new ProtobufEncoder())
                  .addLast("rpc_provider", new RpcProviderHandler());
            }
          });
      ChannelFuture future = server.bind().sync();
      LOGGER.info(RpcProviderHandler.class.getName() +
          " started and listening for connections on " + future.channel().localAddress());
      future.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully().sync();
    }
  }
}
