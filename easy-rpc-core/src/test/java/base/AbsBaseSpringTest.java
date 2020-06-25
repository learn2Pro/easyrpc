package base;

import org.junit.runner.RunWith;
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
