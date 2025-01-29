package com.thoughtworks.services.tasks;

import java.util.List;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.thoughtworks.kinds.api.KindHandlerEvent;
import com.thoughtworks.kinds.api.KindMappingTemplate;
import com.thoughtworks.services.CreateTemplateCommand;
import com.thoughtworks.services.RunnerTask;

public class ExecuteStepsTask implements RunnerTask {

    @Inject
    protected EventBus eventBus;

    @Override
    public CreateTemplateCommand execute(CreateTemplateCommand request) {

        List<KindMappingTemplate> steps = request.getConfiguration().getSteps();

        for (KindMappingTemplate step : steps) {
            request.getLogger().info("Producing KindHandlerEvent: " + step.getKind());
            eventBus.post(new KindHandlerEvent(step, request));
        }

        return request;
    }
}
