package com.twlabs.kinds.api;

import com.google.common.eventbus.EventBusWithExceptionHandler;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import com.google.common.eventbus.EventBus;

/**
 * KindsEventBus
 */
public class KindsEventBus {

    private static KindsEventBus instance;
    private final static Logger LOG = Logger.getLogger(KindsEventBus.class.getName());

    private EventBusWithExceptionHandler bus;

    private EventBusWithExceptionHandler registerHandlers(EventBusWithExceptionHandler bus)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {

        LOG.info("Registering KindsEventBus handlers");
        LOG.info("Registering " + UnsupportedKindHandler.class);
        bus.register(new UnsupportedKindHandler());

        for (Class<?> className : KindFinder.getAll()) {
            LOG.info("Registering " + className);
            bus.register(className.getDeclaredConstructor().newInstance());
        }

        return bus;
    }

    private KindsEventBus() {
        LOG.info("KindsEventBus is initializing");
        EventBusWithExceptionHandler eventBus = new EventBusWithExceptionHandler();
        try {
            this.bus = this.registerHandlers(eventBus);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize KindsEventBus", e);
        }
    }

    synchronized public static EventBus getInstance() {
        if (instance == null) {
            instance = new KindsEventBus();
            LOG.info("KindsEventBus initialized.");
        }
        return instance.bus;
    }
}
