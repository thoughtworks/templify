package com.thoughtworks.kinds.handlers.directoryhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.thoughtworks.di.CoreModule;
import com.thoughtworks.kinds.api.FileHandlerException;
import com.thoughtworks.kinds.api.KindHandlerEvent;
import com.thoughtworks.kinds.api.KindMappingTemplate;
import com.thoughtworks.kinds.handlers.base.KindDefaultSpec;
import com.thoughtworks.kinds.handlers.base.KindHandlerCommand;
import com.thoughtworks.services.CreateTemplateCommand;
import com.thoughtworks.services.CreateTemplateCommand.CreateTemplateCommandBuilder;
import com.thoughtworks.services.logger.RunnerLogger;
import com.thoughtworks.services.tasks.LoadConfigurationTask;

public class DirectoryHandlerKindTest {

    private static final String TARGET = "target/";
    private static final String BUILD_TEMPLATE_DIR = "/template";
    private static final String FIXTURE_DIRECTORY_REPLACE = "src/test/resources-its/com/thoughtworks/mojos/TemplifyIT/test_directory/";

    private LoadConfigurationTask loadConfigurationTask = new LoadConfigurationTask();
    Injector injector = Guice.createInjector(new CoreModule());

    private CreateTemplateCommand spyCreateTemplateCommand;
    private DirectoryHandler spyDirectoryHandler;
    private DirectoryHandlerKind spyDirectoryHandleKind;
    private RunnerLogger spyLogger;

    @BeforeEach
    public void setUp() {
        this.injector.injectMembers(loadConfigurationTask);

        String baseDir = FIXTURE_DIRECTORY_REPLACE;
        String buildDir = baseDir + TARGET;

        var createTemplateCommand = new CreateTemplateCommandBuilder()
                .withBaseDir(new File(baseDir))
                .withBuildDir(new File(buildDir).getPath())
                .withTemplateDir(new File(buildDir).getPath() + BUILD_TEMPLATE_DIR)
                .build();

        spyCreateTemplateCommand = spy(this.loadConfigurationTask.execute(createTemplateCommand));

        spyLogger = spy(spyCreateTemplateCommand.getLogger());
        spyCreateTemplateCommand.setLogger(spyLogger);

        spyDirectoryHandler = spy(new DirectoryHandler());
        spyDirectoryHandleKind = spy(new DirectoryHandlerKind(spyDirectoryHandler));
    }

    private void executeFixture() {
        for (KindMappingTemplate step : spyCreateTemplateCommand.getConfiguration().getSteps()) {
            KindHandlerEvent event = (new KindHandlerEvent(step, spyCreateTemplateCommand));
            spyDirectoryHandleKind.subscribeToKindHandlerEvent(event);
        }
    }

    @Test
    public void test_execute() throws FileHandlerException {
        KindHandlerCommand<DirectoryHandlerSpec> mockCommand = mock(KindHandlerCommand.class);
        doReturn(spyLogger).when(mockCommand).getLogger();
        doReturn(spyCreateTemplateCommand).when(mockCommand).getRequest();
        doNothing().when(spyDirectoryHandler).replace(anyString(), anyString(), anyString());

        executeFixture();

        verify(spyLogger).info(contains("Executing DirectoryHandlerKind."));
        verify(spyLogger).info(contains("[DirectoryHandlerKind] Executing with placeholder settings: "));
        verify(spyLogger).info(contains("[DirectoryHandlerKind] For 1 specs."));
        verify(spyLogger).info(contains("[DirectoryHandlerKind] Executing spec with baseDir: "));
        verify(spyLogger, never()).warn((contains("baseDir parameter is null or empty!")));
    }

    @Test
    public void test_do_replace() throws FileHandlerException {
        KindHandlerCommand<KindDefaultSpec> mockCommand = mock(KindHandlerCommand.class);
        doReturn(spyLogger).when(mockCommand).getLogger();
        doReturn(spyCreateTemplateCommand).when(mockCommand).getRequest();
        doNothing().when(spyDirectoryHandler).replace(anyString(), anyString(), anyString());

        executeFixture();

        verify(spyLogger).warn(contains("Replace: "));
        verify(spyDirectoryHandler).replace(anyString(), anyString(), anyString());
    }

    @Test
    public void test_exception() throws FileHandlerException {
        KindHandlerCommand<KindDefaultSpec> mockCommand = mock(KindHandlerCommand.class);
        doReturn(spyLogger).when(mockCommand).getLogger();
        doReturn(spyCreateTemplateCommand).when(mockCommand).getRequest();

        doThrow(new FileHandlerException("fake_error")).when(spyDirectoryHandler).replace(
                anyString(),
                anyString(),
                anyString());

        assertThrows(RuntimeException.class, () -> executeFixture());

        verify(spyDirectoryHandler).replace(anyString(), anyString(), anyString());
        verify(spyLogger).error(contains("Error to replace placeholders"), any());
        ;
    }

    @Test
    public void test_withouth_baseDir() throws FileHandlerException {
        DirectoryHandlerSpec spec = mock(DirectoryHandlerSpec.class);
        when(spec.getBaseDir()).thenReturn("");

        String result = spyDirectoryHandleKind.getBasedDir(spyLogger, spec);

        assertEquals("", result, "if baseDir is null, return \"\"");

        verify(spyLogger).warn(contains("baseDir parameter is null or empty!"));
        verify(spyLogger).warn(contains("Setting based directory to template root"));

    }

}
