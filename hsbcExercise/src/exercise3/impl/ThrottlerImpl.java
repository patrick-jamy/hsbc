/**
 * 
 * Implementation of a process which  only accept to manage sub process simultaneously during a specified time window
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise3.impl;

import java.util.*;

import exercise3.service.ThrottleSubscriber;
import exercise3.service.Throttler;

public class ThrottlerImpl implements Throttler {
	private final int maxCalls;      	    // Maxmimum number of authorized calls  
	private final long timeWindow;          // Time windo in miliseconds
	private final Queue<Long> callTimes;    // Register the time when the actions were performed
	private  List<ThrottleSubscriber> subscribers; // Subscribers list

	public ThrottlerImpl(int maxCalls, long timeWindow) {
		this.maxCalls = maxCalls;
		this.timeWindow = timeWindow;
		this.callTimes = new LinkedList<>();
		this.subscribers = new ArrayList<>();
	}

	// Check if the action can be perforemd (poll)
	@Override
	public ThrottleResult shouldProceed() {
		long currentTime = System.currentTimeMillis();

		// Remove all the previous actions when time is over the timeWindow
		while (!callTimes.isEmpty() && currentTime - callTimes.peek() > timeWindow) {
			callTimes.poll();
		}

		// If the number of calls is lesser than the maxCalls, we can proceed
		if (callTimes.size() < maxCalls) {
			callTimes.offer(currentTime); // We record the actual time

			// Notify
			if (subscribers.size() > 0) {
				int index = 0;
				for (int notifiyCount = callTimes.size() ; notifiyCount <= maxCalls; notifiyCount ++)  {
					if (index < subscribers.size()) {
						subscribers.get(index).onThrottleEvent(ThrottleResult.PROCEED); // If yes we notify the subscriber
						index++;
					}
				}

				// Refresh the list
				subscribers = subscribers.subList(index, subscribers.size());

			}

			return ThrottleResult.PROCEED;
		}

		// If the number of authorized calls is greater than the maxCalls, we can't proceed 
		return ThrottleResult.DO_NOT_PROCEED;
	}

	// Notify the subscribers that the action can be performed (push)
	@Override
	public void notifyWhenCanProceed(ThrottleSubscriber subscriber) {
		subscribers.add(subscriber);
	}

}
