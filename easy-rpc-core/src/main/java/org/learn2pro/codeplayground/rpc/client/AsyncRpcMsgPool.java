package org.learn2pro.codeplayground.rpc.client;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.learn2pro.codeplayground.rpc.config.RpcConfigServer;
import org.learn2pro.codeplayground.rpc.model.RpcRequest;
import org.learn2pro.codeplayground.rpc.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author :tderong
 * @name :org.learn2pro.codeplayground.rpc.client.ConsumerMsgPool
 * @date :2020/6/25
 */
public class AsyncRpcMsgPool implements RpcMsgPool, InitializingBean, DisposableBean {

  private static final AsyncRpcMsgPool INSTANCE = new AsyncRpcMsgPool();
  private static final BlockingQueue<RpcRequest> requestQueue = new LinkedBlockingQueue<>(
      100);
  protected static final Map<String, RpcResponse> responsePool = Maps
      .newConcurrentMap();
  private static final Logger LOGGER = LoggerFactory.getLogger(AsyncRpcMsgPool.class);
  /**
   * Lock held by take, poll, etc
   */
  private final ReentrantLock takeLock = new ReentrantLock();

  /**
   * Wait queue for waiting takes
   */
  private final Condition updated = takeLock.newCondition();
  private volatile boolean stop = false;

  @Autowired
  private ConsumerRouter consumerRouter;
  @Autowired
  private RpcConfigServer rpcConfigServer;

  public static AsyncRpcMsgPool getInstance() {
    return INSTANCE;
  }

  private RpcResponse fetch(String requestId) throws InterruptedException {
    takeLock.lockInterruptibly();
    try {
      while (!responsePool.containsKey(requestId)) {
        updated.await();
      }
      RpcResponse response = responsePool.get(requestId);
      responsePool.remove(requestId);
      return response;
    } finally {
      takeLock.unlock();
    }
  }

  /**
   * fetch the specified response of request id
   *
   * @param requestId the request id
   * @return the response
   */
  private RpcResponse fetch(String requestId, long timeout)
      throws InterruptedException, TimeoutException {
    takeLock.lockInterruptibly();
    try {
      while (!responsePool.containsKey(requestId)) {
        if (!updated.await(timeout, TimeUnit.MILLISECONDS)) {
          throw new TimeoutException("fetch result timeout!");
        }
      }
      RpcResponse response = responsePool.get(requestId);
      responsePool.remove(requestId);
      return response;
    } finally {
      takeLock.unlock();
    }
  }

  @Override
  public void answer(RpcResponse response) {
    responsePool.put(response.getId(), response);
    signalNotEmpty();
  }

  /**
   * Signals a waiting take. Called only from put/offer (which do not otherwise ordinarily lock
   * takeLock.)
   */
  private void signalNotEmpty() {
    final ReentrantLock takeLock = this.takeLock;
    takeLock.lock();
    try {
      updated.signalAll();
    } finally {
      takeLock.unlock();
    }
  }

  /**
   * send requst to server
   *
   * @param request the request info
   * @return the future of response
   */
  public Future<RpcResponse> send(RpcRequest request) {
    requestQueue.add(request);
    return new RpcFuture(request.getSessionId());
  }

  @Override
  @SuppressWarnings("unchecked")
  public void afterPropertiesSet() throws Exception {
    PROCESSORS.execute(() -> {
      try {
        for (; ; ) {
          if (stop) {
            return;
          }
          RpcRequest request = requestQueue.take();
          consumerRouter.choose(request.getServiceId()).writeAndFlush(request);
        }
      } catch (InterruptedException e) {
        LOGGER.error("this request fetch failed!", e);
      }
    });
  }

  @Override
  public void destroy() throws Exception {
    stop = true;
  }

  static final class RpcFuture implements Future<RpcResponse> {

    /**
     * the request id
     */
    private String requestId;

    public RpcFuture(String requestId) {
      this.requestId = requestId;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
      return true;
    }

    @Override
    public boolean isCancelled() {
      return true;
    }

    @Override
    public boolean isDone() {
      return AsyncRpcMsgPool.responsePool.containsKey(requestId);
    }

    @Override
    public RpcResponse get() throws InterruptedException {
      return getInstance().fetch(requestId);
    }

    @Override
    public RpcResponse get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException {
      long to = 0;
      switch (unit) {
        case DAYS:
          to = TimeUnit.DAYS.toMillis(timeout);
          break;
        case HOURS:
          to = TimeUnit.HOURS.toMillis(timeout);
          break;
        case MINUTES:
          to = TimeUnit.MINUTES.toMillis(timeout);
          break;
        case SECONDS:
          to = TimeUnit.SECONDS.toMillis(timeout);
          break;
        case MILLISECONDS:
          to = TimeUnit.MILLISECONDS.toMillis(timeout);
          break;
        case MICROSECONDS:
          to = TimeUnit.MICROSECONDS.toMillis(timeout);
          break;
        case NANOSECONDS:
          to = TimeUnit.NANOSECONDS.toMillis(timeout);
          break;
      }
      return getInstance().fetch(requestId, to);
    }
  }
}
