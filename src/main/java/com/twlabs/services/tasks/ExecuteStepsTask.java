package com.twlabs.services.tasks;

import java.util.List;
import java.util.logging.Logger;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.twlabs.kinds.KindExecutor;
import com.twlabs.model.settings.StepsKindTemplate;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.RunnerTask;

/**
 * ExecuteStepsTask
 */
public class ExecuteStepsTask implements RunnerTask {

    private static Logger logger = Logger.getLogger(ExecuteStepsTask.class.getName());

    @Inject
    @Named(KindExecutor.Names.FILE_HANDLER_KIND)
    private KindExecutor fileHandlerKind;

    @Override
    public CreateTemplateRequest execute(CreateTemplateRequest request) {

        List<StepsKindTemplate> steps = request.getConfiguration().getSteps();

        for (StepsKindTemplate step : steps) {
            String kind = step.getKind();
            switch (kind) {
                case KindExecutor.Names.FILE_HANDLER_KIND:
                    this.fileHandlerKind.execute(step, request);
                    break;
                default:
                    logger.severe("Unknown kind " + kind);
                    break;
            }
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
