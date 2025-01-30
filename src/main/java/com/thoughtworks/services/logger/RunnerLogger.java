package com.thoughtworks.services.logger;

/**
 * Logger Interface
 */
public interface RunnerLogger {
    public void warn(String msg);

    public void info(String msg);

    public void error(String msg);

    public void error(String msg, Throwable e);
}
