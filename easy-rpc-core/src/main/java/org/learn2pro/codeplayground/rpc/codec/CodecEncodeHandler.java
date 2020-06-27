package org.learn2pro.codeplayground.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;
import org.learn2pro.codeplayground.rpc.config.RpcConfigServer;
import org.learn2pro.codeplayground.rpc.model.RpcSerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @NAME :org.learn2pro.codeplayground.rpc.codec.CodecEncodeHandler
 * @AUTHOR :tderong
 * @DATE :2020/6/26
 */
@Sharable
@Component
public class CodecEncodeHandler extends MessageToMessageEncoder<RpcSerModel> {

  @Autowired
  private RpcConfigServer rpcConfigServer;

  @Override
  @SuppressWarnings("unchecked")
  protected void encode(ChannelHandlerContext ctx, RpcSerModel msg, List out) throws Exception {
    byte[] bytes = ((CodecProcessor<RpcSerModel>) CodecRegistry
        .getInstance(rpcConfigServer.fetchCodec(), msg.getClass())).encode(msg);
    ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer().writeBytes(bytes);
    out.add(byteBuf);
  }
}
