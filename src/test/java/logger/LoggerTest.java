package logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by pgwt on 2017/3/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)

public class LoggerTest {

    static public Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void logTC1() {
        logger.error("error");
        logger.debug("debug");
        logger.info("info");
        logger.trace("trace");
        logger.warn("warn");
        logger.error("error {}", "param");
    }

}
