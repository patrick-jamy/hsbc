/**
 * 
 * 
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise3.service;

public interface Throttler {
	// Check if the action can be performed or not (poll)
    ThrottleResult shouldProceed();

    // Register a subscriber which will be notified when the action can be performed (push)
    void notifyWhenCanProceed(ThrottleSubscriber subscriber);
    
    enum ThrottleResult {
        PROCEED,      // The action can be performed
        DO_NOT_PROCEED // The action can NOT be performed
    }
}