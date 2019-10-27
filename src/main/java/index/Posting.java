package index;

import java.io.Serializable;
import java.util.Comparator;

/**
 * This class creates a Posting, which is when a term appears in the file. It contains the filename, the frequency of
 * said term in the file, the weight and the first location of the term in the file.
 */
public class Posting implements Serializable {
    
    public static final Comparator<Posting> COMPARATOR = Comparator.comparing(posting -> posting.number);
    private static final long serialVersionUID = 3277327571128511637L;
    private int number;
    private int frequency;
    private double weight;
    
    /**
     * Instantiates a new Posting.
     *
     * @param number the document number
     */
    public Posting(int number) {
        this.number = number;
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
     * Increments frequency is the particular word is found another time in the file
     */
    public void incrementFrequency() {
        frequency++;
    }
    
    /**
     * Gets the filename as a String
     *
     * @return the filename
     */
    public int getNumber() {
        return number;
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
    
    /**
     * Output object as String
     *
     * @return the object as a String
     */
    @Override
    public String toString() {
        return "Posting{" +
                "number=" + number +
                ", frequency=" + frequency +
                ", weight=" + weight +
                '}';
    }
}
