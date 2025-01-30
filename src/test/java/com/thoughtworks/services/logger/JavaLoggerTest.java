package com.thoughtworks.services.logger;

import static org.mockito.Mockito.mock;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.logging.Level;

/**
 * JavaLoggerTest
 */
public class JavaLoggerTest {


    @Test
    public void test_java_logger_facade() {

        String msg = "test logger";
        Exception test = new Exception("Test throwable");

        Logger javaLogger = mock(Logger.class);

        JavaLogger logger = new JavaLogger(javaLogger);

        logger.warn(msg);
        Mockito.verify(javaLogger).warning(msg);

        logger.error(msg);
        Mockito.verify(javaLogger).severe(msg);

        logger.error(msg, test);
        Mockito.verify(javaLogger).log(Level.SEVERE, msg, test);

        logger.info(msg);
        Mockito.verify(javaLogger).info(msg);
    }

}
