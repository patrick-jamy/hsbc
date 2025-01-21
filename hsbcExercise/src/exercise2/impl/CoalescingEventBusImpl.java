/**
 * 
 * Implementation of Coaslecing Event Bus, it's like the Multithreading one with the possibility to retrieve the Latest Event for a similar key
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise2.impl;

import java.util.*;
import java.util.concurrent.*;

import exercise2.service.EventSubscriber;
import exercise2.service.EventSubscriberWithFilter;

public class CoalescingEventBusImpl extends AbstractMultiThreadEventBusImpl   {
    private final Map<Class<?>, Object> latestEvents = new ConcurrentHashMap<>();


    public void  publishEvent(Object event) {
        // Coalesce the event to only keep the lastest one thanks to "put" method
        latestEvents.put(event.getClass(), event);

        // Publish the event on a distinct thread
        List<EventSubscriber> subscribers = subscribersMap.get(event.getClass());
        if (subscribers != null) {
            for (EventSubscriber subscriber : subscribers) {
            	if (subscriber instanceof EventSubscriberWithFilter) {
            		if (((EventSubscriberWithFilter) subscriber).filter(event)) {
            			 executorService.submit(() -> subscriber.onEvent(event));
            		}
            	} else {
            		 executorService.submit(() -> subscriber.onEvent(event));
            	} 
            }
        }
    }

    public Object getLatestEvent(Class<?> eventType) {
        return latestEvents.get(eventType);
    }
    
    
}