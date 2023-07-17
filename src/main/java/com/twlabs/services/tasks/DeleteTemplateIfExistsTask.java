package com.twlabs.services.tasks;

import java.io.IOException;
import org.codehaus.plexus.util.FileUtils;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.RunnerTask;

/**
 * DeleteTemplateIfExistsTask
 */
public class DeleteTemplateIfExistsTask implements RunnerTask {

    @Override
    public CreateTemplateRequest execute(CreateTemplateRequest request) {
        try {
            request.getLogger().info("Checking if the template dir exists" + request.getTemplateDir());

            if (FileUtils.fileExists(request.getTemplateDir())) {
                FileUtils.deleteDirectory(request.getTemplateDir());
                request.getLogger().info("Old template directory was found and it was removed!!");
            }
        } catch (IOException e) {
            request.getLogger().error( "It was not possible to remove directory: " + request.getTemplateDir(), e);
            throw new RuntimeException(e);

        }
        return request;
    }
}
