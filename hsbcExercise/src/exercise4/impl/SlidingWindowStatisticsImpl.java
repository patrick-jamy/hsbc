/**
 * 
 * Update statistics datas (with add method) in synchronized context and ask run statistics threads to notify it
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise4.impl;

import java.util.ArrayList;
import java.util.List;

import exercise4.service.SlidingWindowStatistics;
import exercise4.service.StatisticSubscriber;
import exercise4.service.Statistics;

public class SlidingWindowStatisticsImpl implements SlidingWindowStatistics {
	
    private final List<Integer> measurements;
    private final List<StatisticSubscriber> subscribers; 

    public SlidingWindowStatisticsImpl() {
        this.measurements = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    @Override
    public synchronized void add(int measurement) {
        measurements.add(measurement);

        // Calculate current statistics
        Statistics stats = new StatisticsImpl(measurements);

        // Notify all the subscribers
        for (StatisticSubscriber subscriber : subscribers) {
            notifySubscriber(subscriber, stats);
        }
    }

    @Override
    public synchronized void subscribeForStatistics(StatisticSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public synchronized Statistics getLatestStatistics() {
        return new StatisticsImpl(measurements);
    }

    private void notifySubscriber(StatisticSubscriber subscriber, Statistics stats) {
    	// Notify the subscriber in a new thread in order to not freeze the main thread
        new Thread(() -> subscriber.onStatisticsUpdated(stats)).start();
    }
}