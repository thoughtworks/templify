package com.twlabs.kinds.api;

import com.google.common.eventbus.Subscribe;
import com.twlabs.services.logger.RunnerLogger;

/**
 * UnsupportedKindHandler
 */
public class UnsupportedKindHandler {

    @Subscribe
    public void handleDeadEvent(KindHandlerEvent event) {
        final String kindName = event.getKindName() + event.getApiVersion();

        for (Class<?> kh : KindFinder.getAll()) {
            KindHandler annotation = kh.getAnnotation(KindHandler.class);
            String mapping = annotation.name() + annotation.apiVersion();

            event.getRequest().getLogger().info("Unsupported kind handler received: " + kindName);
            event.getRequest().getLogger().info("kind handler find: " + mapping);

            if (kindName.equals(mapping))
                return;
        }

        throw new RuntimeException("Unsupported kind handler received: " + event.getKindName());
    }
}
