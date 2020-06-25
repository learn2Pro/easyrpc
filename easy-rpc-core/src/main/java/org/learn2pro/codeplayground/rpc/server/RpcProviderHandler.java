package org.learn2pro.codeplayground.rpc.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.learn2pro.codeplayground.rpc.core.EasyIOException;

/**
 * name:org.learn2pro.codeplayground.rpc.server.RpcProviderHandler author:tderong date:2020/6/23
 */
@ChannelHandler.Sharable
public class RpcProviderHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    // 覆盖channelRead()事件处理程序方法
    ByteBuf in = (ByteBuf) msg;
    System.out.println(
        "Server received: " + in.toString(CharsetUtil.UTF_8));
    ctx.write(in);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx)
      throws Exception {
    // channelRead()执行完成后，关闭channel连接
    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
        .addListener(ChannelFutureListener.CLOSE);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx,
      Throwable cause) throws EasyIOException {
    ctx.close();
    throw new EasyIOException("easy rpc invoke failed by io problems,pls check!", cause);
  }
}
