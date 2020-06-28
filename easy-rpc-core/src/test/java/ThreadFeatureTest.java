import base.AbsBaseSpringTest;
import org.junit.Test;

/**
 * @NAME :PACKAGE_NAME.ThreadFeatureTest
 * @AUTHOR :tderong
 * @DATE :2020/6/27
 */
public class ThreadFeatureTest extends AbsBaseSpringTest {

  public void enter0(boolean sleepOrWait) {
    try {
      synchronized (this) {
        System.out.println("thread in:" + Thread.currentThread().getName());
        if (sleepOrWait) {
          this.wait(3000);
        } else {
          Thread.sleep(3000);
        }
        System.out.println("thread interupt status:" + Thread.interrupted());
        System.out.println("thread out:" + Thread.currentThread().getName());
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testWaitReleaseLock0() throws InterruptedException {
    Thread t0 = new Thread(() -> enter0(true), "t0");
    Thread t1 = new Thread(() -> enter0(true), "t1");
    t0.start();
    t1.start();
  }

  @Test
  public void testWaitReleaseLock1() throws InterruptedException {
    Thread t0 = new Thread(() -> enter0(false), "t0");
    Thread t1 = new Thread(() -> enter0(false), "t1");
    t0.start();
    t1.start();
  }

  @Test
  public void testWaitReleaseLock2() throws InterruptedException {
    Thread t0 = new Thread(() -> enter0(false), "t0");
    Thread t1 = new Thread(() -> enter0(false), "t1");
    t0.start();
    t0.interrupt();
    t1.start();
  }

  @Test
  public void testWaitReleaseLock3() throws InterruptedException {
    Thread t0 = new Thread(() -> enter0(true), "t0");
    Thread t1 = new Thread(() -> enter0(true), "t1");
    t0.start();
    t0.interrupt();
    t1.start();
  }

  @Test
  public void testWaitReleaseLock4() throws InterruptedException {
    Thread t0 = new Thread(() -> enter0(true), "t0");
    Thread t1 = new Thread(() -> enter0(true), "t1");
    t0.start();
    t1.interrupt();
    t1.start();
  }

}
