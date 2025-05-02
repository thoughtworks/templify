package com.thoughtworks.kinds.handlers.javahandler;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.thoughtworks.config.PlaceholderSettings;
import com.thoughtworks.di.CoreModule;
import com.thoughtworks.kinds.api.FileHandlerException;
import com.thoughtworks.kinds.api.KindHandlerEvent;
import com.thoughtworks.kinds.api.KindMappingTemplate;
import com.thoughtworks.kinds.handlers.base.KindHandlerCommand;
import com.thoughtworks.services.CreateTemplateCommand;
import com.thoughtworks.services.CreateTemplateCommand.CreateTemplateCommandBuilder;
import com.thoughtworks.services.logger.RunnerLogger;
import com.thoughtworks.services.tasks.LoadConfigurationTask;

/**
 * JavaHandlerKindTest
 */
public class JavaHandlerKindTest {

    private static final String TARGET = "target/";
    private static final String BUILD_TEMPLATE_DIR = "/template";
    private static final String FIXTURE_JAVA_REPLACE = "src/test/resources-its/com/thoughtworks/mojos/TemplifyIT/basic_java_example/";

    private LoadConfigurationTask loadConfigurationTask = new LoadConfigurationTask();
    Injector injector = Guice.createInjector(new CoreModule());

    private CreateTemplateCommand spyCreateTemplateCommand;
    private JavaFileHandler spyJavaFileHandler;
    private JavaHandlerKind spyJavaHandlerKind;
    private RunnerLogger spyLogger;

    @BeforeEach
    public void setUp() {
        this.injector.injectMembers(loadConfigurationTask);

        String baseDir = FIXTURE_JAVA_REPLACE;
        String buildDir = baseDir + TARGET;

        var createTemplateCommand = new CreateTemplateCommandBuilder()
                .withBaseDir(new File(baseDir))
                .withBuildDir(new File(buildDir).getPath())
                .withTemplateDir(new File(buildDir).getPath() + BUILD_TEMPLATE_DIR)
                .build();

        spyCreateTemplateCommand = spy(this.loadConfigurationTask.execute(createTemplateCommand));

        spyLogger = spy(spyCreateTemplateCommand.getLogger());
        spyCreateTemplateCommand.setLogger(spyLogger);

        spyJavaFileHandler = spy(new JavaFileHandler());
        spyJavaHandlerKind = spy(new JavaHandlerKind(spyJavaFileHandler));
    }

    private void executeFixture() {
        for (KindMappingTemplate step : spyCreateTemplateCommand.getConfiguration().getSteps()) {
            KindHandlerEvent event = (new KindHandlerEvent(step, spyCreateTemplateCommand));
            spyJavaHandlerKind.subscribeToKindHandlerEvent(event);
        }
    }

    @Test
    void testException() throws FileHandlerException {
        // ARRANGE:
        KindHandlerCommand<JavaHandlerSpec> mockCommand = mock(KindHandlerCommand.class);
        doReturn(spyLogger).when(mockCommand).getLogger();
        doReturn(spyCreateTemplateCommand).when(mockCommand).getRequest();

        doThrow(new FileHandlerException("fake_error")).when(spyJavaFileHandler).replace(
                anyString(),
                anyString(),
                anyString());

        // ACT:
        assertThrows(RuntimeException.class, () -> executeFixture());

        verify(spyJavaFileHandler).replace(anyString(), anyString(), anyString());

        verify(spyLogger).error(contains("Error to replace placeholders"), any());
    }

    @Test
    void testExecute() throws FileHandlerException {
        // ARRANGE:
        KindHandlerCommand<JavaHandlerSpec> mockCommand = mock(KindHandlerCommand.class);
        doReturn(spyLogger).when(mockCommand).getLogger();
        doReturn(spyCreateTemplateCommand).when(mockCommand).getRequest();
        doNothing().when(spyJavaFileHandler).replace(anyString(), anyString(), anyString());

        // ACT:
        executeFixture();

        // ASSERT:
        verify(spyJavaHandlerKind, times(1)).handleJavaType(any(RunnerLogger.class), anyString(),
                any(PlaceholderSettings.class), anyString(), any());

        verify(spyLogger).info(contains("Executing JavaHandlerKind."));

        verify(spyLogger)
                .info(contains("Executing spec: src/main/java"));

        // verify(spyLogger)
        // .warn(contains("Command has no specs. Nothing to do."));

        verify(spyLogger).warn(contains("Replace: "));

        verify(spyJavaFileHandler).replace(anyString(), anyString(), anyString());

    }

    @Test
    void testShouldExecuteFalse() throws FileHandlerException {
        // ARRANGE:
        KindHandlerCommand<JavaHandlerSpec> mockCommand = mock(KindHandlerCommand.class);
        doReturn(spyLogger).when(mockCommand).getLogger();
        doReturn(spyCreateTemplateCommand).when(mockCommand).getRequest();

        KindHandlerEvent event = (new KindHandlerEvent(spyCreateTemplateCommand.getConfiguration().getSteps().get(0),
                spyCreateTemplateCommand));
        // ACT:
        spyJavaHandlerKind.subscribeToKindHandlerEvent(event);

        // ASSERT:
        verify(spyLogger).warn(contains("Event ignored."));

    }
}
