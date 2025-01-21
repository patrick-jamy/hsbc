/**
 * 
 * 
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise3.service;

import exercise3.service.Throttler.ThrottleResult;

public interface ThrottleSubscriber {
	void onThrottleEvent(ThrottleResult result);
}


