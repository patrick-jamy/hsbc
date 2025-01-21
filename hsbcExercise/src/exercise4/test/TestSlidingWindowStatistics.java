/**
 * 
 * Testing class for Statistics
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise4.test;

import java.util.ArrayList;
import java.util.List;

import exercise4.impl.SlidingWindowStatisticsImpl;
import exercise4.impl.StatisticsImpl;
import exercise4.service.SlidingWindowStatistics;
import exercise4.service.StatisticSubscriber;
import exercise4.service.Statistics;

public class TestSlidingWindowStatistics {

	public static void testStatistics() {
		List<Integer> measureList = new ArrayList<Integer>();
		measureList.add(10);
		measureList.add(20);
		measureList.add(20);
		
		Statistics stats  = new StatisticsImpl(measureList);
		
		System.out.println("Expected mean: 16.666.......");
		System.out.println("Calculated mean: "+stats.getMean());
		
		System.out.println("Expected Mode: 20");
		System.out.println("Calculated mode: "+stats.getMode());
		
		System.out.println("Expected 30e percentile: 10.0");
		System.out.println("Calculated percentile: "+stats.getPctile(30));
		
	}
	
	public static void testSlidingWindowStatistic() {
		 // Create an instance instance of SlidingWindowStatistics
        SlidingWindowStatistics statistics = new SlidingWindowStatisticsImpl();

        // Create a subscriber in order to receive updates
        StatisticSubscriber subscriber = new StatisticSubscriber() {
            @Override
            public synchronized void onStatisticsUpdated(Statistics stats) {
            	
                System.out.println("Statistics update : ");
                System.out.println("Mean : " + stats.getMean());
                System.out.println("Mode : " + stats.getMode());
                System.out.println("80e percentile : " + stats.getPctile(80));
                System.out.println("------------------------");
            }
        };

        // Add subscriber
        statistics.subscribeForStatistics(subscriber);

        
        System.out.println("Now we run the threads because when we add some statistics, it will run a notify thread in the implementation");
        System.out.println("We expect to have 6 blocks of coherent statistics details in the console, -CAREFUL- not necessary in the pushed order");
        
        String expectedBlocks = """ 
				Statistics update : 
				Mean : 10.0
				Mode : 10
				80e percentile : 10.0
				------------------------
				Statistics update : 
				Mean : 15.0
				Mode : 20
				80e percentile : 20.0
				------------------------
				Statistics update : 
				Mean : 16.666666666666668
				Mode : 20
				80e percentile : 20.0
				------------------------
				Statistics update : 
				Mean : 20.0
				Mode : 20
				80e percentile : 30.0
				------------------------
				Statistics update : 
				Mean : 24.0
				Mode : 20
				80e percentile : 30.0
				------------------------
				Statistics update : 
				Mean : 28.333333333333332
				Mode : 20
				80e percentile : 40.0		
        		""";
        
        System.out.println("==> EXPECTED STATS BLOCKS -sorted- <==");
        System.out.println(expectedBlocks);
        System.out.println("==> CALCULATED STATS BLOCKS <==");
        // Add datas
        statistics.add(10);
        statistics.add(20);
        statistics.add(20);
        statistics.add(30);
        statistics.add(40);
        statistics.add(50);
	}
	
    public static void main(String[] args) {
    	System.out.println("========================================");
    	System.out.println("=> test statistics <=");
    	testStatistics();
    	System.out.println("========================================");
    	System.out.println("=> test SlidingWindowStatistic <=");
    	testSlidingWindowStatistic();
    }
}