import base.AbsBaseSpringTest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;
import org.learn2pro.codeplayground.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @PACKAGE: PACKAGE_NAME
 * @author: Dell
 * @DATE: 2020/6/23
 */
public class PingPongServiceTest extends AbsBaseSpringTest {

  @Autowired
  private PingService pingService;
  private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);

  @Test
  public void pingTest0() {
    pingService.ping();
  }

  @Test
  public void pingTest1() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(100000);
    for (int i = 0; i < 100000; i++) {
      EXECUTOR.execute(() -> {
        pingService.ping();
        latch.countDown();
      });
    }
    latch.await();
    System.out.println("ending!");
  }

  @Test
  public void queryUserTest0() throws InterruptedException {
    pingService.query();
  }

  @Test
  public void queryUserTest1() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(100000);
    for (int i = 0; i < 100000; i++) {
      EXECUTOR.execute(() -> {
        pingService.query();
        latch.countDown();
      });
    }
    latch.await();
    System.out.println("ending!");
  }
}
