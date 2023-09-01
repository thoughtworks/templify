package com.twlabs.kinds.filehandler;

import static com.twlabs.interfaces.FileHandler.Names.JAVA;
import static com.twlabs.interfaces.FileHandler.Names.JSON;
import static com.twlabs.interfaces.FileHandler.Names.XML;
import static com.twlabs.interfaces.FileHandler.Names.YAML;
import static com.twlabs.interfaces.FileHandler.Names.YML;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fixtures.ContextTestDependencyInjection;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.twlabs.exceptions.FileHandlerException;
import com.twlabs.handlers.JavaHandler;
import com.twlabs.handlers.JsonHandler;
import com.twlabs.handlers.XMLHandler;
import com.twlabs.handlers.YamlHandler;
import com.twlabs.interfaces.ConfigReader;
import com.twlabs.interfaces.FileHandler;
import com.twlabs.model.settings.StepsKindTemplate;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.CreateTemplateRequest.CreateTemplateRequestBuilder;
import com.twlabs.services.YamlConfigReader;
import com.twlabs.services.tasks.LoadConfigurationTask;

public class FileHandlerKindExecutorTest {

    private static final String BUILD_TEMPLATE_DIR = "/template";

    @Inject
    private FileHandlerKindExecutor fileHandlerKindExecutor;

    @Inject
    private LoadConfigurationTask taskConfig;

    @Inject
    @Named(XML)
    private FileHandler xmlHandler;

    @BeforeEach
    public void setup() {
        var injector = Guice.createInjector(new ContextTestDependencyInjection() {
            @Override
            protected void configure() {
                bind(FileHandler.class).annotatedWith(Names.named(JAVA))
                        .toInstance(spy(JavaHandler.class));
                bind(FileHandler.class).annotatedWith(Names.named(JSON))
                        .toInstance(spy(JsonHandler.class));
                bind(FileHandler.class).annotatedWith(Names.named(XML))
                        .toInstance(spy(XMLHandler.class));
                bind(FileHandler.class).annotatedWith(Names.named(YAML))
                        .toInstance(spy(YamlHandler.class));
                bind(FileHandler.class).annotatedWith(Names.named(YML))
                        .toInstance(spy(YamlHandler.class));
                bind(FileHandlerKindExecutor.class)
                        .toInstance(spy(FileHandlerKindExecutor.class));
                bind(ConfigReader.class).to(YamlConfigReader.class);
            }
        });
        injector.injectMembers(this);
    }

    @Test
    public void test_injector_fixtures() throws FileHandlerException {

        String baseDir =
                "src/test/resources-its/com/twlabs/mojos/CookieCutterMojoIT/test_replace_default_pom_file/";
        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

        String buildDir = baseDir + "target/";
        String templateDir = buildDir + BUILD_TEMPLATE_DIR;

        requestBuilder
                .withBaseDir(new File(baseDir))
                .withBuildDir(buildDir)
                .withTemplateDir(templateDir);

        CreateTemplateRequest req = requestBuilder.build();
        req = this.create_step_kind_template(req);

        req.getBaseDir().getAbsolutePath();

        StepsKindTemplate stepsKindTemplate = req.getConfiguration().getSteps().get(0);

        doNothing().when(this.xmlHandler).replace(any(), any(), any());

        this.fileHandlerKindExecutor.execute(stepsKindTemplate, req);
    }


    public CreateTemplateRequest create_step_kind_template(CreateTemplateRequest req) {

        return this.taskConfig.execute(req);
    }
}
