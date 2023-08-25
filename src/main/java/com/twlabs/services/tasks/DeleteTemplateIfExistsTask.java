package com.twlabs.services.tasks;

import java.io.IOException;
import com.twlabs.services.CreateTemplateRequest;
import com.twlabs.services.RunnerTask;
import com.twlabs.services.fs.FileSystem;
import com.twlabs.services.fs.FileSystemImpl;

/**
 * DeleteTemplateIfExistsTask
 */
public class DeleteTemplateIfExistsTask implements RunnerTask {


    FileSystem fs;


    public DeleteTemplateIfExistsTask() {
        this.fs = new FileSystemImpl();
    }

    public DeleteTemplateIfExistsTask(FileSystem fs) {
        this.fs = fs;
    }



    @Override
    public CreateTemplateRequest execute(CreateTemplateRequest request) {


        try {
            request.getLogger()
                    .info("Checking if the template dir exists" + request.getTemplateDir());

            if (fs.fileExists(request.getTemplateDir())) {
                fs.deleteDirectory(request.getTemplateDir());

                request.getLogger().info("Old template directory was found and it was removed!!");
            }
        } catch (IOException e) {
            request.getLogger()
                    .error("It was not possible to remove directory: " + request.getTemplateDir());
            throw new RuntimeException(e);

        }
        return request;
    }


}
