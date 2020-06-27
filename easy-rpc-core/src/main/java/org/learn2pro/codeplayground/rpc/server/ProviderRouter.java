package org.learn2pro.codeplayground.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import java.net.InetSocketAddress;
import javax.annotation.PreDestroy;
import org.learn2pro.codeplayground.rpc.codec.CodecDecodeHandler;
import org.learn2pro.codeplayground.rpc.codec.CodecEncodeHandler;
import org.learn2pro.codeplayground.rpc.config.RpcConfigServer;
import org.learn2pro.codeplayground.rpc.model.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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
  @Autowired
  private RpcProviderHandler rpcProviderHandler;
  @Autowired
  private CodecEncodeHandler codecEncodeHandler;
  @Autowired
  private RpcConfigServer rpcConfigServer;
  private EventLoopGroup master;
  private EventLoopGroup workers;

  /**
   * start server for rpc provider
   *
   * @throws Exception init failed
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    master = new NioEventLoopGroup(1);
    workers = new NioEventLoopGroup(10);
    RemoteAddr addr = RemoteAddr.local();
    new Thread(() -> {
      try {
        ServerBootstrap server = new ServerBootstrap();
        server.group(master, workers)
            .channel(NioServerSocketChannel.class)
            .localAddress(new InetSocketAddress(Integer.parseInt(addr.getPort())))
            .childHandler(new ChannelInitializer<SocketChannel>() {
              @Override
              protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                    .addLast("prepend", new LengthFieldPrepender(2))
                    .addLast("remove", new LengthFieldBasedFrameDecoder(65536, 0, 2, 0, 2))
                    .addLast("decoder", new CodecDecodeHandler(rpcConfigServer.fetchCodec(),
                        RpcRequest.class))
                    .addLast("encoder", codecEncodeHandler)
                    .addLast("rpc_provider", rpcProviderHandler);
              }
            });
        ChannelFuture channelFuture = server.bind().sync();
        LOGGER.info(RpcProviderHandler.class.getName() +
            " started and listening for connections on " + channelFuture.channel().localAddress());
        channelFuture.channel().closeFuture().sync();
      } catch (Exception e) {
        master.shutdownGracefully();
        workers.shutdownGracefully();
      }
    }).start();
  }

  @PreDestroy
  public void destroy() throws InterruptedException {
    LOGGER.info("server shutdown going...");
    master.shutdownGracefully().sync();
    workers.shutdownGracefully().sync();
  }
}
