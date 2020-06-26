package org.learn2pro.codeplayground.rpc.codec;

import com.alibaba.fastjson.JSON;
import org.learn2pro.codeplayground.rpc.core.ann.Codec;
import org.learn2pro.codeplayground.rpc.model.RpcResponse;

/**
 * name:org.learn2pro.codeplayground.rpc.core.RequestCodecProcessor author:tderong date:2020/6/25
 */
@Codec(value = "JSON", target = RpcResponse.class)
public class ResponseJSONCodecProcessor implements CodecProcessor<RpcResponse> {

  @Override
  public byte[] encode(RpcResponse data) {
    return JSON.toJSONBytes(data);
  }

  @Override
  public RpcResponse decode(byte[] bytes) {
    return JSON.parseObject(bytes, RpcResponse.class);
  }
}
