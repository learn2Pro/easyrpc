package org.learn2pro.codeplayground.rpc.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.lang.reflect.Method;
import org.learn2pro.codeplayground.rpc.core.ann.Codec;
import org.learn2pro.codeplayground.rpc.model.RpcRequest;
import org.springframework.util.ReflectionUtils;

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
    RpcRequest request = JSON.parseObject(bytes, RpcRequest.class);
    Method m = ReflectionUtils
        .findMethod(request.getKlass(), request.getMethod(), request.getArgKlazz());
    int size = request.getArgs() == null ? 0 : request.getArgs().length;
    for (int i = 0; i < size; i++) {
      if (request.getArgs()[i] instanceof JSONObject) {
        request.getArgs()[i] = ((JSONObject) request.getArgs()[i])
            .toJavaObject(m.getParameterTypes()[0]);
      }
    }
    return request;
  }
}
