/**
 * 
 * Testing class for Throttler
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise3.test;

import exercise3.impl.ThrottlerImpl;
import exercise3.service.ThrottleSubscriber;
import exercise3.service.Throttler.ThrottleResult;

public class TestThrottler {

    // Test of Throttler
    public static void testThrottler() throws InterruptedException {
        System.out.println("Testing Throttler...");

        System.out.println("In this test, we have defined the Throttler to only manage 3 calls simultaneously over 5 secs");
        System.out.println("Warning rules #1: After a successfull PROCEED we voluntary wait 1s");
        System.out.println("Warning rules #2: After a fail DO_NOT_PROCEED we voluntary wait 2s");
    	System.out.println("We will ask to proceed 5 tasks");
    	System.out.println("Attempt #1, we expect to be PROCEED: because it's performed immediately after the initial one");
    	System.out.println("Attempt #2, we expect to be PROCEED: it will be performed 1s after the #1, so we expect to be PROCEED too -except if buzy cpu-");
    	System.out.println("Attempt #3, we expect to be PROCEED: will be performed ~2s after the begnning, so we still have 3 attempt in PROCEED, limit is reached");
        System.out.println("Attempt #4, we expect to be DO_NOT_PROCEED: it will be performed ~3s after the beginning, because we have waited 2s after the previous DO_NOT_PROCEED; so a task is taking 5s, it's still full");
        System.out.println("Attempt #5, we expect to be DO_NOT_PROCEED: it will be performed ~4s after the beginning, still full until 5s");
        System.out.println("Attempt #6, we expect to be PROCEED:  > 5s on timer, previous task are performed until 5s so it will be ok");
        
        System.out.println("We also expect to see  5 notifications to Subscribers in the logs associated to the 5 performed tasks");
        
        // Create a Throttler which authorized 3 calls every 5 sec
        ThrottlerImpl throttler = new ThrottlerImpl(3, 5000);

        // Create a subscriber
        ThrottleSubscriber subscriber = new ThrottleSubscriber() {
            @Override
            public void onThrottleEvent(ThrottleResult result) {
                System.out.println("Subscriber #1 notified, we can " + result);
            }
        };
        
        ThrottleSubscriber subscriber2 = new ThrottleSubscriber() {
            @Override
            public void onThrottleEvent(ThrottleResult result) {
                System.out.println("Subscriber #2  notified, we can " + result);
            }
        };
        
        ThrottleSubscriber subscriber3 = new ThrottleSubscriber() {
            @Override
            public void onThrottleEvent(ThrottleResult result) {
                System.out.println("Subscriber #3  notified, we can " + result);
            }
        };
        
        ThrottleSubscriber subscriber4 = new ThrottleSubscriber() {
            @Override
            public void onThrottleEvent(ThrottleResult result) {
                System.out.println("Subscriber #4  notified, we can " + result);
            }
        };
        
        ThrottleSubscriber subscriber5 = new ThrottleSubscriber() {
            @Override
            public void onThrottleEvent(ThrottleResult result) {
                System.out.println("Subscriber #5  notified, we can " + result);
            }
        };

        // Register the subscriber to be notified
        throttler.notifyWhenCanProceed(subscriber);
        throttler.notifyWhenCanProceed(subscriber2);
        throttler.notifyWhenCanProceed(subscriber3);
        throttler.notifyWhenCanProceed(subscriber4);
        throttler.notifyWhenCanProceed(subscriber5);

        // Test calls in a loop
        for (int i = 0; i < 6; i++) {
            ThrottleResult result = throttler.shouldProceed();
            System.out.println("Attempt " + (i + 1) + ": " + result);

            // If the call can't be performed, we wait 2s
            if (result == ThrottleResult.DO_NOT_PROCEED) {
                System.out.println("Waiting for the next window...");
            } 
            Thread.sleep(1000); // Simulated pause
        }
    }

    public static void main(String[] args) throws InterruptedException {
       	testThrottler();
    }
}
