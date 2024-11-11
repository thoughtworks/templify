package com.twlabs.services.tasks;

import java.io.File;
import java.io.IOException;
import com.twlabs.services.CreateTemplateCommand;
import com.twlabs.services.RunnerTask;
import com.twlabs.services.fs.FileSystem;
import com.twlabs.services.fs.FileSystemImpl;

/**
 * Copy base dir to the template dir folder to the replaces
 * 
 * @throws MojoExecutionException
 */
public class CopyProjectTask implements RunnerTask {



    FileSystem fs;



    public CopyProjectTask() {

        fs = new FileSystemImpl();
    }



    public CopyProjectTask(FileSystem fs) {
        this.fs = fs;
    }



    @Override
    public CreateTemplateCommand execute(CreateTemplateCommand request) {
        try {
            request.getLogger().info("CopyProjectTask: \n FROM: " + request.getBaseDir() + "\n TO: "
                    + request.getTemplateDir());
            fs.copyDirectoryStructure(request.getBaseDir(), new File(request.getTemplateDir()));

        } catch (IOException e) {
            request.getLogger()
                    .error("Something went wrong while copying the project to the template folder");
            throw new RuntimeException(e);
        }

        return request;
    }


}
