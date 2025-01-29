package com.thoughtworks.services.tasks;

import java.io.IOException;
import com.thoughtworks.services.CreateTemplateCommand;
import com.thoughtworks.services.RunnerTask;
import com.thoughtworks.services.fs.FileSystem;
import com.thoughtworks.services.fs.FileSystemImpl;

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
    public CreateTemplateCommand execute(CreateTemplateCommand request) {


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
