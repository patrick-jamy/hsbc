/**
 * 
 * Testing class for Probabilistict Random Gen
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise1.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exercise1.impl.ProbabilisticRandomGenImpl;
import exercise1.service.ProbabilisticRandomGen;
import exercise1.service.ProbabilisticRandomGen.NumAndProbability;

public class TestProbabilisticRandomGen {

	public static void testLimits() {
		List<NumAndProbability> samples = Arrays.asList(
				new NumAndProbability(1, 0.5f),
				new NumAndProbability(2, 0.1f),
				new NumAndProbability(3, 0.6f) // the total value will be over 1, so we expect an exception to be raised
				);

		try {
			System.out.println("We expect an exception cause sum of probabilities is over 1");
			ProbabilisticRandomGen _ = new ProbabilisticRandomGenImpl(samples);
		} catch (IllegalArgumentException iae) {
			System.out.println("Exception details: "+iae.getMessage());
		}


		samples = Arrays.asList(
				new NumAndProbability(1, 0.5f),
				new NumAndProbability(2, 0.1f),
				new NumAndProbability(3, 2.0f) // one value is over 1, we expect it to be rejected because value must be between 0 and 1
				);
		try {
			System.out.println("We expect an exception because one value is not between 0 and 1 (2.0f)");
			ProbabilisticRandomGen _ = new ProbabilisticRandomGenImpl(samples);
		} catch (IllegalArgumentException iae) {
			System.out.println("Exception details: "+iae.getMessage());
		}
	}


	public static void testValueSort() {
		Map<Integer, Float> mapDatas = new HashMap<>();
		mapDatas.put(1, 0.2f);
		mapDatas.put(2, 0.1f);
		mapDatas.put(3, 0.9f);
		mapDatas.put(4, 0.4f);

		System.out.println("Current values order");
		mapDatas.values().stream().forEach(System.out::println);

		Map<Integer, Float> mapDatasAscSort =  ProbabilisticRandomGenImpl.valueSort(mapDatas);
		System.out.println("We expect to sort by Values asc");
		System.out.println("After sort result:");
		mapDatasAscSort.values().stream().forEach(System.out::println);   
	}

	public static void testProbabilisticRandomGen() {
		List<NumAndProbability> samples = Arrays.asList(
				new NumAndProbability(1, 0.5f),
				new NumAndProbability(2, 0.1f),
				new NumAndProbability(3, 0.4f)
				);

		ProbabilisticRandomGen generator = new ProbabilisticRandomGenImpl(samples);

		// Simulate and count occurrences
		Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
		for (int i = 0; i < 10000; i++) {
			int number = generator.nextFromSample();
			// Find number in the map and additional to the current matching one (+1)
			counts.put(number, counts.getOrDefault(number, 0) + 1);
		}

		// Print results
		System.out.println("We expect the occurences to reflect the probabilities applied for 1000 draws:");
		System.out.println("Expected Number 1 : ~5000");
		System.out.println("Expected Number 2 : ~1000");
		System.out.println("Expected number1 :  ~4000");
		System.out.println("The addition of all must be 10000");
		System.out.println("Occurrences randomly generated:");
		counts.forEach((key, value) -> System.out.println("Number: " + key + ", Count: " + value));
		int total = counts.values().stream().mapToInt(i->i).sum();
		System.out.println("total calculated: "+total);

	}


	// Test code
	public static void main(String[] args) {
		System.out.println("========================================");
		System.out.println("=> test testLimits <=");
		testLimits();
		System.out.println("========================================");
		System.out.println("=> test valueSort <=");
		testValueSort();
		System.out.println("========================================");
		System.out.println("=> test SlidingWindowStatistic <=");
		testProbabilisticRandomGen();
	}

}
