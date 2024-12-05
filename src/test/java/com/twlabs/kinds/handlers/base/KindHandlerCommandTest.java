package com.twlabs.kinds.handlers.base;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import com.twlabs.services.CreateTemplateCommand;
import com.twlabs.services.CreateTemplateCommand.CreateTemplateCommandBuilder;

public class KindHandlerCommandTest {

    @Test
    void test_name_and_metadata() {
        // Arrange
        List<String> specs = new ArrayList<>();
        specs.add("string");
        String expectedName = "name";
        Map<String, String> metadata = new HashMap<>();

        CreateTemplateCommandBuilder requestBuilder = new CreateTemplateCommandBuilder();
        CreateTemplateCommand cmd = requestBuilder.build();

        // Act
        KindHandlerCommand<String> kindHandlerCommand =
                new KindHandlerCommand<>(
                        expectedName,
                        metadata,
                        specs,
                        cmd);

        // Asserts
        assertNotNull(kindHandlerCommand.getMetadata());
        assertNotNull(kindHandlerCommand.getMetadata().get());
        assertEquals(kindHandlerCommand.getMetadata().get(), metadata);
        assertArrayEquals(specs.toArray(), kindHandlerCommand.getSpecs().toArray());

        assertNotNull(kindHandlerCommand.getName());
        assertEquals(kindHandlerCommand.getName(), expectedName);
    }
}

