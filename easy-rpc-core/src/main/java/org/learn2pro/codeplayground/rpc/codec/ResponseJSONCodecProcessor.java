package org.learn2pro.codeplayground.rpc.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.lang.reflect.Method;
import org.learn2pro.codeplayground.rpc.client.AsyncRpcMsgPool;
import org.learn2pro.codeplayground.rpc.core.ann.Codec;
import org.learn2pro.codeplayground.rpc.model.RpcRequest;
import org.learn2pro.codeplayground.rpc.model.RpcResponse;
import org.springframework.util.ReflectionUtils;

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
    RpcResponse response = JSON.parseObject(bytes, RpcResponse.class);
    if (response.getData() instanceof JSONObject) {
      RpcRequest request = AsyncRpcMsgPool.getInstance().search(response.getId());
      Method m = ReflectionUtils
          .findMethod(request.getKlass(), request.getMethod(), request.getArgKlazz());
      Object parsedData = JSON
          .parseObject(((JSONObject) response.getData()).toJSONString(), m.getReturnType());
      response.setData(parsedData);
    }
    return response;
  }
}
