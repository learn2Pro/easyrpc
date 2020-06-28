import base.AbsBaseSpringTest;
import org.junit.Test;

/**
 * @NAME :PACKAGE_NAME.StaticClassTest
 * @AUTHOR :tderong
 * @DATE :2020/6/27
 */
public class StaticClassTest extends AbsBaseSpringTest {

  @Test
  public void testVariable() {
    StaticClass outer = new StaticClass();
    System.out.println("外部类静态变量加载时间：" + outer.OUTER_DATE);
  }

  @Test
  public void testInnerClass() {
    StaticClass outer = new StaticClass();
    System.out.println("外部类静态变量加载时间：" + outer.new InnerClass().INNER_DATE);
    System.out.println("外部类静态变量加载时间：" + outer.OUTER_DATE);
  }

  @Test
  public void testInnerStaticClass() throws InterruptedException {
    StaticClass outer = new StaticClass();
    Thread.sleep(3000);
    System.out.println("外部类静态变量加载时间：" + StaticClass.InnerStaticClass.INNER_STATIC_DATE);
  }

}
