package com.twlabs.services.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JavaLogger
 */
public class JavaLogger implements RunnerLogger {

    private Logger log;

    public JavaLogger(Logger log) {
        this.log = log;
    }

    @Override
    public void warn(String msg) {
        this.log.warning(msg);
    }

    @Override
    public void info(String msg) {
        this.log.info(msg);
    }

    @Override
    public void error(String msg) {
        this.log.severe(msg);
    }

    @Override
    public void error(String msg, Throwable e) {
        this.log.log(Level.SEVERE, msg, e);
    }
}
