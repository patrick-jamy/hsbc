/**
 * 
 * 
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise2.service;

public interface EventSubscriber {
	   void onEvent(Object event);
	    Class<?> getEventType();
}
