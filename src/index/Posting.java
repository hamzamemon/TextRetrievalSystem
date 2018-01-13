package index;

import java.io.Serializable;
import java.util.Comparator;

/**
 * This class creates a Posting, which is when a term appears in the file.
 * It contains the filename, the frequency of said term in the file, the weight and the first location of the
 * term in the file.
 */
public class Posting implements Serializable {
    
    public static final Comparator<Posting> COMPARATOR = Comparator.comparing(posting -> posting.name);
    
    private String name;
    private int frequency;
    private int firstLocation;
    private double weight;
    
    /**
     * Instantiates a new Posting.
     *
     * @param name          the filename
     * @param firstLocation the first location of the term in the file
     */
    public Posting(String name, int firstLocation) {
        this.name = name;
        this.firstLocation = firstLocation;
        frequency = 1;
    }
    
    /**
     * Gets the logged term frequency
     *
     * @return logged term frequency
     */
    public double getLoggedTf() {
        return Math.log10(frequency) + 1;
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
        return firstLocation;
    }
    
    /**
     * Increments frequency is the particular word is found
     * another time in the file
     */
    public void incrementFrequency() {
        frequency++;
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
