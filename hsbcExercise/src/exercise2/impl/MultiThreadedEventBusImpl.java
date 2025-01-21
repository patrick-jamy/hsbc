/**
 * 
 * Implementation of Multithreaded Event Bus, like the Simple Event Bus with threading support
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise2.impl;

import java.util.*;

import exercise2.service.EventSubscriber;
import exercise2.service.EventSubscriberWithFilter;

public class MultiThreadedEventBusImpl   extends AbstractMultiThreadEventBusImpl {

	    @Override
	    public void publishEvent(Object event) {
	    	// Check that each subscriber receive the event on a dedicated thread
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


	
}