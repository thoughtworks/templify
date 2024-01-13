package com.google.common.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;

/**
 * EventBusWithExceptionHandler
 */
public class EventBusWithExceptionHandler extends EventBus {

    /**
     * Just throw a EventHandleException if there's any exception.
     * 
     * @param e
     * @param context
     * @throws EventHandleException
     */


    /**
     * Just throw a EventHandleException if there's any exception.
     * 
     * @param e
     * @param context
     * @throws EventHandleException
     */
    @Override
    protected void handleSubscriberException(Throwable e, SubscriberExceptionContext context)
            throws RuntimeException {
        throw new RuntimeException(e);
    }
}
