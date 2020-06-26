package org.learn2pro.codeplayground.rpc.core;

import com.google.common.collect.Maps;
import java.util.Map;
import org.learn2pro.codeplayground.rpc.core.ann.Gen;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author :tderong
 * @name :org.learn2pro.codeplayground.rpc.codec.CodecRegistry
 * @date :2020/6/25
 */
public class GeneratorRegistry {

  /**
   * scan package the codec extend class must in this package
   */
  private static final String SCAN_PACKAGE = "org.learn2pro";
  private static final Reflections ref = new Reflections(SCAN_PACKAGE);
  private static final Map<String, Generator> CONTAINER = Maps.newHashMap();
  private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorRegistry.class);

  static {
    ref.getSubTypesOf(Generator.class).forEach(r -> {
      try {
        Gen ann = r.getAnnotation(Gen.class);
        CONTAINER.put(ann.value(), r.newInstance());
      } catch (InstantiationException | IllegalAccessException e) {
        LOGGER.error(
            "initialize the codec processor instance [" + r.getName() + "] failed,pls check!", e);
      }
    });
  }

  /**
   * get the generator instance
   *
   * @param type the generator type
   * @return
   */
  public static Generator getInstance(String type) {
    if (CONTAINER.containsKey(type)) {
      return CONTAINER.get(type);
    }
    throw new IllegalStateException(
        "cannot find the match processor in factory,key[" + type + "]!");
  }

}
