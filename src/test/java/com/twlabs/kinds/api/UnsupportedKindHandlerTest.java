package com.twlabs.kinds.api;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.twlabs.services.CreateTemplateCommand;
import com.twlabs.services.logger.LoggerStrategyFactory;
import com.twlabs.services.logger.RunnerLogger;

public class UnsupportedKindHandlerTest {

    private UnsupportedKindHandler unsupportedKindHandler;
    private CreateTemplateCommand command;
    private KindMappingTemplate template;
    private RunnerLogger spyLogger;

    @BeforeEach
    void setup() {
        unsupportedKindHandler = new UnsupportedKindHandler();
        command = mock(CreateTemplateCommand.class);
        template = spy(new KindMappingTemplate());
        spyLogger = spy(new LoggerStrategyFactory().create());
        doReturn(spyLogger).when(command).getLogger();
    }

    @Test
    void test_should_log_no_kindHandler_found_for_x() {

        // ARRANGE:
        doReturn("kind_fake").when(template).getKind();
        doReturn("apiVersion_fake").when(template).getApiVersion();

        KindHandlerEvent event = new KindHandlerEvent(template, command);

        // ACT:
        assertThrows(RuntimeException.class, () -> unsupportedKindHandler.handleDeadEvent(event));

        // ASSERT:
        verify(spyLogger, times(1)).info(contains(
                "No KindHandler found for: " + event.getKindName() + event.getApiVersion()));

    }


    @KindHandler(name = "kind_test", apiVersion = "apiVersion_test")
    static class KindHandlerStup {
        KindHandlerStup() {}
    }

    @Test
    void test_should_never_log_no_kindHandler_found_for_x_and_not_thrown() {
        // ARRANGE:
        doReturn("kind_test").when(template).getKind();
        doReturn("apiVersion_test").when(template).getApiVersion();

        KindHandlerEvent event = new KindHandlerEvent(template, command);

        // ACT:
        unsupportedKindHandler.handleDeadEvent(event);

        // ASSERT:
        verify(spyLogger, never()).info(contains(
                "No KindHandler found for: " + event.getKindName() + event.getApiVersion()));

    }
}

