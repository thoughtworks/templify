package com.thoughtworks.kinds.handlers.xmlhandler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.thoughtworks.di.CoreModule;
import com.thoughtworks.kinds.api.KindHandlerEvent;
import com.thoughtworks.kinds.api.KindMappingTemplate;
import com.thoughtworks.kinds.handlers.base.KindDefaultSpec;
import com.thoughtworks.kinds.handlers.base.KindHandlerCommand;
import com.thoughtworks.services.CreateTemplateCommand;
import com.thoughtworks.services.CreateTemplateCommand.CreateTemplateCommandBuilder;
import com.thoughtworks.services.tasks.LoadConfigurationTask;

public class XmlHandlerKindTest {

    private static final String TARGET = "target/";
    private static final String BUILD_TEMPLATE_DIR = "/template";
    private static final String FIXTURE_GENERIC_XML_FILES =
            "src/test/resources-its/com/thoughtworks/mojos/TemplifyIT/basic_xml_example/";
    LoadConfigurationTask loadConfigurationTask = new LoadConfigurationTask();

    Injector injector = Guice.createInjector(new CoreModule());


    @Test
    void test_execute_should_call_execute_default_file_handler_mock() {
        // Arrange
        XmlHandlerKind spyXMLHandlerKind = spy(new XmlHandlerKind());
        KindHandlerCommand<KindDefaultSpec> mockCommand = mock(KindHandlerCommand.class);
        doNothing().when(spyXMLHandlerKind).executeDefaultFileHandlers(any(XmlFileHandler.class),
                eq(mockCommand));

        // Act
        spyXMLHandlerKind.execute(mockCommand);

        // Assert
        verify(spyXMLHandlerKind, times(1))
                .executeDefaultFileHandlers(any(XmlFileHandler.class), eq(mockCommand));
    }

    @Test
    void test_execute_should_call_execute_default_file_handler() throws IOException {
        this.injector.injectMembers(loadConfigurationTask);
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        // ---- Arrange ----
        String baseDir =
                FIXTURE_GENERIC_XML_FILES;
        String buildDir = baseDir + TARGET;

        // Create a CreateTemplateCommand using the fixture project, without using any mocks, using
        // the CreateTemplateCommandBuilder.
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

        // Create an instance of XmlHandlerKind and spy on it.
        // XmlHandlerKind spyXMLHandlerKind = new XmlHandlerKind();
        XmlHandlerKind spyXMLHandlerKind = spy(new XmlHandlerKind());

        doNothing().when(spyXMLHandlerKind).executeDefaultFileHandlers(any(XmlFileHandler.class),
                any(KindHandlerCommand.class));

        // ----------- Act -------
        // Execute the subscriber method directly with a pre-built event.
        for (KindMappingTemplate step : createTemplateCommand.getConfiguration().getSteps()) {
            KindHandlerEvent event = (new KindHandlerEvent(step, spyCreateTemplateCommand));
            spyXMLHandlerKind.subscribeToKindHandlerEvent(event);
        }

        // TODO improve asserts with argThat and Matchers
        // Assert
        verify(spyXMLHandlerKind, times(1))
                .executeDefaultFileHandlers(any(XmlFileHandler.class),
                        any(KindHandlerCommand.class));

        verify(spyXMLHandlerKind, times(1))
                .convertEventToCommand(any(KindHandlerEvent.class));

        verify(spyXMLHandlerKind, times(1))
                .shouldProcessEvent(any(KindHandlerEvent.class));

        verify(spyCreateTemplateCommand.getLogger()).info(contains("Event accepted."));
        verify(spyCreateTemplateCommand.getLogger()).info(contains("Converting Event to Command."));
    }


}
