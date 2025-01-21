/**
 * 
 * Only calculate statistics based on a list of Integer (measurements)
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise4.impl;

import java.util.*;

import exercise4.service.Statistics;

public class StatisticsImpl implements Statistics {
    private final List<Integer> measurements;

    public StatisticsImpl(List<Integer> measurements) {
        this.measurements = new ArrayList<>(measurements); // Create a copy to be protected if any external update
    }

    @Override
    public double getMean() {
    	// Classic calculation of mean
        if (measurements.isEmpty()) return 0.0;
        int sum = 0;
        for (int value : measurements) {
            sum += value;
        }
        return sum / (double) measurements.size();
    }

    @Override
    public int getMode() {
    	// Classic calculation of Mode, we want to know the max occurence of a value
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int value : measurements) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }
        return frequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0);
    }

    @Override
    public double getPctile(int pctile) {
    	// Classic mathmatic calculation for Pctile, where a % of datas are lesser than the result value
    	List<Integer> sorted = new ArrayList<>(measurements);
        Collections.sort(sorted);
        int index = (int) Math.ceil(pctile / 100.0 * sorted.size()) - 1;
        return sorted.get(index);
    }
}