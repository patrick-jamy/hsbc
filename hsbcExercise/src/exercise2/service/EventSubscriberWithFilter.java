/**
 * 
 * 
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise2.service;

public interface EventSubscriberWithFilter extends EventSubscriber {
	boolean filter(Object event);
}
