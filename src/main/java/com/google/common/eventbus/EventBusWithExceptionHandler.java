package com.google.common.eventbus;

/**
 * EventBusWithExceptionHandler
 */
public class EventBusWithExceptionHandler extends EventBus {

    /**
     * Overrides the handleSubscriberException method from the parent class.
     * 
     * This method is responsible for handling any exceptions that occur during event subscription.
     * It receives two parameters: - e: a Throwable object representing the exception that occurred
     * - context: a SubscriberExceptionContext object providing additional context information about
     * the exception
     * 
     * This method throws a RuntimeException, wrapping the original exception, to ensure that any
     * exceptions that occur during event subscription are properly handled and propagated.
     * 
     * @param e the exception that occurred during event subscription
     * @param context additional context information about the exception
     * @throws RuntimeException if an exception occurs during event subscription
     */
    @Override
    protected void handleSubscriberException(Throwable e, SubscriberExceptionContext context)
            throws RuntimeException {
        throw new RuntimeException(e);
    }

}
