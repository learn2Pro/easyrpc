package org.learn2pro.codeplayground.rpc.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.learn2pro.codeplayground.rpc.core.abnormal.EasyIOException;
import org.learn2pro.codeplayground.rpc.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * name:org.learn2pro.codeplayground.rpc.client.RpcConsumerHandler author:tderong date:2020/6/24
 */
@ChannelHandler.Sharable
@Component
public class RpcConsumerHandler extends ChannelInboundHandlerAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(RpcConsumerHandler.class);

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    AsyncRpcMsgPool.getInstance().answer((RpcResponse) msg);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    LOGGER.error("[client]easy rpc invoke failed by io problems,pls check!", cause);
    ctx.channel().close();
    throw new EasyIOException("[client]easy rpc invoke failed by io problems,pls check!", cause);
  }

}
