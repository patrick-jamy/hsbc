/**
 * 
 * Testing class for the three Event Bus implementation (in one thread / in multithreading context / with Coalescing context)
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise2.test;


import exercise2.impl.SimpleEventBusImpl;
import exercise2.service.EventSubscriber;
import exercise2.service.EventSubscriberWithFilter;

public class TestEventBusSingleThreadAndFilter {
    
    // Test of Event Bus monothread
    public static void testSimpleEventBus() {
        System.out.println("Testing SimpleEventBus...");

        SimpleEventBusImpl eventBus = new SimpleEventBusImpl();

        // Add a subscriber which print the message when he receives the event
        EventSubscriber subscriber = new EventSubscriber() {
            @Override
            public void onEvent(Object event) {
                System.out.println("---> RESULT ---> Subscriber Received String event: " + event);
            }

            @Override
            public Class<?> getEventType() {
                return String.class; // We only want subscribers to receive String type
            }
        };

        // Add subscriber
        eventBus.addSubscriber(Integer.class, subscriber);
        
        
        // Add Integer subscriber
        // Add a subscriber which print the message when he receives the event
        EventSubscriber subscriberInteger = new EventSubscriber() {
            @Override
            public void onEvent(Object event) {
                System.out.println("---> RESULT ---> Subscriber Received  Integer event: " + event);
            }

            @Override
            public Class<?> getEventType() {
                return Integer.class; // We only want subscribers to receive Integer type
            }
        };

        // Add a subscriber without filter
        eventBus.addSubscriber(Integer.class, subscriberInteger);

        // Add a subscriber with filter
        EventSubscriberWithFilter subscriberWithFilter = new EventSubscriberWithFilter() {
        	
        	  @Override
              public void onEvent(Object event) {
                  System.out.println("---> RESULT ---> Subscriber Received Filter String event: " + event);
              }

              @Override
              public Class<?> getEventType() {
                  return String.class; // We only want subscribers to receive String type
              }
              
        	@Override
        	 public boolean filter(Object event) {
        		boolean result = false;
        		if (event instanceof String) {
        		 result = ((String)event).length() <= 5;
        		}
        		return result;
        	}
        };
        eventBus.addSubscriberForFilteredEvents(String.class, subscriberWithFilter);
       
        System.out.println("At this step we have 3 subscribers : 2 for String Event type, 1 for Integer event type");
        System.out.println("One of the String event type is a Filtered Subscriber which only want to receive events with String datas which contains 5 max chars");
        
        // Only Integer Subscriber will receive the following one
        System.out.println("We will publish an Integer data (5) and we expect only the Integer subscriber will receive this data because the event type is Integer");
        eventBus.publishEvent(5);

        // Only the two String subscriber will receive the following ones
        
        // We expect everybody to see this event now
        System.out.println("Now we will publish a data with 5 charaters maximum (Hello!)");
        System.out.println("=== We expect to have 2 Hello! === because of filter, size is equals or lower to 5");
        eventBus.publishEvent("Hello");
        // Filter test
        // We're just expecting to see the first String subscriber without filter to have this, because  the string size if > 5 characters
        System.out.println("Finally  we will publish a data with lots of charaters maximum (Hello,EventBus!)");
        System.out.println("=== We expect to have only one Hello,EventBus!  because size is greater than 5 ===");
        eventBus.publishEvent("Hello, EventBus!");

    }


    public static void main(String[] args) throws InterruptedException {
        // Test Simple EventBus
        testSimpleEventBus();

    }
}