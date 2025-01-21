/**
 * 
 * Allow to send (publish) data and to retrieve it in a single thread
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise2.impl;

import java.util.ArrayList;
import java.util.List;

import exercise2.service.EventBus;
import exercise2.service.EventSubscriber;
import exercise2.service.EventSubscriberWithFilter;

// Implementation of Event bus for a single thread
public class SimpleEventBusImpl implements EventBus {
    private List<EventSubscriber> subscribers = new ArrayList<>();
    
    @Override
    public void publishEvent(Object event) {
    	// Publish the event to all subscribers in the same thread
        for (EventSubscriber subscriber : subscribers) {
            if (subscriber.getEventType().isInstance(event)) {
            	if (subscriber instanceof EventSubscriberWithFilter) {
            		if (((EventSubscriberWithFilter) subscriber).filter(event)) {
            			subscriber.onEvent(event);
            		}
            	} else {
                subscriber.onEvent(event);
            	}
            }
        }
    }

    @Override
    public void addSubscriber(Class<?> eventType, EventSubscriber subscriber) {
        if (subscriber != null && eventType != null) {
            subscribers.add(subscriber);
        }
    }
    
    @Override
    public void addSubscriberForFilteredEvents(Class<?> eventType, EventSubscriberWithFilter subscriber) {
    	addSubscriber(eventType, subscriber);
    }
}