package com.twlabs.services.tasks;

import static com.twlabs.kinds.handlers.KindExecutor.Names.FILE_HANDLER_KIND;
import java.util.List;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.twlabs.kinds.api.KindHandlerEvent;
import com.twlabs.kinds.api.KindMappingTemplate;
import com.twlabs.kinds.api.KindsEventBus;
import com.twlabs.kinds.handlers.KindExecutor;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.RunnerTask;

/**
 * ExecuteStepsTask
 */
public class ExecuteStepsTask implements RunnerTask {

    @Inject
    @Named(FILE_HANDLER_KIND)
    private KindExecutor fileHandlerKind;

    public ExecuteStepsTask() {}

    public ExecuteStepsTask(KindExecutor fileHandlerKind) {
        this.fileHandlerKind = fileHandlerKind;
    }

    @Override
    public CreateTemplateRequest execute(CreateTemplateRequest request) {

        EventBus eventBus = KindsEventBus.getInstance();

        List<KindMappingTemplate> steps = request.getConfiguration().getSteps();

        for (KindMappingTemplate step : steps) {
            eventBus.post(new KindHandlerEvent(step, request));
        }

        return request;
    }

    public KindExecutor getFileHandlerKind() {
        return fileHandlerKind;
    }

    public void setFileHandlerKind(KindExecutor fileHandlerKind) {
        this.fileHandlerKind = fileHandlerKind;
    }
}
