/**
 * 
 * 
 * 
 * @version 1.0
 *
 * @author Patrick JAMY
 */

package exercise4.service;

public interface Statistics {
	
    double getMean();
    
    int getMode();
    
    double getPctile(int pctile);
    
}