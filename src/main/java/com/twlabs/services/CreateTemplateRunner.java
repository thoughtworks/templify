package com.twlabs.services;
/*
 * This interface represents a runner for creating a template.
 * 
 * The runner is responsible for executing the creation of a template based on the provided request.
 * 
 * Usage:
 * 
 * CreateTemplateRunner runner = new CreateTemplateRunnerImpl();
 * CreateTemplateRequest request = new CreateTemplateRequest();
 * // Set request properties
 * runner.execute(request);
 * 
 * @param request The request object containing the necessary information for creating the template.
 * @throws NullPointerException if the request is null.
 * @throws TemplateCreationException if an error occurs during the template creation process.
 */
public interface CreateTemplateRunner {

    /**
     * Executes the creation of a template based on the provided request.
     * 
     * @param request The request object containing the necessary information for creating the template.
     */
    public void execute(CreateTemplateRequest request);

}

