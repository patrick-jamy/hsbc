/**
 * 
 * Testing class for the three Event Bus implementation (in one thread / in multithreading context / with Coalescing context)
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise2.test;

import exercise2.impl.CoalescingEventBusImpl;
import exercise2.service.EventSubscriber;

public class TestEventBusCoaslescing {
    
	
    // Test of EventBus with coalescing
    public static void testCoalescingEventBus() throws InterruptedException {
        System.out.println("\nTesting CoalescingEventBus...");

        CoalescingEventBusImpl eventBus = new CoalescingEventBusImpl();

        // Create a subscriber which print the latest event when he receives it
        EventSubscriber subscriber = new EventSubscriber() {
            @Override
            public void onEvent(Object event) {
            	   System.out.println("--> Result Thread Coalescent " + Thread.currentThread().getName() + " received: " + event);
            }

            @Override
            public Class<?> getEventType() {
                return String.class;
            }
        };

        // Add subscriber
        eventBus.addSubscriber(String.class, subscriber);

        // Publish some events
        eventBus.publishEvent("First event");
        eventBus.publishEvent("Second event");
        eventBus.publishEvent("Latest event");
        
        System.out.println("We publish three  datas, first event, second event and latest event");
        System.out.println("We expect them to be processed and displayed in the console");

        System.out.println("We also expect to retrieve the Latest Event by checking the eventBus");
        // Get the latest event from the Bus
        System.out.println("---> Last Event Test --> the last event published in the bus: " + eventBus.getLatestEvent(String.class));
    }

    public static void main(String[] args) throws InterruptedException {
        // Test Coalescing EventBus
        testCoalescingEventBus();
    }
}