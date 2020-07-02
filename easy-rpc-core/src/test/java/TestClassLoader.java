/**
 * @NAME :PACKAGE_NAME.TestClassLoader
 * @AUTHOR :tderong
 * @DATE :2020/6/29
 */
public class TestClassLoader extends ClassLoader {

  public TestClassLoader(ClassLoader parent) {
    super(parent);
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    return super.findClass(name);
  }
}
