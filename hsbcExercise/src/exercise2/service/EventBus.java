/**
 * 
 * 
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise2.service;

public interface EventBus {
    // Publish an event
    void publishEvent(Object event);
    
    // Add a subscriber
    void addSubscriber(Class<?> eventType, EventSubscriber subscriber);
    
    // Add a subscriber who can filter
    void addSubscriberForFilteredEvents(Class<?> eventType, EventSubscriberWithFilter subscriber);
}