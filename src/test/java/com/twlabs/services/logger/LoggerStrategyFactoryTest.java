package com.twlabs.services.logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import org.apache.maven.plugin.logging.Log;
import org.junit.jupiter.api.Test;



public class LoggerStrategyFactoryTest {

    private LoggerStrategyFactory factory = new LoggerStrategyFactory();

    @Test
    public void test_create_log_using_mvn_logger() {
        RunnerLogger logger = factory.create(mock(Log.class));
        assertThat(logger).isNotNull()
                .isInstanceOfAny(MavenLogger.class);
    }

    @Test
    public void test_create_log_using_java_util() {
        RunnerLogger logger = factory.create();
        assertThat(logger).isNotNull()
                .isInstanceOfAny(JavaLogger.class);
    }

}
