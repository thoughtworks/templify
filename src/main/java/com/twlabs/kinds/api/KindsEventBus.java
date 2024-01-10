package com.twlabs.kinds.api;

import java.util.logging.LogManager;
import java.util.logging.Logger;
import com.google.common.eventbus.EventBus;
import com.twlabs.kinds.handlers.javahandler.JavaHandlerKind;

/**
 * KindsEventBus
 */
public class KindsEventBus {

    private static KindsEventBus instance;
    private final static Logger LOG = Logger.getLogger(KindsEventBus.class.getName());

    private EventBus bus;

    private EventBus registerHandlers(EventBus bus) {
        LOG.info("Registering KindsEventBus handlers");
        bus.register(new JavaHandlerKind());
        return bus;
    }

    private KindsEventBus() {
        LOG.info("KindsEventBus is initializing");
        EventBus eventBus = new EventBus();
        this.bus = this.registerHandlers(eventBus);
    }

    synchronized public static EventBus getInstance() {
        if (instance == null) {
            instance = new KindsEventBus();
            LOG.info("KindsEventBus initialized.");
        }
        return instance.bus;
    }
}
