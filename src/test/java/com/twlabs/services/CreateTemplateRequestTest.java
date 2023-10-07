package com.twlabs.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import com.soebes.itf.jupiter.maven.MavenLog;
import com.twlabs.model.settings.PlaceholderSettings;
import com.twlabs.services.CreateTemplateRequest.CreateTemplateRequestBuilder;
import com.twlabs.services.logger.LoggerStrategyFactory;
import com.twlabs.services.logger.MavenLogger;
import com.twlabs.services.logger.RunnerLogger;

/**
 * CreateTemplateRequestTest
 */
public class CreateTemplateRequestTest {

    @ParameterizedTest
    @CsvSource({
            "/maven-cookiecutter.yml",
    })
    public void test_getMavenCookiecutterYml_is_corret(String ymlFile) {


        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

        assertEquals(ymlFile, requestBuilder.build().getMavenCookiecutterYml());
    }


    @ParameterizedTest
    @CsvSource({
            "fool/DestDir",
    })
    public void test_getDestDir_is_corret(String destDir) {

        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

        assertEquals(requestBuilder.withDestDir(destDir), requestBuilder);
        assertEquals(destDir, requestBuilder.build().getDestDir());
    }


    @ParameterizedTest
    @CsvSource({
            "foo/BuildDir",
    })
    public void test_getBuildDir_is_corret(String buildDir) {

        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

        assertEquals(requestBuilder.withBuildDir(buildDir), requestBuilder);
        assertEquals(buildDir, requestBuilder.build().getBuildDir());
    }

    @ParameterizedTest
    @CsvSource({
            "match, replace",
    })
    public void test_withPlaceholder_is_corret(String placeholderMatch, String placeholderReplace) {

        PlaceholderSettings placeholder =
                new PlaceholderSettings(placeholderMatch, placeholderReplace);
        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();
        assertEquals(requestBuilder.withPlaceholder(placeholder), requestBuilder);
        assertEquals(placeholder, requestBuilder.build().getPlaceholder());


    }

    @Test
    public void test_withRunnerLogger() {

        MavenLogger logger = Mockito.mock(MavenLogger.class);
        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

        assertEquals(requestBuilder.withLogger(logger), requestBuilder);
        assertEquals(logger, requestBuilder.build().getLogger());
    }


    @Test
    public void test_withLog() {

        Log log = Mockito.mock(Log.class);
        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();


        assertEquals(requestBuilder.withLogger(log), requestBuilder);
        assertEquals(requestBuilder.build().getLogger().getClass(), MavenLogger.class);

    }



}
