package com.thoughtworks.services;

import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import com.thoughtworks.services.tasks.CopyProjectTask;
import com.thoughtworks.services.tasks.DeleteTemplateIfExistsTask;
import com.thoughtworks.services.tasks.ExecuteStepsTask;
import com.thoughtworks.services.tasks.LoadConfigurationTask;

/**
 * RunnerDefaultTest
 */
public class RunnerDefaultTest {



    @Test
    public void test_task_sequence_should_be_guaranteed() {

        RunnerDefault runnerDefault = new RunnerDefault();

        runnerDefault.setCopyProject(mock(CopyProjectTask.class));
        runnerDefault.setExecuteSteps(mock(ExecuteStepsTask.class));
        runnerDefault.setLoadConfigurations(mock(LoadConfigurationTask.class));
        runnerDefault.setDeleteTemplateIfExists(mock(DeleteTemplateIfExistsTask.class));
        runnerDefault.execute(mock(CreateTemplateCommand.class));

        InOrder inOrder =
                Mockito.inOrder(runnerDefault.getDeleteTemplateIfExists(),
                        runnerDefault.getCopyProject(),
                        runnerDefault.getLoadConfigurations(), runnerDefault.getExecuteSteps());

        inOrder.verify(runnerDefault.getDeleteTemplateIfExists()).execute(Mockito.any());
        inOrder.verify(runnerDefault.getCopyProject()).execute(Mockito.any());
        inOrder.verify(runnerDefault.getLoadConfigurations()).execute(Mockito.any());
        inOrder.verify(runnerDefault.getExecuteSteps()).execute(Mockito.any());

    }

}
