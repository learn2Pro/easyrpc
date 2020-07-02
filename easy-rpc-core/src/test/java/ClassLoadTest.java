import base.AbsBaseSpringTest;
import org.junit.Test;
import sun.misc.Launcher;

/**
 * @NAME :PACKAGE_NAME.ClassLoadTest
 * @AUTHOR :tderong
 * @DATE :2020/6/29
 */
class ConstClass {

  static {
    System.out.println("ConstClass init!");
  }

  public static final String HELLO_BINGO = "Hello Bingo";

}

public class ClassLoadTest extends AbsBaseSpringTest {

  @Test
  public void testSuperInit() {
    assert SubClass.value == 123;
  }

  @Test
  public void testSuperInit2() {
    SuperClass[] klazz = new SuperClass[10];
  }

  @Test
  public void testSuperInit3() {
    SubClass subClass = new SubClass();
    assert subClass != null;
  }

  @Test
  public void testClassInSameFile() {
    assert ConstClass.HELLO_BINGO.equals("Hello Bingo");
  }

  @Test
  public void classLoaderTest0() throws ClassNotFoundException {
    ClassLoader loader = this.getClass().getClassLoader();
    System.out.println(loader.getClass().getName());
    System.out.println("1");
    assert loader.loadClass("ConstClass") == ConstClass.class;
    System.out.println("2");
    assert Class.forName("ConstClass", true, loader) == ConstClass.class;
    System.out.println("3");
    assert Class.forName("ConstClass") == ConstClass.class;
  }

  @Test
  public void classLoaderTest1() {
    ClassLoader current = Thread.currentThread().getContextClassLoader();
    System.out.println(current);
    assert current == Launcher.getLauncher().getClassLoader();
    System.out.println(current.getParent());
    assert current.getParent() == Launcher.getLauncher().getClassLoader().getParent();
    System.out.println(current.getParent().getParent());
    assert current.getParent().getParent() == null;
  }


}
