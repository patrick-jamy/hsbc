/**
 * 
 * Implementation of ProbabilisticRandomGen
 * Assign a probability and emulate the drawn of the next value (method nextFromSample) following the probability chosen
 * 
 * @version 1.0
 *
 * @see ProbabilisticRandomGen
 * @author Patrick JAMY
 */

package exercise1.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Random;


import java.util.TreeMap;

import exercise1.service.ProbabilisticRandomGen;

public class ProbabilisticRandomGenImpl implements ProbabilisticRandomGen {

    private final List<Integer> numbers;
    private final List<Float> probabilitiesList;
    private final Random random;

    private Map<Integer, Float> numberAndSortedDescProbabilities;

	// Constructor to initialize the generator with a list of numbers and probabilities
    // Can raise IllegalArgumentException exception if probabilities values are not coherent (sum equals to 1 and must be between 0 and 1)
    public ProbabilisticRandomGenImpl(List<NumAndProbability> samples) {

    	// Checking coherence datas
        if (samples == null || samples.isEmpty()) {
            throw new IllegalArgumentException("Samples cannot be null or empty");
        }

        float totalProbability = 0;
        numbers = new ArrayList<>();
        probabilitiesList = new ArrayList<>();

        for (NumAndProbability sample : samples) {
            if (sample.getProbabilityOfSample() < 0 || sample.getProbabilityOfSample() > 1) {
                throw new IllegalArgumentException("Probabilities must be between 0 and 1");
            }
            totalProbability += sample.getProbabilityOfSample();
            numbers.add(sample.getNumber());
            probabilitiesList.add(totalProbability);
        }

        if (Math.abs(totalProbability - 1.0) > 0) {
            throw new IllegalArgumentException("Probabilities must sum to 1");
        }

        // Determinate Random object to calculate random values later
        this.random = new Random();

        // Insert into all Datas into a TreeMap
        Map<Integer, Float> mapDatas = new HashMap<>();
        for (Integer pos = 0; pos < numbers.size(); pos++) {
        	mapDatas.put(numbers.get(pos), probabilitiesList.get(pos));
        }

        // sortedMapDatas contains the key associated to a probability float between 0 and 1 to reach it
        // for example if we have as initial input {1=0.5, 2=0.1, 3=0.4}
        // the contents of the sortedMapDatas will be : {1=0.5, 2=0.6, 3=1.0}
        // it will allow us to easily match the the first value in the sortedMap
        // where the random previously value is the closest
        numberAndSortedDescProbabilities = valueSort(mapDatas);
    }


    // Classic function to sort datas in Map from Highest Map Value (not key) to Lowest one
    public static <K, V extends Comparable<V> > Map<K, V>
    valueSort(final Map<K, V> map)
    {
        // Static Method with return type Map and
        // extending comparator class which compares values
        // associated with two keys
        Comparator<K> valueComparator = new Comparator<>() {

                  // return comparison results of values of
                  // two keys
                  @Override
				public int compare(K k1, K k2)
                  {
                      int comp = map.get(k1).compareTo(
                          map.get(k2));
                      if (comp == 0) {
						return 1;
					} else {
						return comp;
					}
                  }

              };

        // SortedMap created using the comparator
        Map<K, V> sorted = new TreeMap<>(valueComparator);

        sorted.putAll(map);

        return sorted;
    }

    public Map<Integer, Float> getNumberAndSortedDescProbabilities() {
		return numberAndSortedDescProbabilities;
	}

	public void setNumberAndSortedDescProbabilities(Map<Integer, Float> numberAndSortedDescProbabilities) {
		this.numberAndSortedDescProbabilities = numberAndSortedDescProbabilities;
	}


    @Override
    public int nextFromSample() {
    	int result = 0;

        float randValue = random.nextFloat(); // Random value between 0 and 1

        // Will return the first value from the sorted datas closest (and inferior) to the random value
        OptionalInt optionalInt = numberAndSortedDescProbabilities.entrySet().stream().filter( p -> randValue < p.getValue() ).mapToInt(p -> p.getKey()).findFirst();
        result = optionalInt.getAsInt();

        return result;
    }

}