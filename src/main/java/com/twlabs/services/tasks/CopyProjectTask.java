package com.twlabs.services.tasks;

import java.io.File;
import java.util.logging.Logger;
import org.codehaus.plexus.util.FileUtils;
import com.twlabs.services.RunnerRequest;
import com.twlabs.services.RunnerTask;

/**
 * Copy base dir to the template dir folder to the replaces
 * 
 * @throws MojoExecutionException
 */
public class CopyProjectTask implements RunnerTask {

    private static Logger logger = Logger.getLogger(CopyProjectTask.class.getName());

    @Override
    public RunnerRequest execute(RunnerRequest request) {
        try {
            CopyProjectTask.logger.info("CopyProjectTask");
            FileUtils.copyDirectoryStructure(request.getBaseDir(), new File(request.getTemplateDir()));

        } catch (Exception e) {
            CopyProjectTask.logger.severe(e.getMessage());
            throw new RuntimeException(
                    "Something went wrong while copying the project to the template folder.", e);
        }

        return request;
    }


}
