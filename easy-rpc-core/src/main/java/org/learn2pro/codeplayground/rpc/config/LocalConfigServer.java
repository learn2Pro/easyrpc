package org.learn2pro.codeplayground.rpc.config;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import org.learn2pro.codeplayground.rpc.server.RemoteAddr;
import org.springframework.stereotype.Component;

/**
 * name:org.learn2pro.codeplayground.rpc.config.LocalConfigServer author:tderong date:2020/6/25
 */
@Component
public class LocalConfigServer implements RpcConfigServer {

  public static final Random rnd = new Random();
  /**
   * the configuration in local jvm
   */
  private Map<String, Set<RemoteAddr>> configuration = Maps.newConcurrentMap();
  private Map<String, Class<?>> services = Maps.newConcurrentMap();

  @Override
  public RemoteAddr sense(String service) throws IllegalAccessException {
    Preconditions.checkArgument(configuration.containsKey(service), "not found server,pls retry!");
    Set<RemoteAddr> servers = configuration.get(service);
    int roundrobin = rnd.nextInt(servers.size());
    int i = 0;
    for (RemoteAddr current : servers) {
      if (roundrobin == i) {
        return current;
      }
      i++;
    }
    throw new IllegalAccessException("not found server to provider service:[" + service + "]");
  }

  @Override
  public void register(RemoteAddr addr, String service, Class<?> klass) {
    Set<RemoteAddr> addrSet = configuration.getOrDefault(service, Sets.newHashSet());
    addrSet.add(addr);
    configuration.put(service, addrSet);
    services.put(service, klass);
  }

  @Override
  public void unregister(RemoteAddr addr) {
    for (Entry<String, Set<RemoteAddr>> entry : configuration.entrySet()) {
      entry.getValue().remove(addr);
    }
  }

}
