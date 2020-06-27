import base.AbsBaseSpringTest;
import java.util.concurrent.CountDownLatch;
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

  @Test
  public void pingTest0() {
    pingService.ping();
  }

  @Test
  public void pingTest1() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(10);
    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        pingService.ping();
        latch.countDown();
      }).start();
    }
    latch.await();
    System.out.println("ending!");
  }
}
