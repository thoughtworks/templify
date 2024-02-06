package com.twlabs.kinds.handlers.javahandler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.File;
import org.junit.jupiter.api.Test;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.twlabs.config.PlaceholderSettings;
import com.twlabs.di.ContextDependencyInjection;
import com.twlabs.kinds.handlers.base.KindHandlerCommand;
import com.twlabs.services.CreateTemplateCommand;
import com.twlabs.services.CreateTemplateCommand.CreateTemplateCommandBuilder;
import com.twlabs.services.logger.LoggerStrategyFactory;
import com.twlabs.services.logger.RunnerLogger;
import com.twlabs.services.tasks.LoadConfigurationTask;
import com.twlabs.kinds.api.KindHandlerEvent;
import com.twlabs.kinds.api.KindMappingTemplate;

/**
 * JavaHandlerKindTest
 */
public class JavaHandlerKindTest {

    private static final String TARGET = "target/";
    private static final String BUILD_TEMPLATE_DIR = "/template";
    private static final String FIXTURE_JAVA_REPLACE =
            "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_replace_java/";

    private LoadConfigurationTask loadConfigurationTask = new LoadConfigurationTask();
    Injector injector = Guice.createInjector(new ContextDependencyInjection());

    @Test
    void testExecute() {
        // ARRANGE:
        this.injector.injectMembers(loadConfigurationTask);

        String baseDir = FIXTURE_JAVA_REPLACE;
        String buildDir = baseDir + TARGET;

        // TODO: Create a CreateTemplateCommand using the fixture project, without using any mocks,
        // using the CreateTemplateCommandBuilder.
        CreateTemplateCommand createTemplateCommand = new CreateTemplateCommandBuilder()
                .withBaseDir(new File(baseDir))
                .withBuildDir(new File(buildDir).getPath())
                .withTemplateDir(new File(buildDir).getPath() + BUILD_TEMPLATE_DIR)
                .build();

        // Enhance the command by utilizing the LoadConfigurationTask and spy on it.
        CreateTemplateCommand spyCreateTemplateCommand =
                spy(this.loadConfigurationTask.execute(createTemplateCommand));

        // spy on the Logger
        spyCreateTemplateCommand.setLogger(spy(spyCreateTemplateCommand.getLogger()));


        JavaHandlerKind spyJavaHandlerKind = spy(new JavaHandlerKind());
        KindHandlerCommand<JavaHandlerSpec> mockCommand = mock(KindHandlerCommand.class);

        doReturn(spy(new LoggerStrategyFactory().create())).when(mockCommand).getLogger();
        doReturn(spy(createTemplateCommand)).when(mockCommand).getRequest();

        doNothing().when(spyJavaHandlerKind).handleJavaType(any(RunnerLogger.class), anyString(),
                any(PlaceholderSettings.class), anyString(), any());

        // ACT:
        for (KindMappingTemplate step : createTemplateCommand.getConfiguration().getSteps()) {
            KindHandlerEvent event = (new KindHandlerEvent(step, spyCreateTemplateCommand));
            spyJavaHandlerKind.subscribeToKindHandlerEvent(event);
        }

        // ASSERT:
        verify(spyJavaHandlerKind, times(1)).handleJavaType(any(RunnerLogger.class), anyString(),
                any(PlaceholderSettings.class), anyString(), any());
    }

}

