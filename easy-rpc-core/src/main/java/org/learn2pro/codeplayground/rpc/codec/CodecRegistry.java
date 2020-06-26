package org.learn2pro.codeplayground.rpc.codec;

import com.google.common.collect.Maps;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import org.learn2pro.codeplayground.rpc.core.ann.Codec;
import org.learn2pro.codeplayground.rpc.core.enumerate.CodecType;
import org.learn2pro.codeplayground.rpc.model.RpcSerModel;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * name:org.learn2pro.codeplayground.rpc.codec.CodecRegistry author:tderong date:2020/6/25
 */
public class CodecRegistry {

  /**
   * scan package the codec extend class must in this package
   */
  private static final String SCAN_PACKAGE = "org.learn2pro";
  private static final Reflections ref = new Reflections(SCAN_PACKAGE);
  private static final Map<Pair<CodecType, Class<?>>, CodecProcessor> CONTAINER = Maps.newHashMap();
  private static final Logger LOGGER = LoggerFactory.getLogger(CodecRegistry.class);

  static {
    ref.getSubTypesOf(CodecProcessor.class).forEach(r -> {
      try {
        Codec ann = r.getAnnotation(Codec.class);
        CONTAINER.put(Pair.of(CodecType.valueOf(ann.value()), ann.target()), r.newInstance());
      } catch (InstantiationException | IllegalAccessException e) {
        LOGGER.error(
            "initialize the codec processor instance [" + r.getName() + "] failed,pls check!", e);
      }
    });
  }

  /**
   * get the processor instance
   *
   * @param type  the codec type
   * @param klazz the codec instance
   * @return
   */
  public static CodecProcessor<? extends RpcSerModel> getInstance(CodecType type, Class<?> klazz) {
    Pair pairKey = Pair.of(type, klazz);
    if (CONTAINER.containsKey(pairKey)) {
      return CONTAINER.get(pairKey);
    }
    throw new IllegalStateException(
        "cannot find the match processor in factory,key[" + type.name() + "," + klazz
            .getSimpleName() + "]!");
  }

}
