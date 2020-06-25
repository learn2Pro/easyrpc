package org.learn2pro.codeplayground.rpc.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.learn2pro.codeplayground.rpc.config.RpcConfigServer;
import org.learn2pro.codeplayground.rpc.core.CodecRegistry;
import org.learn2pro.codeplayground.rpc.model.RpcResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * name:org.learn2pro.codeplayground.rpc.client.RpcConsumerHandler author:tderong date:2020/6/24
 */
@ChannelHandler.Sharable
@Component
public class RpcConsumerHandler extends SimpleChannelInboundHandler<ByteBuf> {

  @Autowired
  private RpcConfigServer rpcConfigServer;

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
    RpcResponse response = (RpcResponse) CodecRegistry
        .getInstance(rpcConfigServer.fetchCodec(), RpcResponse.class).decode(msg.array());

  }
}
