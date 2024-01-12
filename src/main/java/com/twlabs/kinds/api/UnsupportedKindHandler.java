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

        for (Class<?> controller : KindFinder.getAll()) {
            KindHandler request = controller.getAnnotation(KindHandler.class);
            String mapping = request.name() + request.apiVersion();
            if (kindName.equals(mapping))
                return;
            else
                break;
        }

        RunnerLogger logger = event.getRequest().getLogger();
        logger.error("Unsupported kind handler received: " + event.getKindName());
    }
}
