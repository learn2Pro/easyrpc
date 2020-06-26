package org.learn2pro.codeplayground.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import org.learn2pro.codeplayground.rpc.core.enumerate.CodecType;
import org.learn2pro.codeplayground.rpc.model.RpcSerModel;

/**
 * @NAME :org.learn2pro.codeplayground.rpc.codec.CodecEncodeHandler
 * @AUTHOR :tderong
 * @DATE :2020/6/26
 */
@Sharable
public class CodecDecodeHandler extends MessageToMessageDecoder<ByteBuf> {

  /**
   * codec type
   */
  private CodecType codecType;
  /**
   * parse class
   */
  private Class<?> klass;

  public CodecDecodeHandler(CodecType codecType, Class<?> klass) {
    this.codecType = codecType;
    this.klass = klass;
  }

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List out) throws Exception {
    int size = msg.readableBytes();
    byte[] bytes = new byte[size];
    msg.readBytes(bytes);
    RpcSerModel serModel = CodecRegistry.getInstance(codecType, klass).decode(bytes);
    out.add(serModel);
  }
}
