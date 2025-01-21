/**
 * 
 * 
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise1.service;

public interface ProbabilisticRandomGen {

	public int nextFromSample();
	
	static class NumAndProbability {
		
		private final int number;
		
		private final float probabilityOfSample;
		
		public NumAndProbability(int number, float probabilityOfSample) {	
			this.number = number;
			this.probabilityOfSample = probabilityOfSample;
		}
		
		public int getNumber() {
			return number;
		}
		
		public float getProbabilityOfSample() {
			return probabilityOfSample;
		}
	}	

}
