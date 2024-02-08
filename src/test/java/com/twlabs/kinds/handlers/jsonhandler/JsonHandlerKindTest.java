package com.twlabs.kinds.handlers.jsonhandler;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.twlabs.di.CoreModule;
import com.twlabs.kinds.api.FileHandler;
import com.twlabs.kinds.api.FileHandlerException;
import com.twlabs.kinds.handlers.base.KindDefaultSpec;
import com.twlabs.kinds.handlers.base.KindHandlerCommand;
import com.twlabs.kinds.handlers.base.KindPlaceholder;
import com.twlabs.kinds.handlers.javahandler.JavaHandlerSpec;
import com.twlabs.services.CreateTemplateCommand;
import com.twlabs.services.CreateTemplateCommand.CreateTemplateCommandBuilder;
import com.twlabs.services.logger.RunnerLogger;
import com.twlabs.services.tasks.LoadConfigurationTask;

public class JsonHandlerKindTest {

    private JsonHandlerKind spyJsonHandlerKind;
    private FileHandler spyJsonFileHandler;
    private static final String TARGET = "target/";
    private static final String BUILD_TEMPLATE_DIR = "/template";
    private static final String FIXTURE_JAVA_REPLACE =
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_replace_java/";

    private LoadConfigurationTask loadConfigurationTask = new LoadConfigurationTask();
    Injector injector = Guice.createInjector(new CoreModule());

    private CreateTemplateCommand spyCreateTemplateCommand;
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

        spyJsonFileHandler = spy(new JsonFileHandler());
        spyJsonHandlerKind = spy(new JsonHandlerKind(spyJsonFileHandler));

    }

    @Test
    void test_defaultHandler() throws FileHandlerException {
        // ARRANGE:
        KindHandlerCommand<KindDefaultSpec> mockCommand = mock(KindHandlerCommand.class);
        doNothing().when(spyJsonFileHandler).replace(anyString(), anyString(), anyString());
        doReturn(spyLogger).when(mockCommand).getLogger();
        doReturn(spyCreateTemplateCommand).when(mockCommand).getRequest();

        KindPlaceholder placeholder = new KindPlaceholder("teste", "teste");
        KindDefaultSpec kindDefaultSpec =
                new KindDefaultSpec(List.of("teste"), List.of(placeholder));

        doReturn(List.of(kindDefaultSpec)).when(mockCommand).getSpecs();

        // ACT:
        spyJsonHandlerKind.execute(mockCommand);

        // ASSERT:
        verify(spyJsonHandlerKind, times(1)).executeDefaultFileHandlers(any(JsonFileHandler.class),
                eq(mockCommand));

        verify(spyJsonFileHandler).replace(anyString(), anyString(), anyString());

        verify(spyLogger).info(contains("Executing"));

        verify(spyLogger).warn(contains("Start placeholder for: "));

        verify(spyLogger).warn(contains("Replace: "));
    }

    @Test
    void test_defaultHandler_should_raise_error_for_different_non_default_specifications()
            throws FileHandlerException {
        // ARRANGE:
        KindHandlerCommand<KindDefaultSpec> mockCommand = mock(KindHandlerCommand.class);
        doReturn(spyLogger).when(mockCommand).getLogger();
        doReturn(spyCreateTemplateCommand).when(mockCommand).getRequest();
        doReturn(List.of(new JavaHandlerSpec())).when(mockCommand).getSpecs();
        doThrow(new FileHandlerException("fake_error")).when(spyJsonFileHandler)
                .replace(anyString(), anyString(), anyString());

        KindPlaceholder placeholder = new KindPlaceholder("teste", "teste");
        KindDefaultSpec kindDefaultSpec =
                new KindDefaultSpec(List.of("teste"), List.of(placeholder));

        doReturn(List.of(kindDefaultSpec)).when(mockCommand).getSpecs();

        // ACT:
        assertThrows(RuntimeException.class, () -> spyJsonHandlerKind.execute(mockCommand));

        // ASSERT:
        verify(spyJsonHandlerKind, times(1)).executeDefaultFileHandlers(any(JsonFileHandler.class),
                eq(mockCommand));

        verify(spyLogger).error(contains("Error to replace placeholders"), any());
    }

    @Test
    void test_execute() throws FileHandlerException {
        // ARRANGE:
        KindHandlerCommand<KindDefaultSpec> mockCommand = mock(KindHandlerCommand.class);
        doNothing().when(spyJsonHandlerKind).executeDefaultFileHandlers(any(JsonFileHandler.class),
                eq(mockCommand));

        // ACT:
        spyJsonHandlerKind.execute(mockCommand);

        // ASSERT:
        verify(spyJsonHandlerKind, times(1))
                .executeDefaultFileHandlers(any(JsonFileHandler.class), eq(mockCommand));

    }
}

