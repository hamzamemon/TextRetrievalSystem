package index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class creates a Posting, which is when a term appears in the file. It contains the filename, the frequency of
 * said term in the file, the logged term frequency, the weight and the first location of the term in the file.
 *
 * @author hamza
 */
public class Posting implements Serializable {
    
    public static final Comparator<Posting> COMPARATOR = Comparator.comparing(posting -> posting.name);
    
    private String name;
    private int frequency;
    //private int firstLocation;
    private double loggedTf;
    private double weight;
    private ArrayList<Integer> locations;
    
    /**
     * Instantiates a new Posting.
     *
     * @param name          the filename
     * @param firstLocation the first location of the term in the file
     */
    public Posting(String name, int firstLocation) {
        this.name = name;
        //this.firstLocation = firstLocation;
        frequency = 1;
        
        locations = new ArrayList<>();
        locations.add(firstLocation);
    }
    
    /**
     * Gets the logged term frequency
     *
     * @return logged term frequency
     */
    public double getLoggedTf() {
        return loggedTf;
    }
    
    /**
     * Sets the logged term frequency
     */
    public void setLoggedTf() {
        loggedTf = Math.log10(frequency) + 1;
    }
    
    /**
     * Gets the frequency of the term in the file as an Integer
     *
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }
    
    /**
     * Gets the first location of the term in the file as an Integer.
     *
     * @return the first location
     */
    public int getFirstLocation() {
        return locations.get(0);
    }
    
    /**
     * Increments frequency is the particular word is found another time in the file
     */
    public void addLocation(int location) {
        locations.add(location);
        frequency++;
    }
    
    public ArrayList<Integer> getLocations() {
        return locations;
    }
    
    /**
     * Gets the filename as a String
     *
     * @return the filename
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the weight
     *
     * @return weight
     */
    public double getWeight() {
        return weight;
    }
    
    /**
     * Sets the weight
     *
     * @param weight weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
}
