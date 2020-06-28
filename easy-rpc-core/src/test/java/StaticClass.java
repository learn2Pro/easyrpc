/**
 * 静态类 class加载测试
 */
public class StaticClass {

  public static long OUTER_DATE = System.currentTimeMillis();

  static {
    System.out.println("外部类静态块加载时间：" + System.currentTimeMillis());
  }

  public StaticClass() {
    System.out.println("外部类构造函数时间：" + System.currentTimeMillis());
  }

  static class InnerStaticClass {

    public static long INNER_STATIC_DATE = System.currentTimeMillis();

    static {
      System.out.println("静态内部类静态块加载时间：" + System.currentTimeMillis());
    }
  }

  class InnerClass {

    public long INNER_DATE = 0;

    public InnerClass() {
      INNER_DATE = System.currentTimeMillis();
    }
  }
}