/**
 * 
 * 
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise4.service;

public interface SlidingWindowStatistics {
	
    void add(int measurement);
    
    // subscriber will have a callback that'll deliver a Statistics instance (push)
    void subscribeForStatistics(StatisticSubscriber subscriber);
    
    // get latest statistics (poll)
    Statistics getLatestStatistics(); 
}