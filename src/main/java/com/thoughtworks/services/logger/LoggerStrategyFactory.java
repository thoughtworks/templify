package com.thoughtworks.services.logger;

import java.util.logging.Logger;
import org.apache.maven.plugin.logging.Log;

/**
 * LoggerFactory
 * 
 * This class is responsible for creating instances of RunnerLogger based on the provided
 * parameters. It provides two methods for creating a RunnerLogger object: one that takes a Log
 * object as a parameter, and one that doesn't take any parameters.
 */
public class LoggerStrategyFactory {

    /**
     * create
     * 
     * This method creates a RunnerLogger object using the provided Log object.
     * 
     * @param log The Log object to be used for creating the RunnerLogger.
     * @return A RunnerLogger object created using the provided Log object.
     */
    public RunnerLogger create(Log log) {
        return new MavenLogger(log);
    }

    /**
     * create
     * 
     * This method creates a RunnerLogger object using the default Java Logger.
     * 
     * @return A RunnerLogger object created using the default Java Logger.
     */
    public RunnerLogger create() {
        return new JavaLogger(Logger.getLogger("com.thoughtworks"));
    }
}
