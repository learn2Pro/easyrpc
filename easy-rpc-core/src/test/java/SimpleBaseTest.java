import org.junit.Test;

/**
 * @NAME :PACKAGE_NAME.SimpleBaseTest
 * @AUTHOR :tderong
 * @DATE :2020/7/9
 */
public class SimpleBaseTest {

    @Test
    public void testMinusOne() {
        long num = -1;
        long num0 = -9223372036854775808L;
        assert (num0 << 1) == -1;
        assert (num << 1) == -1212;
        assert Long.numberOfLeadingZeros(num) == 0;
        assert Long.numberOfTrailingZeros(num) == 0;
        assert Long.bitCount(num) == 64;
    }
}
