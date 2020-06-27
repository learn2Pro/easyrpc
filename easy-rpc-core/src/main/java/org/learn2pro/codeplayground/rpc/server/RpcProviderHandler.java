package org.learn2pro.codeplayground.rpc.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.learn2pro.codeplayground.rpc.config.RpcConfigServer;
import org.learn2pro.codeplayground.rpc.core.ProviderRepository;
import org.learn2pro.codeplayground.rpc.core.abnormal.EasyIOException;
import org.learn2pro.codeplayground.rpc.model.RpcRequest;
import org.learn2pro.codeplayground.rpc.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @NAME : org.learn2pro.codeplayground.rpc.server.RpcProviderHandler
 * @AUTHOR : tderong
 * @DATE : 2020/6/23
 */
@ChannelHandler.Sharable
@Component
public class RpcProviderHandler extends ChannelInboundHandlerAdapter {
  /**
   * the logger instance
   */
  public static final Logger LOGGER = LoggerFactory.getLogger(RpcProviderHandler.class);
  private static final ThreadLocal<String> SESSIONS = new ThreadLocal<>();

  @Override
  @SuppressWarnings("unchecked")
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    // 覆盖channelRead()事件处理程序方法
    RpcRequest request = (RpcRequest) msg;
    SESSIONS.set(request.getSessionId());
    LOGGER.info("request [" + request.getSessionId() + "] is processing!");
    RpcResponse response = ProviderRepository.getInstance().invoke(request);
    ctx.channel().writeAndFlush(response);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx)
      throws Exception {
    SESSIONS.remove();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx,
      Throwable cause) throws EasyIOException {
    ctx.write(RpcResponse.networkError(SESSIONS.get()));
    ctx.close();
    throw new EasyIOException("[server]easy rpc invoke failed by io problems,pls check!", cause);
  }
}
