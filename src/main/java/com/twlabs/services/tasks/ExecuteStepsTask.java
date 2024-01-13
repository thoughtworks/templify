package com.twlabs.services.tasks;

import java.util.List;
import com.google.common.eventbus.EventBus;
import com.twlabs.kinds.api.KindHandlerEvent;
import com.twlabs.kinds.api.KindMappingTemplate;
import com.twlabs.kinds.api.KindsEventBus;
import com.twlabs.services.CreateTemplateCommand;
import com.twlabs.services.RunnerTask;

public class ExecuteStepsTask implements RunnerTask {

    private EventBus eventBus = KindsEventBus.getInstance();

    public ExecuteStepsTask(EventBus bus) {
        this.eventBus = bus;
    }

    @Override
    public CreateTemplateCommand execute(CreateTemplateCommand request) {

        List<KindMappingTemplate> steps = request.getConfiguration().getSteps();

        for (KindMappingTemplate step : steps) {
            request.getLogger().warn("Producing KindHandlerEvent: " + step.getKind());
            eventBus.post(new KindHandlerEvent(step, request));
        }

        return request;
    }
}
