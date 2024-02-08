package com.twlabs.kinds.api;

import com.google.common.eventbus.Subscribe;

/**
 * UnsupportedKindHandler
 */
public class UnsupportedKindHandler {

    @Subscribe
    public void handleDeadEvent(KindHandlerEvent event) {
        final String kindName = event.getKindName() + event.getApiVersion();

        for (Kind<?> kh : new KindFinder().getAllKindHandlers()) {
            KindHandler annotation = kh.getClass().getAnnotation(KindHandler.class);
            String mapping = annotation.name() + annotation.apiVersion();

            if (kindName.equals(mapping))
                return;
        }
        event.getCommand().getLogger().info("No KindHandler found for: " + kindName);

        throw new RuntimeException("Unsupported kind handler received: " + event.getKindName());
    }
}
