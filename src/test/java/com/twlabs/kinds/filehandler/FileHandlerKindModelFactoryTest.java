package com.twlabs.kinds.filehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import com.twlabs.model.settings.StepsKindTemplate;

/**
 * FileHandlerKindModelFactoryTest
 */
public class FileHandlerKindModelFactoryTest {

    FileHandlerKindModelFactory factory = new FileHandlerKindModelFactory();

    @Test
    public void test_should_map_spec_metadata_and_kind_correctly() {

        StepsKindTemplate template = new StepsKindTemplate();
        List<Map<String, Object>> specs = new ArrayList<>();
        Map<String, Object> metadata = new HashMap<>();

        metadata.put("type", "typeValue");
        template.setMetadata(metadata);

        template.setKind("test");

        List<String> files = new ArrayList<>();
        files.add("test_file_1");

        Map<String, Object> spec1 = new HashMap<>();
        spec1.put("files", files);
        specs.add(spec1);
        template.setSpec(specs);

        FileHandlerKindModel build = factory.build(template);

        assertNotNull(build);
        assertEquals("test", build.getKind(), "Attribute kind should be mapped");
        assertEquals("typeValue", build.getMetadata().getType(), "Metadata::type should be mapped");
        assertEquals("test_file_1", build.getSpec().get(0).getFiles().get(0),
                "Spec::files should be mapped");

    }

    @Test
    public void should_throw_execption_if_missing_values() {

        StepsKindTemplate template = new StepsKindTemplate();

        template.setMetadata(null);

        assertThrows(IllegalArgumentException.class, () -> factory.build(template),
                "Should throw IllegalArgumentException if metadata is null or empty");

        template.setMetadata(new HashMap<>());

        assertThrows(IllegalArgumentException.class, () -> factory.build(template),
                "Should throw IllegalArgumentException if metadata is null or empty");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("type", "typeValue");
        template.setMetadata(metadata);

        template.setSpec(null);
        assertThrows(IllegalArgumentException.class, () -> factory.build(template),
                "Should throw IllegalArgumentException if spec is null or empty");

        template.setSpec(new ArrayList<>());
        assertThrows(IllegalArgumentException.class, () -> factory.build(template),
                "Should throw IllegalArgumentException if spec is null or empty");

    }
}
