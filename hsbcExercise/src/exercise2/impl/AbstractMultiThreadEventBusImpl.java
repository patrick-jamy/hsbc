package exercise2.impl;


/**
 * 
 * Common implementation for multithreading Event bus
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import exercise2.service.EventBus;
import exercise2.service.EventSubscriber;
import exercise2.service.EventSubscriberWithFilter;

// Implementation of common functionnalities for multithreading bus
public  abstract class AbstractMultiThreadEventBusImpl implements EventBus {
    protected final Map<Class<?>, List<EventSubscriber>> subscribersMap = new HashMap<>();

    protected final ExecutorService executorService = Executors.newCachedThreadPool();
    
    @Override
    public abstract void publishEvent(Object event);


    @Override
    public void addSubscriber(Class<?> eventType, EventSubscriber subscriber) {
        subscribersMap.computeIfAbsent(eventType, k -> new ArrayList<>()).add(subscriber);
    }

    @Override
    public void addSubscriberForFilteredEvents(Class<?> eventType, EventSubscriberWithFilter subscriber) {
        subscribersMap.computeIfAbsent(eventType, k -> new ArrayList<>()).add(subscriber);
    }
    

    public void shutdown() {
        executorService.shutdown();
    }
}