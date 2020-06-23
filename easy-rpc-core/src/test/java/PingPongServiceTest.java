import base.AbsBaseSpringTest;
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
}
