package com.twlabs.services.logger;

import org.apache.maven.plugin.logging.Log;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * MavenLoggerTest
 */
public class MavenLoggerTest {

    @Test
    public void test_maven_logger_facade() {

        String msg = "test maven logger";
        Exception test = new Exception("Test throwable");

        Log mavenLog = Mockito.mock(Log.class);

        MavenLogger logger = new MavenLogger(mavenLog);

        logger.warn(msg);
        Mockito.verify(mavenLog).warn(msg);

        logger.error(msg);
        Mockito.verify(mavenLog).error(msg);

        logger.error(msg, test);
        Mockito.verify(mavenLog).error(msg, test);

        logger.info(msg);
        Mockito.verify(mavenLog).info(msg);
    }
}
