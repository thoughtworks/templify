package com.twlabs.services.logger;

import org.apache.maven.plugin.logging.Log;

/**
 * MavenLogger
 */
public class MavenLogger implements RunnerLogger {

    private Log log;

    public MavenLogger(Log log) {
        this.log = log;
    }

    @Override
    public void warn(String msg) {
        this.log.warn(msg);
    }

    @Override
    public void info(String msg) {
        this.log.info(msg);
    }

    @Override
    public void error(String msg) {
        this.log.error(msg);
    }

    @Override
    public void error(String msg, Throwable e) {
        this.log.error(msg, e);
    }
}
