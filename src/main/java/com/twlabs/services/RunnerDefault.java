package com.twlabs.services;

import java.util.LinkedList;
import java.util.List;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * RunnerImpl is an implementation of the Runner interface that executes a series of tasks in a
 * specific order. The tasks include deleting a template if it exists, copying a project, loading
 * configuration, and executing steps. The execute method takes a RunnerRequest object as a
 * parameter and modifies it by executing each task in the order they were added.
 * 
 * @version 2.0
 */
public class RunnerDefault implements CreateTemplateRunner {

    @Inject
    @Named(RunnerTask.Names.COPY_PROJECT_TASK)
    RunnerTask copyProject;

    @Inject
    @Named(RunnerTask.Names.DELETE_TEMPLATE_FOLDER_TASK)
    RunnerTask deleteTemplateIfExists;

    @Inject
    @Named(RunnerTask.Names.LOAD_PLUGIN_CONFIGURATION_TASK)
    RunnerTask loadConfigurations;

    @Inject
    @Named(RunnerTask.Names.EXECUTE_STEPS_TASK)
    RunnerTask executeSteps;

    @Override
    public void execute(CreateTemplateRequest request) {

        List<RunnerTask> tasks = new LinkedList<>();

        tasks.add(this.deleteTemplateIfExists);
        tasks.add(this.copyProject);
        tasks.add(this.loadConfigurations);
        tasks.add(this.executeSteps);

        for (int i = 0; i < tasks.size(); i++) {
            request = tasks.get(i).execute(request);
        }
    }

    public RunnerTask getCopyProject() {
        return copyProject;
    }

    public void setCopyProject(RunnerTask copyProject) {
        this.copyProject = copyProject;
    }

    public RunnerTask getDeleteTemplateIfExists() {
        return deleteTemplateIfExists;
    }

    public void setDeleteTemplateIfExists(RunnerTask deleteTemplateIfExists) {
        this.deleteTemplateIfExists = deleteTemplateIfExists;
    }

    public RunnerTask getLoadConfigurations() {
        return loadConfigurations;
    }

    public void setLoadConfigurations(RunnerTask loadConfigurations) {
        this.loadConfigurations = loadConfigurations;
    }

    public RunnerTask getExecuteSteps() {
        return executeSteps;
    }

    public void setExecuteSteps(RunnerTask executeSteps) {
        this.executeSteps = executeSteps;
    }


}
