package com.twlabs.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.twlabs.services.CreateTemplateRequest.CreateTemplateRequestBuilder;

/**
 * CreateTemplateRequestTest
 */
public class CreateTemplateRequestTest {

    @ParameterizedTest
    @CsvSource({
            "/maven-cookiecutter.yml",
    })
    public void test_getMavenCookiecutterYml_is_corret(String ymlFile){
    

        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

        assertEquals(ymlFile, requestBuilder.build().getMavenCookiecutterYml());

    }


    @ParameterizedTest
    @CsvSource({
            "fool/DestDir",

    })
    public void test_getDestDir_is_corret(String destDir){
    
        CreateTemplateRequestBuilder requestBuilder = new CreateTemplateRequestBuilder();

        assertEquals(requestBuilder.withDestDir(destDir), requestBuilder);
        assertEquals(destDir, requestBuilder.build().getDestDir());

    }

}
