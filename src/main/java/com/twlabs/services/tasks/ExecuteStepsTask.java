package com.twlabs.services.tasks;

import static com.twlabs.kinds.KindExecutor.Names.FILE_HANDLER_KIND;
import java.util.List;
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

    @Inject
    @Named(FILE_HANDLER_KIND)
    private KindExecutor fileHandlerKind;

    public ExecuteStepsTask() {}

    public ExecuteStepsTask(KindExecutor fileHandlerKind) {
        this.fileHandlerKind = fileHandlerKind;
    }

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
                    request.getLogger().error("Unknown kind " + kind);
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
