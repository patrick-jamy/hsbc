/**
 * 
 * Testing class for the three Event Bus implementation (in one thread / in multithreading context / with Coalescing context)
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise2.test;

import exercise2.impl.MultiThreadedEventBusImpl;
import exercise2.service.EventSubscriber;

public class TestEventBusMultithreaded {

    // Test of Multithreaded Event bus
    public static void testMultiThreadedEventBus() throws InterruptedException {
        System.out.println("\nTesting MultiThreadedEventBus...");

        MultiThreadedEventBusImpl eventBus = new MultiThreadedEventBusImpl();

        // Create a subscriber which print the latest event when he receives it
        EventSubscriber subscriber = new EventSubscriber() {
            @Override
            public void onEvent(Object event) {
                System.out.println("Thread Multithreaded " + Thread.currentThread().getName() + " received: " + event);
            }

            @Override
            public Class<?> getEventType() {
                return String.class;
            }
        };

        // Add a subscriber
        eventBus.addSubscriber(String.class, subscriber);


        // Publish an event in main thread
        System.out.println("We will publish two events in two differents threads");
        System.out.println("We expect them to be read in the consolte in two different output");
        
        System.out.println("Result:");
        new Thread(() -> {
            eventBus.publishEvent("Multithread #1 Event!");
        }).start();
        
        new Thread(() -> {
            eventBus.publishEvent("Multithread #2 Event!");
        }).start();


    }


    public static void main(String[] args) throws InterruptedException {
        // Test MultiThreaded EventBus
        testMultiThreadedEventBus();
    }
}