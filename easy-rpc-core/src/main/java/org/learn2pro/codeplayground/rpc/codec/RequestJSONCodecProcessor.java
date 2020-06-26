package org.learn2pro.codeplayground.rpc.codec;

import com.alibaba.fastjson.JSON;
import org.learn2pro.codeplayground.rpc.core.ann.Codec;
import org.learn2pro.codeplayground.rpc.model.RpcRequest;

/**
 * name:org.learn2pro.codeplayground.rpc.core.RequestCodecProcessor author:tderong date:2020/6/25
 */
@Codec(value = "JSON", target = RpcRequest.class)
public class RequestJSONCodecProcessor implements CodecProcessor<RpcRequest> {

  @Override
  public byte[] encode(RpcRequest data) {
    return JSON.toJSONBytes(data);
  }

  @Override
  public RpcRequest decode(byte[] bytes) {
    return JSON.parseObject(bytes, RpcRequest.class);
  }
}
