package base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.learn2pro.codeplayground.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @PACKAGE: base
 * @author: Dell
 * @DATE: 2020/6/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/spring-test-rpc-bean.xml")
public abstract class AbsBaseSpringTest {

}
