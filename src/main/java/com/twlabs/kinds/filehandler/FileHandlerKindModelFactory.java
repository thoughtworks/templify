package com.twlabs.kinds.filehandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twlabs.kinds.KindModelFactory;
import com.twlabs.model.settings.StepsKindTemplate;

/**
 * FileHandlerKindModelFactory
 */
public class FileHandlerKindModelFactory implements KindModelFactory<FileHandlerKindModel> {

    final ObjectMapper mapper = new ObjectMapper();

    @Override
    public FileHandlerKindModel build(StepsKindTemplate template) {

        List<FileHandlerKindModelSpec> specs = new ArrayList<>();
        FileHandlerKindModel model = new FileHandlerKindModel();
        FileHandlerKindModelMetadata metadata = null;

        if (template.getSpec() == null || template.getSpec().isEmpty())
            throw new IllegalArgumentException("spec required.");

        for (Map<String, Object> spec : template.getSpec()) {
            specs.add(mapper.convertValue(spec, FileHandlerKindModelSpec.class));
        }

        if (template.getMetadata() == null || template.getMetadata().isEmpty())
            throw new IllegalArgumentException("metadata required.");

        metadata =
                mapper.convertValue(template.getMetadata(), FileHandlerKindModelMetadata.class);

        model.setSpec(specs);
        model.setMetadata(metadata);

        if (template.getKind() != null)
            model.setKind(template.getKind());

        return model;
    }


}
